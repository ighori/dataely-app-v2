/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RelatedTableColumnDetailComponent from '@/entities/related-table-column/related-table-column-details.vue';
import RelatedTableColumnClass from '@/entities/related-table-column/related-table-column-details.component';
import RelatedTableColumnService from '@/entities/related-table-column/related-table-column.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RelatedTableColumn Management Detail Component', () => {
    let wrapper: Wrapper<RelatedTableColumnClass>;
    let comp: RelatedTableColumnClass;
    let relatedTableColumnServiceStub: SinonStubbedInstance<RelatedTableColumnService>;

    beforeEach(() => {
      relatedTableColumnServiceStub = sinon.createStubInstance<RelatedTableColumnService>(RelatedTableColumnService);

      wrapper = shallowMount<RelatedTableColumnClass>(RelatedTableColumnDetailComponent, {
        store,
        localVue,
        router,
        provide: { relatedTableColumnService: () => relatedTableColumnServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRelatedTableColumn = { id: 123 };
        relatedTableColumnServiceStub.find.resolves(foundRelatedTableColumn);

        // WHEN
        comp.retrieveRelatedTableColumn(123);
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTableColumn).toBe(foundRelatedTableColumn);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRelatedTableColumn = { id: 123 };
        relatedTableColumnServiceStub.find.resolves(foundRelatedTableColumn);

        // WHEN
        comp.beforeRouteEnter({ params: { relatedTableColumnId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.relatedTableColumn).toBe(foundRelatedTableColumn);
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
