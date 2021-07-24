/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AnalyzerAdHocRecognizersUpdateComponent from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers-update.vue';
import AnalyzerAdHocRecognizersClass from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers-update.component';
import AnalyzerAdHocRecognizersService from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.service';

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
  describe('AnalyzerAdHocRecognizers Management Update Component', () => {
    let wrapper: Wrapper<AnalyzerAdHocRecognizersClass>;
    let comp: AnalyzerAdHocRecognizersClass;
    let analyzerAdHocRecognizersServiceStub: SinonStubbedInstance<AnalyzerAdHocRecognizersService>;

    beforeEach(() => {
      analyzerAdHocRecognizersServiceStub = sinon.createStubInstance<AnalyzerAdHocRecognizersService>(AnalyzerAdHocRecognizersService);

      wrapper = shallowMount<AnalyzerAdHocRecognizersClass>(AnalyzerAdHocRecognizersUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          analyzerAdHocRecognizersService: () => analyzerAdHocRecognizersServiceStub,
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
        comp.analyzerAdHocRecognizers = entity;
        analyzerAdHocRecognizersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerAdHocRecognizersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.analyzerAdHocRecognizers = entity;
        analyzerAdHocRecognizersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(analyzerAdHocRecognizersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerAdHocRecognizers = { id: 123 };
        analyzerAdHocRecognizersServiceStub.find.resolves(foundAnalyzerAdHocRecognizers);
        analyzerAdHocRecognizersServiceStub.retrieve.resolves([foundAnalyzerAdHocRecognizers]);

        // WHEN
        comp.beforeRouteEnter({ params: { analyzerAdHocRecognizersId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerAdHocRecognizers).toBe(foundAnalyzerAdHocRecognizers);
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
