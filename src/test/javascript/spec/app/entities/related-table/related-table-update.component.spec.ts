/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import RelatedTableUpdateComponent from '@/entities/related-table/related-table-update.vue';
import RelatedTableClass from '@/entities/related-table/related-table-update.component';
import RelatedTableService from '@/entities/related-table/related-table.service';

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
  describe('RelatedTable Management Update Component', () => {
    let wrapper: Wrapper<RelatedTableClass>;
    let comp: RelatedTableClass;
    let relatedTableServiceStub: SinonStubbedInstance<RelatedTableService>;

    beforeEach(() => {
      relatedTableServiceStub = sinon.createStubInstance<RelatedTableService>(RelatedTableService);

      wrapper = shallowMount<RelatedTableClass>(RelatedTableUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          relatedTableService: () => relatedTableServiceStub,

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
        comp.relatedTable = entity;
        relatedTableServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(relatedTableServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.relatedTable = entity;
        relatedTableServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(relatedTableServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRelatedTable = { id: 123 };
        relatedTableServiceStub.find.resolves(foundRelatedTable);
        relatedTableServiceStub.retrieve.resolves([foundRelatedTable]);

        // WHEN
        comp.beforeRouteEnter({ params: { relatedTableId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTable).toBe(foundRelatedTable);
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
