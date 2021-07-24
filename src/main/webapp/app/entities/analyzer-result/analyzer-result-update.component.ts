import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DataSourceService from '@/entities/data-source/data-source.service';
import { IDataSource } from '@/shared/model/data-source.model';

import FileSourceService from '@/entities/file-source/file-source.service';
import { IFileSource } from '@/shared/model/file-source.model';

import { IAnalyzerResult, AnalyzerResult } from '@/shared/model/analyzer-result.model';
import AnalyzerResultService from './analyzer-result.service';

const validations: any = {
  analyzerResult: {
    name: {
      required,
    },
    detail: {},
    objectId: {},
    objectType: {},
    fieldId: {},
    fieldType: {},
    entityType: {},
    start: {},
    end: {},
    score: {},
    recognizer: {},
    patternName: {},
    patternExpr: {},
    originalScore: {},
    textualExplanation: {},
    supportiveContextWord: {},
    validationResult: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class AnalyzerResultUpdate extends Vue {
  @Inject('analyzerResultService') private analyzerResultService: () => AnalyzerResultService;
  public analyzerResult: IAnalyzerResult = new AnalyzerResult();

  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;

  public dataSources: IDataSource[] = [];

  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;

  public fileSources: IFileSource[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerResultId) {
        vm.retrieveAnalyzerResult(to.params.analyzerResultId);
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
    if (this.analyzerResult.id) {
      this.analyzerResultService()
        .update(this.analyzerResult)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerResult is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.analyzerResultService()
        .create(this.analyzerResult)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerResult is created with identifier ' + param.id;
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
      this.analyzerResult[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerResult[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.analyzerResult[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerResult[field] = null;
    }
  }

  public retrieveAnalyzerResult(analyzerResultId): void {
    this.analyzerResultService()
      .find(analyzerResultId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.analyzerResult = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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
  }
}
