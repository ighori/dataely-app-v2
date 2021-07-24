/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FileInfoDetailComponent from '@/entities/file-info/file-info-details.vue';
import FileInfoClass from '@/entities/file-info/file-info-details.component';
import FileInfoService from '@/entities/file-info/file-info.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FileInfo Management Detail Component', () => {
    let wrapper: Wrapper<FileInfoClass>;
    let comp: FileInfoClass;
    let fileInfoServiceStub: SinonStubbedInstance<FileInfoService>;

    beforeEach(() => {
      fileInfoServiceStub = sinon.createStubInstance<FileInfoService>(FileInfoService);

      wrapper = shallowMount<FileInfoClass>(FileInfoDetailComponent, {
        store,
        localVue,
        router,
        provide: { fileInfoService: () => fileInfoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFileInfo = { id: 123 };
        fileInfoServiceStub.find.resolves(foundFileInfo);

        // WHEN
        comp.retrieveFileInfo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fileInfo).toBe(foundFileInfo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileInfo = { id: 123 };
        fileInfoServiceStub.find.resolves(foundFileInfo);

        // WHEN
        comp.beforeRouteEnter({ params: { fileInfoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileInfo).toBe(foundFileInfo);
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
