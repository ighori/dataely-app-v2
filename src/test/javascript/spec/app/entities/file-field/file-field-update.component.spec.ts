/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import FileFieldUpdateComponent from '@/entities/file-field/file-field-update.vue';
import FileFieldClass from '@/entities/file-field/file-field-update.component';
import FileFieldService from '@/entities/file-field/file-field.service';

import FileInfoService from '@/entities/file-info/file-info.service';

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
  describe('FileField Management Update Component', () => {
    let wrapper: Wrapper<FileFieldClass>;
    let comp: FileFieldClass;
    let fileFieldServiceStub: SinonStubbedInstance<FileFieldService>;

    beforeEach(() => {
      fileFieldServiceStub = sinon.createStubInstance<FileFieldService>(FileFieldService);

      wrapper = shallowMount<FileFieldClass>(FileFieldUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          fileFieldService: () => fileFieldServiceStub,

          fileInfoService: () => new FileInfoService(),
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
        comp.fileField = entity;
        fileFieldServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileFieldServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fileField = entity;
        fileFieldServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileFieldServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileField = { id: 123 };
        fileFieldServiceStub.find.resolves(foundFileField);
        fileFieldServiceStub.retrieve.resolves([foundFileField]);

        // WHEN
        comp.beforeRouteEnter({ params: { fileFieldId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileField).toBe(foundFileField);
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
