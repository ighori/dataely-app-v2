/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TablesDefinitionComponent from '@/entities/tables-definition/tables-definition.vue';
import TablesDefinitionClass from '@/entities/tables-definition/tables-definition.component';
import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';

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
  describe('TablesDefinition Management Component', () => {
    let wrapper: Wrapper<TablesDefinitionClass>;
    let comp: TablesDefinitionClass;
    let tablesDefinitionServiceStub: SinonStubbedInstance<TablesDefinitionService>;

    beforeEach(() => {
      tablesDefinitionServiceStub = sinon.createStubInstance<TablesDefinitionService>(TablesDefinitionService);
      tablesDefinitionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TablesDefinitionClass>(TablesDefinitionComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          tablesDefinitionService: () => tablesDefinitionServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tablesDefinitionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTablesDefinitions();
      await comp.$nextTick();

      // THEN
      expect(tablesDefinitionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tablesDefinitions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      tablesDefinitionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(tablesDefinitionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tablesDefinitions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      tablesDefinitionServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(tablesDefinitionServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      tablesDefinitionServiceStub.retrieve.reset();
      tablesDefinitionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(tablesDefinitionServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.tablesDefinitions[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      tablesDefinitionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTablesDefinition();
      await comp.$nextTick();

      // THEN
      expect(tablesDefinitionServiceStub.delete.called).toBeTruthy();
      expect(tablesDefinitionServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
