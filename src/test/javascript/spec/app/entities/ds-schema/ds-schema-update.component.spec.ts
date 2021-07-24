/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DsSchemaUpdateComponent from '@/entities/ds-schema/ds-schema-update.vue';
import DsSchemaClass from '@/entities/ds-schema/ds-schema-update.component';
import DsSchemaService from '@/entities/ds-schema/ds-schema.service';

import DataSourceService from '@/entities/data-source/data-source.service';

import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';

import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';

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
  describe('DsSchema Management Update Component', () => {
    let wrapper: Wrapper<DsSchemaClass>;
    let comp: DsSchemaClass;
    let dsSchemaServiceStub: SinonStubbedInstance<DsSchemaService>;

    beforeEach(() => {
      dsSchemaServiceStub = sinon.createStubInstance<DsSchemaService>(DsSchemaService);

      wrapper = shallowMount<DsSchemaClass>(DsSchemaUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          dsSchemaService: () => dsSchemaServiceStub,

          dataSourceService: () => new DataSourceService(),

          dsSchemaRelationshipService: () => new DsSchemaRelationshipService(),

          tablesDefinitionService: () => new TablesDefinitionService(),
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
        comp.dsSchema = entity;
        dsSchemaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dsSchemaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dsSchema = entity;
        dsSchemaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dsSchemaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDsSchema = { id: 123 };
        dsSchemaServiceStub.find.resolves(foundDsSchema);
        dsSchemaServiceStub.retrieve.resolves([foundDsSchema]);

        // WHEN
        comp.beforeRouteEnter({ params: { dsSchemaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchema).toBe(foundDsSchema);
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
