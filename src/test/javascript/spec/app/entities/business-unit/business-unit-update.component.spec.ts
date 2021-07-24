/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import BusinessUnitUpdateComponent from '@/entities/business-unit/business-unit-update.vue';
import BusinessUnitClass from '@/entities/business-unit/business-unit-update.component';
import BusinessUnitService from '@/entities/business-unit/business-unit.service';

import ApplicationService from '@/entities/application/application.service';

import OrganizationService from '@/entities/organization/organization.service';

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
  describe('BusinessUnit Management Update Component', () => {
    let wrapper: Wrapper<BusinessUnitClass>;
    let comp: BusinessUnitClass;
    let businessUnitServiceStub: SinonStubbedInstance<BusinessUnitService>;

    beforeEach(() => {
      businessUnitServiceStub = sinon.createStubInstance<BusinessUnitService>(BusinessUnitService);

      wrapper = shallowMount<BusinessUnitClass>(BusinessUnitUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          businessUnitService: () => businessUnitServiceStub,

          applicationService: () => new ApplicationService(),

          organizationService: () => new OrganizationService(),
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
        comp.businessUnit = entity;
        businessUnitServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.businessUnit = entity;
        businessUnitServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(businessUnitServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBusinessUnit = { id: 123 };
        businessUnitServiceStub.find.resolves(foundBusinessUnit);
        businessUnitServiceStub.retrieve.resolves([foundBusinessUnit]);

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
