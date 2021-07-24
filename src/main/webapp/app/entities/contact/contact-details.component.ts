import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IContact } from '@/shared/model/contact.model';
import ContactService from './contact.service';

@Component
export default class ContactDetails extends mixins(JhiDataUtils) {
  @Inject('contactService') private contactService: () => ContactService;
  public contact: IContact = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactId) {
        vm.retrieveContact(to.params.contactId);
      }
    });
  }

  public retrieveContact(contactId) {
    this.contactService()
      .find(contactId)
      .then(res => {
        this.contact = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
