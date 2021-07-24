/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FileConfigComponent from '@/entities/file-config/file-config.vue';
import FileConfigClass from '@/entities/file-config/file-config.component';
import FileConfigService from '@/entities/file-config/file-config.service';

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
  describe('FileConfig Management Component', () => {
    let wrapper: Wrapper<FileConfigClass>;
    let comp: FileConfigClass;
    let fileConfigServiceStub: SinonStubbedInstance<FileConfigService>;

    beforeEach(() => {
      fileConfigServiceStub = sinon.createStubInstance<FileConfigService>(FileConfigService);
      fileConfigServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FileConfigClass>(FileConfigComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          fileConfigService: () => fileConfigServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      fileConfigServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFileConfigs();
      await comp.$nextTick();

      // THEN
      expect(fileConfigServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fileConfigs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      fileConfigServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(fileConfigServiceStub.retrieve.called).toBeTruthy();
      expect(comp.fileConfigs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      fileConfigServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(fileConfigServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      fileConfigServiceStub.retrieve.reset();
      fileConfigServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(fileConfigServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.fileConfigs[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      fileConfigServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFileConfig();
      await comp.$nextTick();

      // THEN
      expect(fileConfigServiceStub.delete.called).toBeTruthy();
      expect(fileConfigServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
