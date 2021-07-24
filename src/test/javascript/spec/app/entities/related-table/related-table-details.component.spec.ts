/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RelatedTableDetailComponent from '@/entities/related-table/related-table-details.vue';
import RelatedTableClass from '@/entities/related-table/related-table-details.component';
import RelatedTableService from '@/entities/related-table/related-table.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RelatedTable Management Detail Component', () => {
    let wrapper: Wrapper<RelatedTableClass>;
    let comp: RelatedTableClass;
    let relatedTableServiceStub: SinonStubbedInstance<RelatedTableService>;

    beforeEach(() => {
      relatedTableServiceStub = sinon.createStubInstance<RelatedTableService>(RelatedTableService);

      wrapper = shallowMount<RelatedTableClass>(RelatedTableDetailComponent, {
        store,
        localVue,
        router,
        provide: { relatedTableService: () => relatedTableServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRelatedTable = { id: 123 };
        relatedTableServiceStub.find.resolves(foundRelatedTable);

        // WHEN
        comp.retrieveRelatedTable(123);
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTable).toBe(foundRelatedTable);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRelatedTable = { id: 123 };
        relatedTableServiceStub.find.resolves(foundRelatedTable);

        // WHEN
        comp.beforeRouteEnter({ params: { relatedTableId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTable).toBe(foundRelatedTable);
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
