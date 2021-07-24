import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EnvironmentService from '@/entities/environment/environment.service';
import { IEnvironment } from '@/shared/model/environment.model';

import DataSourceService from '@/entities/data-source/data-source.service';
import { IDataSource } from '@/shared/model/data-source.model';

import FileSourceService from '@/entities/file-source/file-source.service';
import { IFileSource } from '@/shared/model/file-source.model';

import UserService from '@/admin/user-management/user-management.service';

import { IAnalyzerJob, AnalyzerJob } from '@/shared/model/analyzer-job.model';
import AnalyzerJobService from './analyzer-job.service';

const validations: any = {
  analyzerJob: {
    name: {
      required,
    },
    detail: {},
    endTime: {},
    startTime: {},
    status: {},
    previousRunTime: {},
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
export default class AnalyzerJobUpdate extends Vue {
  @Inject('analyzerJobService') private analyzerJobService: () => AnalyzerJobService;
  public analyzerJob: IAnalyzerJob = new AnalyzerJob();

  @Inject('environmentService') private environmentService: () => EnvironmentService;

  public environments: IEnvironment[] = [];

  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;

  public dataSources: IDataSource[] = [];

  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;

  public fileSources: IFileSource[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerJobId) {
        vm.retrieveAnalyzerJob(to.params.analyzerJobId);
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
    if (this.analyzerJob.id) {
      this.analyzerJobService()
        .update(this.analyzerJob)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerJob is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.analyzerJobService()
        .create(this.analyzerJob)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerJob is created with identifier ' + param.id;
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
      this.analyzerJob[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerJob[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.analyzerJob[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerJob[field] = null;
    }
  }

  public retrieveAnalyzerJob(analyzerJobId): void {
    this.analyzerJobService()
      .find(analyzerJobId)
      .then(res => {
        res.endTime = new Date(res.endTime);
        res.startTime = new Date(res.startTime);
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.analyzerJob = res;
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
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
