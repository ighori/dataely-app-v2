/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnalyzerJobComponent from '@/entities/analyzer-job/analyzer-job.vue';
import AnalyzerJobClass from '@/entities/analyzer-job/analyzer-job.component';
import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';

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
  describe('AnalyzerJob Management Component', () => {
    let wrapper: Wrapper<AnalyzerJobClass>;
    let comp: AnalyzerJobClass;
    let analyzerJobServiceStub: SinonStubbedInstance<AnalyzerJobService>;

    beforeEach(() => {
      analyzerJobServiceStub = sinon.createStubInstance<AnalyzerJobService>(AnalyzerJobService);
      analyzerJobServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalyzerJobClass>(AnalyzerJobComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          analyzerJobService: () => analyzerJobServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analyzerJobServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalyzerJobs();
      await comp.$nextTick();

      // THEN
      expect(analyzerJobServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerJobs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      analyzerJobServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(analyzerJobServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerJobs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      analyzerJobServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(analyzerJobServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      analyzerJobServiceStub.retrieve.reset();
      analyzerJobServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(analyzerJobServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.analyzerJobs[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      analyzerJobServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAnalyzerJob();
      await comp.$nextTick();

      // THEN
      expect(analyzerJobServiceStub.delete.called).toBeTruthy();
      expect(analyzerJobServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
