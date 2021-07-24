/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DsSchemaDetailComponent from '@/entities/ds-schema/ds-schema-details.vue';
import DsSchemaClass from '@/entities/ds-schema/ds-schema-details.component';
import DsSchemaService from '@/entities/ds-schema/ds-schema.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DsSchema Management Detail Component', () => {
    let wrapper: Wrapper<DsSchemaClass>;
    let comp: DsSchemaClass;
    let dsSchemaServiceStub: SinonStubbedInstance<DsSchemaService>;

    beforeEach(() => {
      dsSchemaServiceStub = sinon.createStubInstance<DsSchemaService>(DsSchemaService);

      wrapper = shallowMount<DsSchemaClass>(DsSchemaDetailComponent, {
        store,
        localVue,
        router,
        provide: { dsSchemaService: () => dsSchemaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDsSchema = { id: 123 };
        dsSchemaServiceStub.find.resolves(foundDsSchema);

        // WHEN
        comp.retrieveDsSchema(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchema).toBe(foundDsSchema);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDsSchema = { id: 123 };
        dsSchemaServiceStub.find.resolves(foundDsSchema);

        // WHEN
        comp.beforeRouteEnter({ params: { dsSchemaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dsSchema).toBe(foundDsSchema);
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
