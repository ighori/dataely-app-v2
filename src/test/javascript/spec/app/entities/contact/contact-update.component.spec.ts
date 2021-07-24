/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ContactUpdateComponent from '@/entities/contact/contact-update.vue';
import ContactClass from '@/entities/contact/contact-update.component';
import ContactService from '@/entities/contact/contact.service';

import EnvironmentService from '@/entities/environment/environment.service';

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
  describe('Contact Management Update Component', () => {
    let wrapper: Wrapper<ContactClass>;
    let comp: ContactClass;
    let contactServiceStub: SinonStubbedInstance<ContactService>;

    beforeEach(() => {
      contactServiceStub = sinon.createStubInstance<ContactService>(ContactService);

      wrapper = shallowMount<ContactClass>(ContactUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          contactService: () => contactServiceStub,

          environmentService: () => new EnvironmentService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.contact = entity;
        contactServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.contact = entity;
        contactServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContact = { id: 123 };
        contactServiceStub.find.resolves(foundContact);
        contactServiceStub.retrieve.resolves([foundContact]);

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
