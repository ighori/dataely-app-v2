import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EnvironmentService from '@/entities/environment/environment.service';
import { IEnvironment } from '@/shared/model/environment.model';

import BusinessUnitService from '@/entities/business-unit/business-unit.service';
import { IBusinessUnit } from '@/shared/model/business-unit.model';

import { IApplication, Application } from '@/shared/model/application.model';
import ApplicationService from './application.service';

const validations: any = {
  application: {
    name: {
      required,
    },
    detail: {},
    version: {},
    appType: {
      required,
    },
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class ApplicationUpdate extends Vue {
  @Inject('applicationService') private applicationService: () => ApplicationService;
  public application: IApplication = new Application();

  @Inject('environmentService') private environmentService: () => EnvironmentService;

  public environments: IEnvironment[] = [];

  @Inject('businessUnitService') private businessUnitService: () => BusinessUnitService;

  public businessUnits: IBusinessUnit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.applicationId) {
        vm.retrieveApplication(to.params.applicationId);
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
    if (this.application.id) {
      this.applicationService()
        .update(this.application)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Application is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.applicationService()
        .create(this.application)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Application is created with identifier ' + param.id;
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
      this.application[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.application[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.application[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.application[field] = null;
    }
  }

  public retrieveApplication(applicationId): void {
    this.applicationService()
      .find(applicationId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.application = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.environmentService()
      .retrieve()
      .then(res => {
        this.environments = res.data;
      });
    this.businessUnitService()
      .retrieve()
      .then(res => {
        this.businessUnits = res.data;
      });
  }
}
