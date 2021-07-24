/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DataSourceDetailComponent from '@/entities/data-source/data-source-details.vue';
import DataSourceClass from '@/entities/data-source/data-source-details.component';
import DataSourceService from '@/entities/data-source/data-source.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DataSource Management Detail Component', () => {
    let wrapper: Wrapper<DataSourceClass>;
    let comp: DataSourceClass;
    let dataSourceServiceStub: SinonStubbedInstance<DataSourceService>;

    beforeEach(() => {
      dataSourceServiceStub = sinon.createStubInstance<DataSourceService>(DataSourceService);

      wrapper = shallowMount<DataSourceClass>(DataSourceDetailComponent, {
        store,
        localVue,
        router,
        provide: { dataSourceService: () => dataSourceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDataSource = { id: 123 };
        dataSourceServiceStub.find.resolves(foundDataSource);

        // WHEN
        comp.retrieveDataSource(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dataSource).toBe(foundDataSource);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDataSource = { id: 123 };
        dataSourceServiceStub.find.resolves(foundDataSource);

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
