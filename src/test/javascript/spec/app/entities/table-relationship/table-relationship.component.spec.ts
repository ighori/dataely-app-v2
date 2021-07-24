/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TableRelationshipComponent from '@/entities/table-relationship/table-relationship.vue';
import TableRelationshipClass from '@/entities/table-relationship/table-relationship.component';
import TableRelationshipService from '@/entities/table-relationship/table-relationship.service';

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
  describe('TableRelationship Management Component', () => {
    let wrapper: Wrapper<TableRelationshipClass>;
    let comp: TableRelationshipClass;
    let tableRelationshipServiceStub: SinonStubbedInstance<TableRelationshipService>;

    beforeEach(() => {
      tableRelationshipServiceStub = sinon.createStubInstance<TableRelationshipService>(TableRelationshipService);
      tableRelationshipServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TableRelationshipClass>(TableRelationshipComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          tableRelationshipService: () => tableRelationshipServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tableRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTableRelationships();
      await comp.$nextTick();

      // THEN
      expect(tableRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tableRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      tableRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(tableRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tableRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      tableRelationshipServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(tableRelationshipServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      tableRelationshipServiceStub.retrieve.reset();
      tableRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(tableRelationshipServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.tableRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      tableRelationshipServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTableRelationship();
      await comp.$nextTick();

      // THEN
      expect(tableRelationshipServiceStub.delete.called).toBeTruthy();
      expect(tableRelationshipServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
