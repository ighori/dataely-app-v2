/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BusinessUnitDetailComponent from '@/entities/business-unit/business-unit-details.vue';
import BusinessUnitClass from '@/entities/business-unit/business-unit-details.component';
import BusinessUnitService from '@/entities/business-unit/business-unit.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BusinessUnit Management Detail Component', () => {
    let wrapper: Wrapper<BusinessUnitClass>;
    let comp: BusinessUnitClass;
    let businessUnitServiceStub: SinonStubbedInstance<BusinessUnitService>;

    beforeEach(() => {
      businessUnitServiceStub = sinon.createStubInstance<BusinessUnitService>(BusinessUnitService);

      wrapper = shallowMount<BusinessUnitClass>(BusinessUnitDetailComponent, {
        store,
        localVue,
        router,
        provide: { businessUnitService: () => businessUnitServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBusinessUnit = { id: 123 };
        businessUnitServiceStub.find.resolves(foundBusinessUnit);

        // WHEN
        comp.retrieveBusinessUnit(123);
        await comp.$nextTick();

        // THEN
        expect(comp.businessUnit).toBe(foundBusinessUnit);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBusinessUnit = { id: 123 };
        businessUnitServiceStub.find.resolves(foundBusinessUnit);

        // WHEN
        comp.beforeRouteEnter({ params: { businessUnitId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.businessUnit).toBe(foundBusinessUnit);
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
