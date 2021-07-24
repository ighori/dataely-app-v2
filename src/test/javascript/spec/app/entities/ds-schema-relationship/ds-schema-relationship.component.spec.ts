/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DsSchemaRelationshipComponent from '@/entities/ds-schema-relationship/ds-schema-relationship.vue';
import DsSchemaRelationshipClass from '@/entities/ds-schema-relationship/ds-schema-relationship.component';
import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';

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
  describe('DsSchemaRelationship Management Component', () => {
    let wrapper: Wrapper<DsSchemaRelationshipClass>;
    let comp: DsSchemaRelationshipClass;
    let dsSchemaRelationshipServiceStub: SinonStubbedInstance<DsSchemaRelationshipService>;

    beforeEach(() => {
      dsSchemaRelationshipServiceStub = sinon.createStubInstance<DsSchemaRelationshipService>(DsSchemaRelationshipService);
      dsSchemaRelationshipServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DsSchemaRelationshipClass>(DsSchemaRelationshipComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          dsSchemaRelationshipService: () => dsSchemaRelationshipServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dsSchemaRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDsSchemaRelationships();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dsSchemaRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      dsSchemaRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(dsSchemaRelationshipServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dsSchemaRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      dsSchemaRelationshipServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(dsSchemaRelationshipServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      dsSchemaRelationshipServiceStub.retrieve.reset();
      dsSchemaRelationshipServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaRelationshipServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.dsSchemaRelationships[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      dsSchemaRelationshipServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDsSchemaRelationship();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaRelationshipServiceStub.delete.called).toBeTruthy();
      expect(dsSchemaRelationshipServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
