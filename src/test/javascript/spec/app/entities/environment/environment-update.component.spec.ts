/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import EnvironmentUpdateComponent from '@/entities/environment/environment-update.vue';
import EnvironmentClass from '@/entities/environment/environment-update.component';
import EnvironmentService from '@/entities/environment/environment.service';

import ContactService from '@/entities/contact/contact.service';

import ApplicationService from '@/entities/application/application.service';

import DataSourceService from '@/entities/data-source/data-source.service';

import FileSourceService from '@/entities/file-source/file-source.service';

import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';

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
  describe('Environment Management Update Component', () => {
    let wrapper: Wrapper<EnvironmentClass>;
    let comp: EnvironmentClass;
    let environmentServiceStub: SinonStubbedInstance<EnvironmentService>;

    beforeEach(() => {
      environmentServiceStub = sinon.createStubInstance<EnvironmentService>(EnvironmentService);

      wrapper = shallowMount<EnvironmentClass>(EnvironmentUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          environmentService: () => environmentServiceStub,

          contactService: () => new ContactService(),

          applicationService: () => new ApplicationService(),

          dataSourceService: () => new DataSourceService(),

          fileSourceService: () => new FileSourceService(),

          analyzerJobService: () => new AnalyzerJobService(),
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
        comp.environment = entity;
        environmentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(environmentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.environment = entity;
        environmentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(environmentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEnvironment = { id: 123 };
        environmentServiceStub.find.resolves(foundEnvironment);
        environmentServiceStub.retrieve.resolves([foundEnvironment]);

        // WHEN
        comp.beforeRouteEnter({ params: { environmentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.environment).toBe(foundEnvironment);
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
