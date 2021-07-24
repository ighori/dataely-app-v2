/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TablesDefinitionUpdateComponent from '@/entities/tables-definition/tables-definition-update.vue';
import TablesDefinitionClass from '@/entities/tables-definition/tables-definition-update.component';
import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';

import TableColumnService from '@/entities/table-column/table-column.service';

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
  describe('TablesDefinition Management Update Component', () => {
    let wrapper: Wrapper<TablesDefinitionClass>;
    let comp: TablesDefinitionClass;
    let tablesDefinitionServiceStub: SinonStubbedInstance<TablesDefinitionService>;

    beforeEach(() => {
      tablesDefinitionServiceStub = sinon.createStubInstance<TablesDefinitionService>(TablesDefinitionService);

      wrapper = shallowMount<TablesDefinitionClass>(TablesDefinitionUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          tablesDefinitionService: () => tablesDefinitionServiceStub,

          dsSchemaService: () => new DsSchemaService(),

          tableColumnService: () => new TableColumnService(),
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
        comp.tablesDefinition = entity;
        tablesDefinitionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tablesDefinitionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tablesDefinition = entity;
        tablesDefinitionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tablesDefinitionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTablesDefinition = { id: 123 };
        tablesDefinitionServiceStub.find.resolves(foundTablesDefinition);
        tablesDefinitionServiceStub.retrieve.resolves([foundTablesDefinition]);

        // WHEN
        comp.beforeRouteEnter({ params: { tablesDefinitionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tablesDefinition).toBe(foundTablesDefinition);
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
