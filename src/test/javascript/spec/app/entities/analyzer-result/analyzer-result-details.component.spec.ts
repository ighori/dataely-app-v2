/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalyzerResultDetailComponent from '@/entities/analyzer-result/analyzer-result-details.vue';
import AnalyzerResultClass from '@/entities/analyzer-result/analyzer-result-details.component';
import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnalyzerResult Management Detail Component', () => {
    let wrapper: Wrapper<AnalyzerResultClass>;
    let comp: AnalyzerResultClass;
    let analyzerResultServiceStub: SinonStubbedInstance<AnalyzerResultService>;

    beforeEach(() => {
      analyzerResultServiceStub = sinon.createStubInstance<AnalyzerResultService>(AnalyzerResultService);

      wrapper = shallowMount<AnalyzerResultClass>(AnalyzerResultDetailComponent, {
        store,
        localVue,
        router,
        provide: { analyzerResultService: () => analyzerResultServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalyzerResult = { id: 123 };
        analyzerResultServiceStub.find.resolves(foundAnalyzerResult);

        // WHEN
        comp.retrieveAnalyzerResult(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerResult).toBe(foundAnalyzerResult);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerResult = { id: 123 };
        analyzerResultServiceStub.find.resolves(foundAnalyzerResult);

        // WHEN
        comp.beforeRouteEnter({ params: { analyzerResultId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerResult).toBe(foundAnalyzerResult);
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
