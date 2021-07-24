/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FileSourceDetailComponent from '@/entities/file-source/file-source-details.vue';
import FileSourceClass from '@/entities/file-source/file-source-details.component';
import FileSourceService from '@/entities/file-source/file-source.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FileSource Management Detail Component', () => {
    let wrapper: Wrapper<FileSourceClass>;
    let comp: FileSourceClass;
    let fileSourceServiceStub: SinonStubbedInstance<FileSourceService>;

    beforeEach(() => {
      fileSourceServiceStub = sinon.createStubInstance<FileSourceService>(FileSourceService);

      wrapper = shallowMount<FileSourceClass>(FileSourceDetailComponent, {
        store,
        localVue,
        router,
        provide: { fileSourceService: () => fileSourceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFileSource = { id: 123 };
        fileSourceServiceStub.find.resolves(foundFileSource);

        // WHEN
        comp.retrieveFileSource(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fileSource).toBe(foundFileSource);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileSource = { id: 123 };
        fileSourceServiceStub.find.resolves(foundFileSource);

        // WHEN
        comp.beforeRouteEnter({ params: { fileSourceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileSource).toBe(foundFileSource);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
