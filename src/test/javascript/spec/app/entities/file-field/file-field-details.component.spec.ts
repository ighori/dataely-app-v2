/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FileFieldDetailComponent from '@/entities/file-field/file-field-details.vue';
import FileFieldClass from '@/entities/file-field/file-field-details.component';
import FileFieldService from '@/entities/file-field/file-field.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FileField Management Detail Component', () => {
    let wrapper: Wrapper<FileFieldClass>;
    let comp: FileFieldClass;
    let fileFieldServiceStub: SinonStubbedInstance<FileFieldService>;

    beforeEach(() => {
      fileFieldServiceStub = sinon.createStubInstance<FileFieldService>(FileFieldService);

      wrapper = shallowMount<FileFieldClass>(FileFieldDetailComponent, {
        store,
        localVue,
        router,
        provide: { fileFieldService: () => fileFieldServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFileField = { id: 123 };
        fileFieldServiceStub.find.resolves(foundFileField);

        // WHEN
        comp.retrieveFileField(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fileField).toBe(foundFileField);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileField = { id: 123 };
        fileFieldServiceStub.find.resolves(foundFileField);

        // WHEN
        comp.beforeRouteEnter({ params: { fileFieldId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileField).toBe(foundFileField);
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
