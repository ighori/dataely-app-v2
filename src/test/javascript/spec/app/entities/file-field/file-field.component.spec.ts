/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FileFieldComponent from '@/entities/file-field/file-field.vue';
import FileFieldClass from '@/entities/file-field/file-field.component';
import FileFieldService from '@/entities/file-field/file-field.service';

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
  describe('FileField Management Component', () => {
    let wrapper: Wrapper<FileFieldClass>;
    let comp: FileFieldClass;
    let fileFieldServiceStub: SinonStubbedInstance<FileFieldService>;

    beforeEach(() => {
      fileFieldServiceStub = sinon.createStubInstance<FileFieldService>(FileFieldService);
      fileFieldServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FileFieldClass>(FileFieldComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          fileFieldService: () => fileFieldServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      fileFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFileFields();
      await comp.$nextTick();

      // THEN
      expect(fileFieldServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fileFields[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      fileFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(fileFieldServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fileFields[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      fileFieldServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(fileFieldServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      fileFieldServiceStub.retrieve.reset();
      fileFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(fileFieldServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.fileFields[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      fileFieldServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFileField();
      await comp.$nextTick();

      // THEN
      expect(fileFieldServiceStub.delete.called).toBeTruthy();
      expect(fileFieldServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
