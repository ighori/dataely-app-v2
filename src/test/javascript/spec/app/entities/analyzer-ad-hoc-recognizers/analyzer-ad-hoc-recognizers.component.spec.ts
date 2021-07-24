/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AnalyzerAdHocRecognizersComponent from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.vue';
import AnalyzerAdHocRecognizersClass from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.component';
import AnalyzerAdHocRecognizersService from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.service';

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
  describe('AnalyzerAdHocRecognizers Management Component', () => {
    let wrapper: Wrapper<AnalyzerAdHocRecognizersClass>;
    let comp: AnalyzerAdHocRecognizersClass;
    let analyzerAdHocRecognizersServiceStub: SinonStubbedInstance<AnalyzerAdHocRecognizersService>;

    beforeEach(() => {
      analyzerAdHocRecognizersServiceStub = sinon.createStubInstance<AnalyzerAdHocRecognizersService>(AnalyzerAdHocRecognizersService);
      analyzerAdHocRecognizersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AnalyzerAdHocRecognizersClass>(AnalyzerAdHocRecognizersComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          analyzerAdHocRecognizersService: () => analyzerAdHocRecognizersServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      analyzerAdHocRecognizersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAnalyzerAdHocRecognizerss();
      await comp.$nextTick();

      // THEN
      expect(analyzerAdHocRecognizersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerAdHocRecognizers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      analyzerAdHocRecognizersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(analyzerAdHocRecognizersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.analyzerAdHocRecognizers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      analyzerAdHocRecognizersServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(analyzerAdHocRecognizersServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      analyzerAdHocRecognizersServiceStub.retrieve.reset();
      analyzerAdHocRecognizersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(analyzerAdHocRecognizersServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.analyzerAdHocRecognizers[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      analyzerAdHocRecognizersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAnalyzerAdHocRecognizers();
      await comp.$nextTick();

      // THEN
      expect(analyzerAdHocRecognizersServiceStub.delete.called).toBeTruthy();
      expect(analyzerAdHocRecognizersServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
