/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalyzerRecognizersDetailComponent from '@/entities/analyzer-recognizers/analyzer-recognizers-details.vue';
import AnalyzerRecognizersClass from '@/entities/analyzer-recognizers/analyzer-recognizers-details.component';
import AnalyzerRecognizersService from '@/entities/analyzer-recognizers/analyzer-recognizers.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnalyzerRecognizers Management Detail Component', () => {
    let wrapper: Wrapper<AnalyzerRecognizersClass>;
    let comp: AnalyzerRecognizersClass;
    let analyzerRecognizersServiceStub: SinonStubbedInstance<AnalyzerRecognizersService>;

    beforeEach(() => {
      analyzerRecognizersServiceStub = sinon.createStubInstance<AnalyzerRecognizersService>(AnalyzerRecognizersService);

      wrapper = shallowMount<AnalyzerRecognizersClass>(AnalyzerRecognizersDetailComponent, {
        store,
        localVue,
        router,
        provide: { analyzerRecognizersService: () => analyzerRecognizersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalyzerRecognizers = { id: 123 };
        analyzerRecognizersServiceStub.find.resolves(foundAnalyzerRecognizers);

        // WHEN
        comp.retrieveAnalyzerRecognizers(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerRecognizers).toBe(foundAnalyzerRecognizers);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerRecognizers = { id: 123 };
        analyzerRecognizersServiceStub.find.resolves(foundAnalyzerRecognizers);

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
