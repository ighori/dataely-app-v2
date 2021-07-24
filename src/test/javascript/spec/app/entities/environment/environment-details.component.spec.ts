/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EnvironmentDetailComponent from '@/entities/environment/environment-details.vue';
import EnvironmentClass from '@/entities/environment/environment-details.component';
import EnvironmentService from '@/entities/environment/environment.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Environment Management Detail Component', () => {
    let wrapper: Wrapper<EnvironmentClass>;
    let comp: EnvironmentClass;
    let environmentServiceStub: SinonStubbedInstance<EnvironmentService>;

    beforeEach(() => {
      environmentServiceStub = sinon.createStubInstance<EnvironmentService>(EnvironmentService);

      wrapper = shallowMount<EnvironmentClass>(EnvironmentDetailComponent, {
        store,
        localVue,
        router,
        provide: { environmentService: () => environmentServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEnvironment = { id: 123 };
        environmentServiceStub.find.resolves(foundEnvironment);

        // WHEN
        comp.retrieveEnvironment(123);
        await comp.$nextTick();

        // THEN
        expect(comp.environment).toBe(foundEnvironment);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEnvironment = { id: 123 };
        environmentServiceStub.find.resolves(foundEnvironment);

        // WHEN
        comp.beforeRouteEnter({ params: { environmentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.environment).toBe(foundEnvironment);
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
