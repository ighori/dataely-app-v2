/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TableColumnUpdateComponent from '@/entities/table-column/table-column-update.vue';
import TableColumnClass from '@/entities/table-column/table-column-update.component';
import TableColumnService from '@/entities/table-column/table-column.service';

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
  describe('TableColumn Management Update Component', () => {
    let wrapper: Wrapper<TableColumnClass>;
    let comp: TableColumnClass;
    let tableColumnServiceStub: SinonStubbedInstance<TableColumnService>;

    beforeEach(() => {
      tableColumnServiceStub = sinon.createStubInstance<TableColumnService>(TableColumnService);

      wrapper = shallowMount<TableColumnClass>(TableColumnUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          tableColumnService: () => tableColumnServiceStub,

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
        comp.tableColumn = entity;
        tableColumnServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tableColumnServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tableColumn = entity;
        tableColumnServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tableColumnServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTableColumn = { id: 123 };
        tableColumnServiceStub.find.resolves(foundTableColumn);
        tableColumnServiceStub.retrieve.resolves([foundTableColumn]);

        // WHEN
        comp.beforeRouteEnter({ params: { tableColumnId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tableColumn).toBe(foundTableColumn);
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
