/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FileConfigDetailComponent from '@/entities/file-config/file-config-details.vue';
import FileConfigClass from '@/entities/file-config/file-config-details.component';
import FileConfigService from '@/entities/file-config/file-config.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FileConfig Management Detail Component', () => {
    let wrapper: Wrapper<FileConfigClass>;
    let comp: FileConfigClass;
    let fileConfigServiceStub: SinonStubbedInstance<FileConfigService>;

    beforeEach(() => {
      fileConfigServiceStub = sinon.createStubInstance<FileConfigService>(FileConfigService);

      wrapper = shallowMount<FileConfigClass>(FileConfigDetailComponent, {
        store,
        localVue,
        router,
        provide: { fileConfigService: () => fileConfigServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFileConfig = { id: 123 };
        fileConfigServiceStub.find.resolves(foundFileConfig);

        // WHEN
        comp.retrieveFileConfig(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fileConfig).toBe(foundFileConfig);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileConfig = { id: 123 };
        fileConfigServiceStub.find.resolves(foundFileConfig);

        // WHEN
        comp.beforeRouteEnter({ params: { fileConfigId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileConfig).toBe(foundFileConfig);
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
