/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AnalyzerRecognizersUpdateComponent from '@/entities/analyzer-recognizers/analyzer-recognizers-update.vue';
import AnalyzerRecognizersClass from '@/entities/analyzer-recognizers/analyzer-recognizers-update.component';
import AnalyzerRecognizersService from '@/entities/analyzer-recognizers/analyzer-recognizers.service';

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
  describe('AnalyzerRecognizers Management Update Component', () => {
    let wrapper: Wrapper<AnalyzerRecognizersClass>;
    let comp: AnalyzerRecognizersClass;
    let analyzerRecognizersServiceStub: SinonStubbedInstance<AnalyzerRecognizersService>;

    beforeEach(() => {
      analyzerRecognizersServiceStub = sinon.createStubInstance<AnalyzerRecognizersService>(AnalyzerRecognizersService);

      wrapper = shallowMount<AnalyzerRecognizersClass>(AnalyzerRecognizersUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          analyzerRecognizersService: () => analyzerRecognizersServiceStub,
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
        comp.analyzerRecognizers = entity;
        analyzerRecognizersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerRecognizersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.analyzerRecognizers = entity;
        analyzerRecognizersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerRecognizersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerRecognizers = { id: 123 };
        analyzerRecognizersServiceStub.find.resolves(foundAnalyzerRecognizers);
        analyzerRecognizersServiceStub.retrieve.resolves([foundAnalyzerRecognizers]);

        // WHEN
        comp.beforeRouteEnter({ params: { analyzerRecognizersId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerRecognizers).toBe(foundAnalyzerRecognizers);
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
