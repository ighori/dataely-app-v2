/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TableRelationshipDetailComponent from '@/entities/table-relationship/table-relationship-details.vue';
import TableRelationshipClass from '@/entities/table-relationship/table-relationship-details.component';
import TableRelationshipService from '@/entities/table-relationship/table-relationship.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TableRelationship Management Detail Component', () => {
    let wrapper: Wrapper<TableRelationshipClass>;
    let comp: TableRelationshipClass;
    let tableRelationshipServiceStub: SinonStubbedInstance<TableRelationshipService>;

    beforeEach(() => {
      tableRelationshipServiceStub = sinon.createStubInstance<TableRelationshipService>(TableRelationshipService);

      wrapper = shallowMount<TableRelationshipClass>(TableRelationshipDetailComponent, {
        store,
        localVue,
        router,
        provide: { tableRelationshipService: () => tableRelationshipServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTableRelationship = { id: 123 };
        tableRelationshipServiceStub.find.resolves(foundTableRelationship);

        // WHEN
        comp.retrieveTableRelationship(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tableRelationship).toBe(foundTableRelationship);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTableRelationship = { id: 123 };
        tableRelationshipServiceStub.find.resolves(foundTableRelationship);

        // WHEN
        comp.beforeRouteEnter({ params: { tableRelationshipId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tableRelationship).toBe(foundTableRelationship);
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
