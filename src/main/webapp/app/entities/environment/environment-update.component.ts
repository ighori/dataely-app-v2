import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import ContactService from '@/entities/contact/contact.service';
import { IContact } from '@/shared/model/contact.model';

import ApplicationService from '@/entities/application/application.service';
import { IApplication } from '@/shared/model/application.model';

import DataSourceService from '@/entities/data-source/data-source.service';
import { IDataSource } from '@/shared/model/data-source.model';

import FileSourceService from '@/entities/file-source/file-source.service';
import { IFileSource } from '@/shared/model/file-source.model';

import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';

import { IEnvironment, Environment } from '@/shared/model/environment.model';
import EnvironmentService from './environment.service';

const validations: any = {
  environment: {
    name: {
      required,
    },
    detail: {},
    type: {
      required,
    },
    purpose: {
      required,
    },
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class EnvironmentUpdate extends Vue {
  @Inject('environmentService') private environmentService: () => EnvironmentService;
  public environment: IEnvironment = new Environment();

  @Inject('contactService') private contactService: () => ContactService;

  public contacts: IContact[] = [];

  @Inject('applicationService') private applicationService: () => ApplicationService;

  public applications: IApplication[] = [];

  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;

  public dataSources: IDataSource[] = [];

  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;

  public fileSources: IFileSource[] = [];

  @Inject('analyzerJobService') private analyzerJobService: () => AnalyzerJobService;

  public analyzerJobs: IAnalyzerJob[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.environmentId) {
        vm.retrieveEnvironment(to.params.environmentId);
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
    if (this.environment.id) {
      this.environmentService()
        .update(this.environment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Environment is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.environmentService()
        .create(this.environment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Environment is created with identifier ' + param.id;
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
      this.environment[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.environment[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.environment[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.environment[field] = null;
    }
  }

  public retrieveEnvironment(environmentId): void {
    this.environmentService()
      .find(environmentId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.environment = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.contactService()
      .retrieve()
      .then(res => {
        this.contacts = res.data;
      });
    this.applicationService()
      .retrieve()
      .then(res => {
        this.applications = res.data;
      });
    this.dataSourceService()
      .retrieve()
      .then(res => {
        this.dataSources = res.data;
      });
    this.fileSourceService()
      .retrieve()
      .then(res => {
        this.fileSources = res.data;
      });
    this.analyzerJobService()
      .retrieve()
      .then(res => {
        this.analyzerJobs = res.data;
      });
  }
}
