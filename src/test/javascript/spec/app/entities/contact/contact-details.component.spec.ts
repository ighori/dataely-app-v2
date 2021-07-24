/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ContactDetailComponent from '@/entities/contact/contact-details.vue';
import ContactClass from '@/entities/contact/contact-details.component';
import ContactService from '@/entities/contact/contact.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Contact Management Detail Component', () => {
    let wrapper: Wrapper<ContactClass>;
    let comp: ContactClass;
    let contactServiceStub: SinonStubbedInstance<ContactService>;

    beforeEach(() => {
      contactServiceStub = sinon.createStubInstance<ContactService>(ContactService);

      wrapper = shallowMount<ContactClass>(ContactDetailComponent, {
        store,
        localVue,
        router,
        provide: { contactService: () => contactServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundContact = { id: 123 };
        contactServiceStub.find.resolves(foundContact);

        // WHEN
        comp.retrieveContact(123);
        await comp.$nextTick();

        // THEN
        expect(comp.contact).toBe(foundContact);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContact = { id: 123 };
        contactServiceStub.find.resolves(foundContact);

        // WHEN
        comp.beforeRouteEnter({ params: { contactId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.contact).toBe(foundContact);
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
