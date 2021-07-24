/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DsSchemaComponent from '@/entities/ds-schema/ds-schema.vue';
import DsSchemaClass from '@/entities/ds-schema/ds-schema.component';
import DsSchemaService from '@/entities/ds-schema/ds-schema.service';

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
  describe('DsSchema Management Component', () => {
    let wrapper: Wrapper<DsSchemaClass>;
    let comp: DsSchemaClass;
    let dsSchemaServiceStub: SinonStubbedInstance<DsSchemaService>;

    beforeEach(() => {
      dsSchemaServiceStub = sinon.createStubInstance<DsSchemaService>(DsSchemaService);
      dsSchemaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DsSchemaClass>(DsSchemaComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          dsSchemaService: () => dsSchemaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dsSchemaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDsSchemas();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dsSchemas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      dsSchemaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(dsSchemaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dsSchemas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      dsSchemaServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(dsSchemaServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      dsSchemaServiceStub.retrieve.reset();
      dsSchemaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.dsSchemas[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      dsSchemaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDsSchema();
      await comp.$nextTick();

      // THEN
      expect(dsSchemaServiceStub.delete.called).toBeTruthy();
      expect(dsSchemaServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
