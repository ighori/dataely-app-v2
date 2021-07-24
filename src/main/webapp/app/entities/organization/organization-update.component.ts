import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import UserService from '@/admin/user-management/user-management.service';

import BusinessUnitService from '@/entities/business-unit/business-unit.service';
import { IBusinessUnit } from '@/shared/model/business-unit.model';

import { IOrganization, Organization } from '@/shared/model/organization.model';
import OrganizationService from './organization.service';

const validations: any = {
  organization: {
    name: {
      required,
    },
    detail: {},
    creationDate: {},
    lastUpdated: {},
    user: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OrganizationUpdate extends Vue {
  @Inject('organizationService') private organizationService: () => OrganizationService;
  public organization: IOrganization = new Organization();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('businessUnitService') private businessUnitService: () => BusinessUnitService;

  public businessUnits: IBusinessUnit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.organizationId) {
        vm.retrieveOrganization(to.params.organizationId);
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
    if (this.organization.id) {
      this.organizationService()
        .update(this.organization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Organization is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.organizationService()
        .create(this.organization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Organization is created with identifier ' + param.id;
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.organization[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.organization[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.organization[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.organization[field] = null;
    }
  }

  public retrieveOrganization(organizationId): void {
    this.organizationService()
      .find(organizationId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.organization = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.businessUnitService()
      .retrieve()
      .then(res => {
        this.businessUnits = res.data;
      });
  }
}
