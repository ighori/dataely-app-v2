/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AnalyzerEntitiesUpdateComponent from '@/entities/analyzer-entities/analyzer-entities-update.vue';
import AnalyzerEntitiesClass from '@/entities/analyzer-entities/analyzer-entities-update.component';
import AnalyzerEntitiesService from '@/entities/analyzer-entities/analyzer-entities.service';

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
  describe('AnalyzerEntities Management Update Component', () => {
    let wrapper: Wrapper<AnalyzerEntitiesClass>;
    let comp: AnalyzerEntitiesClass;
    let analyzerEntitiesServiceStub: SinonStubbedInstance<AnalyzerEntitiesService>;

    beforeEach(() => {
      analyzerEntitiesServiceStub = sinon.createStubInstance<AnalyzerEntitiesService>(AnalyzerEntitiesService);

      wrapper = shallowMount<AnalyzerEntitiesClass>(AnalyzerEntitiesUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          analyzerEntitiesService: () => analyzerEntitiesServiceStub,
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
        comp.analyzerEntities = entity;
        analyzerEntitiesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerEntitiesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.analyzerEntities = entity;
        analyzerEntitiesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerEntitiesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerEntities = { id: 123 };
        analyzerEntitiesServiceStub.find.resolves(foundAnalyzerEntities);
        analyzerEntitiesServiceStub.retrieve.resolves([foundAnalyzerEntities]);

        // WHEN
        comp.beforeRouteEnter({ params: { analyzerEntitiesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerEntities).toBe(foundAnalyzerEntities);
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
