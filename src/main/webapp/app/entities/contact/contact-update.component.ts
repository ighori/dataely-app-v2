import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';

import EnvironmentService from '@/entities/environment/environment.service';
import { IEnvironment } from '@/shared/model/environment.model';

import { IContact, Contact } from '@/shared/model/contact.model';
import ContactService from './contact.service';

const validations: any = {
  contact: {
    firstName: {
      required,
    },
    lastName: {
      required,
    },
    name: {
      required,
    },
    role: {
      required,
    },
    email: {
      required,
    },
    phone: {},
    extension: {},
    mobile: {},
    location: {},
    addressLine1: {},
    addressLine2: {},
    city: {},
    country: {},
    image: {},
  },
};

@Component({
  validations,
})
export default class ContactUpdate extends mixins(JhiDataUtils) {
  @Inject('contactService') private contactService: () => ContactService;
  public contact: IContact = new Contact();

  @Inject('environmentService') private environmentService: () => EnvironmentService;

  public environments: IEnvironment[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactId) {
        vm.retrieveContact(to.params.contactId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.contact.id) {
      this.contactService()
        .update(this.contact)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Contact is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.contactService()
        .create(this.contact)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Contact is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveContact(contactId): void {
    this.contactService()
      .find(contactId)
      .then(res => {
        this.contact = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.contact && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.contact, field)) {
        this.contact[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.contact, fieldContentType)) {
        this.contact[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.environmentService()
      .retrieve()
      .then(res => {
        this.environments = res.data;
      });
  }
}
