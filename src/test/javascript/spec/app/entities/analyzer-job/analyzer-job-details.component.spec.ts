/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalyzerJobDetailComponent from '@/entities/analyzer-job/analyzer-job-details.vue';
import AnalyzerJobClass from '@/entities/analyzer-job/analyzer-job-details.component';
import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnalyzerJob Management Detail Component', () => {
    let wrapper: Wrapper<AnalyzerJobClass>;
    let comp: AnalyzerJobClass;
    let analyzerJobServiceStub: SinonStubbedInstance<AnalyzerJobService>;

    beforeEach(() => {
      analyzerJobServiceStub = sinon.createStubInstance<AnalyzerJobService>(AnalyzerJobService);

      wrapper = shallowMount<AnalyzerJobClass>(AnalyzerJobDetailComponent, {
        store,
        localVue,
        router,
        provide: { analyzerJobService: () => analyzerJobServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalyzerJob = { id: 123 };
        analyzerJobServiceStub.find.resolves(foundAnalyzerJob);

        // WHEN
        comp.retrieveAnalyzerJob(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerJob).toBe(foundAnalyzerJob);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerJob = { id: 123 };
        analyzerJobServiceStub.find.resolves(foundAnalyzerJob);

        // WHEN
        comp.beforeRouteEnter({ params: { analyzerJobId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerJob).toBe(foundAnalyzerJob);
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
