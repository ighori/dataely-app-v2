/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DsSchemaRelationshipUpdateComponent from '@/entities/ds-schema-relationship/ds-schema-relationship-update.vue';
import DsSchemaRelationshipClass from '@/entities/ds-schema-relationship/ds-schema-relationship-update.component';
import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';

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
  describe('DsSchemaRelationship Management Update Component', () => {
    let wrapper: Wrapper<DsSchemaRelationshipClass>;
    let comp: DsSchemaRelationshipClass;
    let dsSchemaRelationshipServiceStub: SinonStubbedInstance<DsSchemaRelationshipService>;

    beforeEach(() => {
      dsSchemaRelationshipServiceStub = sinon.createStubInstance<DsSchemaRelationshipService>(DsSchemaRelationshipService);

      wrapper = shallowMount<DsSchemaRelationshipClass>(DsSchemaRelationshipUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          dsSchemaRelationshipService: () => dsSchemaRelationshipServiceStub,

          dsSchemaService: () => new DsSchemaService(),
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
        comp.dsSchemaRelationship = entity;
        dsSchemaRelationshipServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dsSchemaRelationshipServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dsSchemaRelationship = entity;
        dsSchemaRelationshipServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dsSchemaRelationshipServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDsSchemaRelationship = { id: 123 };
        dsSchemaRelationshipServiceStub.find.resolves(foundDsSchemaRelationship);
        dsSchemaRelationshipServiceStub.retrieve.resolves([foundDsSchemaRelationship]);

        // WHEN
        comp.beforeRouteEnter({ params: { dsSchemaRelationshipId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchemaRelationship).toBe(foundDsSchemaRelationship);
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
