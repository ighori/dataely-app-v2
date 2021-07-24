/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RelatedTableColumnComponent from '@/entities/related-table-column/related-table-column.vue';
import RelatedTableColumnClass from '@/entities/related-table-column/related-table-column.component';
import RelatedTableColumnService from '@/entities/related-table-column/related-table-column.service';

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
  describe('RelatedTableColumn Management Component', () => {
    let wrapper: Wrapper<RelatedTableColumnClass>;
    let comp: RelatedTableColumnClass;
    let relatedTableColumnServiceStub: SinonStubbedInstance<RelatedTableColumnService>;

    beforeEach(() => {
      relatedTableColumnServiceStub = sinon.createStubInstance<RelatedTableColumnService>(RelatedTableColumnService);
      relatedTableColumnServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RelatedTableColumnClass>(RelatedTableColumnComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          relatedTableColumnService: () => relatedTableColumnServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      relatedTableColumnServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRelatedTableColumns();
      await comp.$nextTick();

      // THEN
      expect(relatedTableColumnServiceStub.retrieve.called).toBeTruthy();
      expect(comp.relatedTableColumns[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      relatedTableColumnServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(relatedTableColumnServiceStub.retrieve.called).toBeTruthy();
      expect(comp.relatedTableColumns[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      relatedTableColumnServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(relatedTableColumnServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      relatedTableColumnServiceStub.retrieve.reset();
      relatedTableColumnServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(relatedTableColumnServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.relatedTableColumns[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      relatedTableColumnServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeRelatedTableColumn();
      await comp.$nextTick();

      // THEN
      expect(relatedTableColumnServiceStub.delete.called).toBeTruthy();
      expect(relatedTableColumnServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
