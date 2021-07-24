/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TableColumnDetailComponent from '@/entities/table-column/table-column-details.vue';
import TableColumnClass from '@/entities/table-column/table-column-details.component';
import TableColumnService from '@/entities/table-column/table-column.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TableColumn Management Detail Component', () => {
    let wrapper: Wrapper<TableColumnClass>;
    let comp: TableColumnClass;
    let tableColumnServiceStub: SinonStubbedInstance<TableColumnService>;

    beforeEach(() => {
      tableColumnServiceStub = sinon.createStubInstance<TableColumnService>(TableColumnService);

      wrapper = shallowMount<TableColumnClass>(TableColumnDetailComponent, {
        store,
        localVue,
        router,
        provide: { tableColumnService: () => tableColumnServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTableColumn = { id: 123 };
        tableColumnServiceStub.find.resolves(foundTableColumn);

        // WHEN
        comp.retrieveTableColumn(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tableColumn).toBe(foundTableColumn);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTableColumn = { id: 123 };
        tableColumnServiceStub.find.resolves(foundTableColumn);

        // WHEN
        comp.beforeRouteEnter({ params: { tableColumnId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tableColumn).toBe(foundTableColumn);
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
