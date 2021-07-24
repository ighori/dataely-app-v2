/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import FileSourceUpdateComponent from '@/entities/file-source/file-source-update.vue';
import FileSourceClass from '@/entities/file-source/file-source-update.component';
import FileSourceService from '@/entities/file-source/file-source.service';

import EnvironmentService from '@/entities/environment/environment.service';

import FileInfoService from '@/entities/file-info/file-info.service';

import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';

import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';

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
  describe('FileSource Management Update Component', () => {
    let wrapper: Wrapper<FileSourceClass>;
    let comp: FileSourceClass;
    let fileSourceServiceStub: SinonStubbedInstance<FileSourceService>;

    beforeEach(() => {
      fileSourceServiceStub = sinon.createStubInstance<FileSourceService>(FileSourceService);

      wrapper = shallowMount<FileSourceClass>(FileSourceUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          fileSourceService: () => fileSourceServiceStub,

          environmentService: () => new EnvironmentService(),

          fileInfoService: () => new FileInfoService(),

          analyzerJobService: () => new AnalyzerJobService(),

          analyzerResultService: () => new AnalyzerResultService(),
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
        comp.fileSource = entity;
        fileSourceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileSourceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fileSource = entity;
        fileSourceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fileSourceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFileSource = { id: 123 };
        fileSourceServiceStub.find.resolves(foundFileSource);
        fileSourceServiceStub.retrieve.resolves([foundFileSource]);

        // WHEN
        comp.beforeRouteEnter({ params: { fileSourceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fileSource).toBe(foundFileSource);
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
