/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalyzerAdHocRecognizersDetailComponent from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers-details.vue';
import AnalyzerAdHocRecognizersClass from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers-details.component';
import AnalyzerAdHocRecognizersService from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnalyzerAdHocRecognizers Management Detail Component', () => {
    let wrapper: Wrapper<AnalyzerAdHocRecognizersClass>;
    let comp: AnalyzerAdHocRecognizersClass;
    let analyzerAdHocRecognizersServiceStub: SinonStubbedInstance<AnalyzerAdHocRecognizersService>;

    beforeEach(() => {
      analyzerAdHocRecognizersServiceStub = sinon.createStubInstance<AnalyzerAdHocRecognizersService>(AnalyzerAdHocRecognizersService);

      wrapper = shallowMount<AnalyzerAdHocRecognizersClass>(AnalyzerAdHocRecognizersDetailComponent, {
        store,
        localVue,
        router,
        provide: { analyzerAdHocRecognizersService: () => analyzerAdHocRecognizersServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalyzerAdHocRecognizers = { id: 123 };
        analyzerAdHocRecognizersServiceStub.find.resolves(foundAnalyzerAdHocRecognizers);

        // WHEN
        comp.retrieveAnalyzerAdHocRecognizers(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerAdHocRecognizers).toBe(foundAnalyzerAdHocRecognizers);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerAdHocRecognizers = { id: 123 };
        analyzerAdHocRecognizersServiceStub.find.resolves(foundAnalyzerAdHocRecognizers);

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
