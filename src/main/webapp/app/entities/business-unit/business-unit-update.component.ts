import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ApplicationService from '@/entities/application/application.service';
import { IApplication } from '@/shared/model/application.model';

import OrganizationService from '@/entities/organization/organization.service';
import { IOrganization } from '@/shared/model/organization.model';

import { IBusinessUnit, BusinessUnit } from '@/shared/model/business-unit.model';
import BusinessUnitService from './business-unit.service';

const validations: any = {
  businessUnit: {
    name: {
      required,
    },
    detail: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class BusinessUnitUpdate extends Vue {
  @Inject('businessUnitService') private businessUnitService: () => BusinessUnitService;
  public businessUnit: IBusinessUnit = new BusinessUnit();

  @Inject('applicationService') private applicationService: () => ApplicationService;

  public applications: IApplication[] = [];

  @Inject('organizationService') private organizationService: () => OrganizationService;

  public organizations: IOrganization[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.businessUnitId) {
        vm.retrieveBusinessUnit(to.params.businessUnitId);
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
    if (this.businessUnit.id) {
      this.businessUnitService()
        .update(this.businessUnit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A BusinessUnit is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.businessUnitService()
        .create(this.businessUnit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A BusinessUnit is created with identifier ' + param.id;
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
      this.businessUnit[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.businessUnit[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.businessUnit[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.businessUnit[field] = null;
    }
  }

  public retrieveBusinessUnit(businessUnitId): void {
    this.businessUnitService()
      .find(businessUnitId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.businessUnit = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.applicationService()
      .retrieve()
      .then(res => {
        this.applications = res.data;
      });
    this.organizationService()
      .retrieve()
      .then(res => {
        this.organizations = res.data;
      });
  }
}
