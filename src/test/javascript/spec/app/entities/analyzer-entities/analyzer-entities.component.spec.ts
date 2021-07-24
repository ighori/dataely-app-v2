/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnalyzerEntitiesComponent from '@/entities/analyzer-entities/analyzer-entities.vue';
import AnalyzerEntitiesClass from '@/entities/analyzer-entities/analyzer-entities.component';
import AnalyzerEntitiesService from '@/entities/analyzer-entities/analyzer-entities.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('AnalyzerEntities Management Component', () => {
    let wrapper: Wrapper<AnalyzerEntitiesClass>;
    let comp: AnalyzerEntitiesClass;
    let analyzerEntitiesServiceStub: SinonStubbedInstance<AnalyzerEntitiesService>;

    beforeEach(() => {
      analyzerEntitiesServiceStub = sinon.createStubInstance<AnalyzerEntitiesService>(AnalyzerEntitiesService);
      analyzerEntitiesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalyzerEntitiesClass>(AnalyzerEntitiesComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          analyzerEntitiesService: () => analyzerEntitiesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analyzerEntitiesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalyzerEntitiess();
      await comp.$nextTick();

      // THEN
      expect(analyzerEntitiesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerEntities[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      analyzerEntitiesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(analyzerEntitiesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerEntities[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      analyzerEntitiesServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(analyzerEntitiesServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      analyzerEntitiesServiceStub.retrieve.reset();
      analyzerEntitiesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(analyzerEntitiesServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.analyzerEntities[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      analyzerEntitiesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAnalyzerEntities();
      await comp.$nextTick();

      // THEN
      expect(analyzerEntitiesServiceStub.delete.called).toBeTruthy();
      expect(analyzerEntitiesServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
