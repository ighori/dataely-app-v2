/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import FileInfoUpdateComponent from '@/entities/file-info/file-info-update.vue';
import FileInfoClass from '@/entities/file-info/file-info-update.component';
import FileInfoService from '@/entities/file-info/file-info.service';

import FileSourceService from '@/entities/file-source/file-source.service';

import FileConfigService from '@/entities/file-config/file-config.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('FileInfo Management Update Component', () => {
    let wrapper: Wrapper<FileInfoClass>;
    let comp: FileInfoClass;
    let fileInfoServiceStub: SinonStubbedInstance<FileInfoService>;

    beforeEach(() => {
      fileInfoServiceStub = sinon.createStubInstance<FileInfoService>(FileInfoService);

      wrapper = shallowMount<FileInfoClass>(FileInfoUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          fileInfoService: () => fileInfoServiceStub,

          fileSourceService: () => new FileSourceService(),

          fileConfigService: () => new FileConfigService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.fileInfo = entity;
        fileInfoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileInfoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fileInfo = entity;
        fileInfoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileInfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileInfo = { id: 123 };
        fileInfoServiceStub.find.resolves(foundFileInfo);
        fileInfoServiceStub.retrieve.resolves([foundFileInfo]);

        // WHEN
        comp.beforeRouteEnter({ params: { fileInfoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileInfo).toBe(foundFileInfo);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
