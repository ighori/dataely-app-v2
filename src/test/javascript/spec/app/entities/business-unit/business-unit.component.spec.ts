/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BusinessUnitComponent from '@/entities/business-unit/business-unit.vue';
import BusinessUnitClass from '@/entities/business-unit/business-unit.component';
import BusinessUnitService from '@/entities/business-unit/business-unit.service';

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
  describe('BusinessUnit Management Component', () => {
    let wrapper: Wrapper<BusinessUnitClass>;
    let comp: BusinessUnitClass;
    let businessUnitServiceStub: SinonStubbedInstance<BusinessUnitService>;

    beforeEach(() => {
      businessUnitServiceStub = sinon.createStubInstance<BusinessUnitService>(BusinessUnitService);
      businessUnitServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BusinessUnitClass>(BusinessUnitComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          businessUnitService: () => businessUnitServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBusinessUnits();
      await comp.$nextTick();

      // THEN
      expect(businessUnitServiceStub.retrieve.called).toBeTruthy();
      expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(businessUnitServiceStub.retrieve.called).toBeTruthy();
      expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      businessUnitServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(businessUnitServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      businessUnitServiceStub.retrieve.reset();
      businessUnitServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(businessUnitServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.businessUnits[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      businessUnitServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBusinessUnit();
      await comp.$nextTick();

      // THEN
      expect(businessUnitServiceStub.delete.called).toBeTruthy();
      expect(businessUnitServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
