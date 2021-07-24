/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnalyzerEntitiesDetailComponent from '@/entities/analyzer-entities/analyzer-entities-details.vue';
import AnalyzerEntitiesClass from '@/entities/analyzer-entities/analyzer-entities-details.component';
import AnalyzerEntitiesService from '@/entities/analyzer-entities/analyzer-entities.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AnalyzerEntities Management Detail Component', () => {
    let wrapper: Wrapper<AnalyzerEntitiesClass>;
    let comp: AnalyzerEntitiesClass;
    let analyzerEntitiesServiceStub: SinonStubbedInstance<AnalyzerEntitiesService>;

    beforeEach(() => {
      analyzerEntitiesServiceStub = sinon.createStubInstance<AnalyzerEntitiesService>(AnalyzerEntitiesService);

      wrapper = shallowMount<AnalyzerEntitiesClass>(AnalyzerEntitiesDetailComponent, {
        store,
        localVue,
        router,
        provide: { analyzerEntitiesService: () => analyzerEntitiesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnalyzerEntities = { id: 123 };
        analyzerEntitiesServiceStub.find.resolves(foundAnalyzerEntities);

        // WHEN
        comp.retrieveAnalyzerEntities(123);
        await comp.$nextTick();

        // THEN
        expect(comp.analyzerEntities).toBe(foundAnalyzerEntities);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnalyzerEntities = { id: 123 };
        analyzerEntitiesServiceStub.find.resolves(foundAnalyzerEntities);

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
