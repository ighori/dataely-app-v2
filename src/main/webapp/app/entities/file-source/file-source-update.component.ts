import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EnvironmentService from '@/entities/environment/environment.service';
import { IEnvironment } from '@/shared/model/environment.model';

import FileInfoService from '@/entities/file-info/file-info.service';
import { IFileInfo } from '@/shared/model/file-info.model';

import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';

import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';
import { IAnalyzerResult } from '@/shared/model/analyzer-result.model';

import { IFileSource, FileSource } from '@/shared/model/file-source.model';
import FileSourceService from './file-source.service';

const validations: any = {
  fileSource: {
    name: {
      required,
    },
    detail: {},
    hostname: {
      required,
    },
    port: {
      required,
      numeric,
    },
    username: {
      required,
    },
    password: {
      required,
    },
    icon: {},
    connectionType: {},
    type: {},
    region: {},
    ignoreDottedFiles: {},
    recurse: {},
    pathFilterRegex: {},
    remotePath: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class FileSourceUpdate extends Vue {
  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;
  public fileSource: IFileSource = new FileSource();

  @Inject('environmentService') private environmentService: () => EnvironmentService;

  public environments: IEnvironment[] = [];

  @Inject('fileInfoService') private fileInfoService: () => FileInfoService;

  public fileInfos: IFileInfo[] = [];

  @Inject('analyzerJobService') private analyzerJobService: () => AnalyzerJobService;

  public analyzerJobs: IAnalyzerJob[] = [];

  @Inject('analyzerResultService') private analyzerResultService: () => AnalyzerResultService;

  public analyzerResults: IAnalyzerResult[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileSourceId) {
        vm.retrieveFileSource(to.params.fileSourceId);
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
    if (this.fileSource.id) {
      this.fileSourceService()
        .update(this.fileSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileSource is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.fileSourceService()
        .create(this.fileSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileSource is created with identifier ' + param.id;
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
      this.fileSource[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileSource[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.fileSource[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileSource[field] = null;
    }
  }

  public retrieveFileSource(fileSourceId): void {
    this.fileSourceService()
      .find(fileSourceId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.fileSource = res;
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
    this.fileInfoService()
      .retrieve()
      .then(res => {
        this.fileInfos = res.data;
      });
    this.analyzerJobService()
      .retrieve()
      .then(res => {
        this.analyzerJobs = res.data;
      });
    this.analyzerResultService()
      .retrieve()
      .then(res => {
        this.analyzerResults = res.data;
      });
  }
}
