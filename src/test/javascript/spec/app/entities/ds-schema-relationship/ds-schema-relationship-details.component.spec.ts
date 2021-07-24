/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DsSchemaRelationshipDetailComponent from '@/entities/ds-schema-relationship/ds-schema-relationship-details.vue';
import DsSchemaRelationshipClass from '@/entities/ds-schema-relationship/ds-schema-relationship-details.component';
import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DsSchemaRelationship Management Detail Component', () => {
    let wrapper: Wrapper<DsSchemaRelationshipClass>;
    let comp: DsSchemaRelationshipClass;
    let dsSchemaRelationshipServiceStub: SinonStubbedInstance<DsSchemaRelationshipService>;

    beforeEach(() => {
      dsSchemaRelationshipServiceStub = sinon.createStubInstance<DsSchemaRelationshipService>(DsSchemaRelationshipService);

      wrapper = shallowMount<DsSchemaRelationshipClass>(DsSchemaRelationshipDetailComponent, {
        store,
        localVue,
        router,
        provide: { dsSchemaRelationshipService: () => dsSchemaRelationshipServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDsSchemaRelationship = { id: 123 };
        dsSchemaRelationshipServiceStub.find.resolves(foundDsSchemaRelationship);

        // WHEN
        comp.retrieveDsSchemaRelationship(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchemaRelationship).toBe(foundDsSchemaRelationship);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDsSchemaRelationship = { id: 123 };
        dsSchemaRelationshipServiceStub.find.resolves(foundDsSchemaRelationship);

        // WHEN
        comp.beforeRouteEnter({ params: { dsSchemaRelationshipId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchemaRelationship).toBe(foundDsSchemaRelationship);
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
