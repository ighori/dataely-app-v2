/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DataSourceUpdateComponent from '@/entities/data-source/data-source-update.vue';
import DataSourceClass from '@/entities/data-source/data-source-update.component';
import DataSourceService from '@/entities/data-source/data-source.service';

import EnvironmentService from '@/entities/environment/environment.service';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';

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
  describe('DataSource Management Update Component', () => {
    let wrapper: Wrapper<DataSourceClass>;
    let comp: DataSourceClass;
    let dataSourceServiceStub: SinonStubbedInstance<DataSourceService>;

    beforeEach(() => {
      dataSourceServiceStub = sinon.createStubInstance<DataSourceService>(DataSourceService);

      wrapper = shallowMount<DataSourceClass>(DataSourceUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          dataSourceService: () => dataSourceServiceStub,

          environmentService: () => new EnvironmentService(),

          dsSchemaService: () => new DsSchemaService(),

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
        comp.dataSource = entity;
        dataSourceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dataSourceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dataSource = entity;
        dataSourceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dataSourceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDataSource = { id: 123 };
        dataSourceServiceStub.find.resolves(foundDataSource);
        dataSourceServiceStub.retrieve.resolves([foundDataSource]);

        // WHEN
        comp.beforeRouteEnter({ params: { dataSourceId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dataSource).toBe(foundDataSource);
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
