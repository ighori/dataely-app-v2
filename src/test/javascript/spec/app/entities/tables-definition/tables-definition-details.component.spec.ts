/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TablesDefinitionDetailComponent from '@/entities/tables-definition/tables-definition-details.vue';
import TablesDefinitionClass from '@/entities/tables-definition/tables-definition-details.component';
import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TablesDefinition Management Detail Component', () => {
    let wrapper: Wrapper<TablesDefinitionClass>;
    let comp: TablesDefinitionClass;
    let tablesDefinitionServiceStub: SinonStubbedInstance<TablesDefinitionService>;

    beforeEach(() => {
      tablesDefinitionServiceStub = sinon.createStubInstance<TablesDefinitionService>(TablesDefinitionService);

      wrapper = shallowMount<TablesDefinitionClass>(TablesDefinitionDetailComponent, {
        store,
        localVue,
        router,
        provide: { tablesDefinitionService: () => tablesDefinitionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTablesDefinition = { id: 123 };
        tablesDefinitionServiceStub.find.resolves(foundTablesDefinition);

        // WHEN
        comp.retrieveTablesDefinition(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tablesDefinition).toBe(foundTablesDefinition);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTablesDefinition = { id: 123 };
        tablesDefinitionServiceStub.find.resolves(foundTablesDefinition);

        // WHEN
        comp.beforeRouteEnter({ params: { tablesDefinitionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tablesDefinition).toBe(foundTablesDefinition);
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
