/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import OrganizationUpdateComponent from '@/entities/organization/organization-update.vue';
import OrganizationClass from '@/entities/organization/organization-update.component';
import OrganizationService from '@/entities/organization/organization.service';

import UserService from '@/admin/user-management/user-management.service';

import BusinessUnitService from '@/entities/business-unit/business-unit.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Organization Management Update Component', () => {
    let wrapper: Wrapper<OrganizationClass>;
    let comp: OrganizationClass;
    let organizationServiceStub: SinonStubbedInstance<OrganizationService>;

    beforeEach(() => {
      organizationServiceStub = sinon.createStubInstance<OrganizationService>(OrganizationService);

      wrapper = shallowMount<OrganizationClass>(OrganizationUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          organizationService: () => organizationServiceStub,

          userService: () => new UserService(),

          businessUnitService: () => new BusinessUnitService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.organization = entity;
        organizationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.organization = entity;
        organizationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(organizationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOrganization = { id: 123 };
        organizationServiceStub.find.resolves(foundOrganization);
        organizationServiceStub.retrieve.resolves([foundOrganization]);

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
