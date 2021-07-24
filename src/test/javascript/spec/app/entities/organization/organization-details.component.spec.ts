/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OrganizationDetailComponent from '@/entities/organization/organization-details.vue';
import OrganizationClass from '@/entities/organization/organization-details.component';
import OrganizationService from '@/entities/organization/organization.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Organization Management Detail Component', () => {
    let wrapper: Wrapper<OrganizationClass>;
    let comp: OrganizationClass;
    let organizationServiceStub: SinonStubbedInstance<OrganizationService>;

    beforeEach(() => {
      organizationServiceStub = sinon.createStubInstance<OrganizationService>(OrganizationService);

      wrapper = shallowMount<OrganizationClass>(OrganizationDetailComponent, {
        store,
        localVue,
        router,
        provide: { organizationService: () => organizationServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOrganization = { id: 123 };
        organizationServiceStub.find.resolves(foundOrganization);

        // WHEN
        comp.retrieveOrganization(123);
        await comp.$nextTick();

        // THEN
        expect(comp.organization).toBe(foundOrganization);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrganization = { id: 123 };
        organizationServiceStub.find.resolves(foundOrganization);

        // WHEN
        comp.beforeRouteEnter({ params: { organizationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.organization).toBe(foundOrganization);
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
