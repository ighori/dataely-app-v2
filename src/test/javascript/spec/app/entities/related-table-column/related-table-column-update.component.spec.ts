/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import RelatedTableColumnUpdateComponent from '@/entities/related-table-column/related-table-column-update.vue';
import RelatedTableColumnClass from '@/entities/related-table-column/related-table-column-update.component';
import RelatedTableColumnService from '@/entities/related-table-column/related-table-column.service';

import RelatedTableService from '@/entities/related-table/related-table.service';

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
  describe('RelatedTableColumn Management Update Component', () => {
    let wrapper: Wrapper<RelatedTableColumnClass>;
    let comp: RelatedTableColumnClass;
    let relatedTableColumnServiceStub: SinonStubbedInstance<RelatedTableColumnService>;

    beforeEach(() => {
      relatedTableColumnServiceStub = sinon.createStubInstance<RelatedTableColumnService>(RelatedTableColumnService);

      wrapper = shallowMount<RelatedTableColumnClass>(RelatedTableColumnUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          relatedTableColumnService: () => relatedTableColumnServiceStub,

          relatedTableService: () => new RelatedTableService(),
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
        comp.relatedTableColumn = entity;
        relatedTableColumnServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(relatedTableColumnServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.relatedTableColumn = entity;
        relatedTableColumnServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(relatedTableColumnServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRelatedTableColumn = { id: 123 };
        relatedTableColumnServiceStub.find.resolves(foundRelatedTableColumn);
        relatedTableColumnServiceStub.retrieve.resolves([foundRelatedTableColumn]);

        // WHEN
        comp.beforeRouteEnter({ params: { relatedTableColumnId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTableColumn).toBe(foundRelatedTableColumn);
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
