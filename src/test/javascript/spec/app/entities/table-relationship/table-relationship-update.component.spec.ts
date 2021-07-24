/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TableRelationshipUpdateComponent from '@/entities/table-relationship/table-relationship-update.vue';
import TableRelationshipClass from '@/entities/table-relationship/table-relationship-update.component';
import TableRelationshipService from '@/entities/table-relationship/table-relationship.service';

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
  describe('TableRelationship Management Update Component', () => {
    let wrapper: Wrapper<TableRelationshipClass>;
    let comp: TableRelationshipClass;
    let tableRelationshipServiceStub: SinonStubbedInstance<TableRelationshipService>;

    beforeEach(() => {
      tableRelationshipServiceStub = sinon.createStubInstance<TableRelationshipService>(TableRelationshipService);

      wrapper = shallowMount<TableRelationshipClass>(TableRelationshipUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          tableRelationshipService: () => tableRelationshipServiceStub,

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
        comp.tableRelationship = entity;
        tableRelationshipServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tableRelationshipServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tableRelationship = entity;
        tableRelationshipServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tableRelationshipServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTableRelationship = { id: 123 };
        tableRelationshipServiceStub.find.resolves(foundTableRelationship);
        tableRelationshipServiceStub.retrieve.resolves([foundTableRelationship]);

        // WHEN
        comp.beforeRouteEnter({ params: { tableRelationshipId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tableRelationship).toBe(foundTableRelationship);
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
