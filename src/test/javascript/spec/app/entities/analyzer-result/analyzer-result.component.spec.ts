/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnalyzerResultComponent from '@/entities/analyzer-result/analyzer-result.vue';
import AnalyzerResultClass from '@/entities/analyzer-result/analyzer-result.component';
import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';

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
  describe('AnalyzerResult Management Component', () => {
    let wrapper: Wrapper<AnalyzerResultClass>;
    let comp: AnalyzerResultClass;
    let analyzerResultServiceStub: SinonStubbedInstance<AnalyzerResultService>;

    beforeEach(() => {
      analyzerResultServiceStub = sinon.createStubInstance<AnalyzerResultService>(AnalyzerResultService);
      analyzerResultServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalyzerResultClass>(AnalyzerResultComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          analyzerResultService: () => analyzerResultServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analyzerResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalyzerResults();
      await comp.$nextTick();

      // THEN
      expect(analyzerResultServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerResults[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      analyzerResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(analyzerResultServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerResults[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      analyzerResultServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(analyzerResultServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      analyzerResultServiceStub.retrieve.reset();
      analyzerResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(analyzerResultServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.analyzerResults[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      analyzerResultServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAnalyzerResult();
      await comp.$nextTick();

      // THEN
      expect(analyzerResultServiceStub.delete.called).toBeTruthy();
      expect(analyzerResultServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
