import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EnvironmentService from '@/entities/environment/environment.service';
import { IEnvironment } from '@/shared/model/environment.model';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';
import { IDsSchema } from '@/shared/model/ds-schema.model';

import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';

import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';
import { IAnalyzerResult } from '@/shared/model/analyzer-result.model';

import { IDataSource, DataSource } from '@/shared/model/data-source.model';
import DataSourceService from './data-source.service';

const validations: any = {
  dataSource: {
    name: {
      required,
    },
    detail: {},
    databaseName: {
      required,
    },
    instanceName: {},
    schemaName: {},
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
    dbType: {
      required,
    },
    dbVersion: {},
    schemaCount: {},
    dsType: {
      required,
    },
    driverClassName: {},
    jdbcUrl: {},
    sid: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class DataSourceUpdate extends Vue {
  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;
  public dataSource: IDataSource = new DataSource();

  @Inject('environmentService') private environmentService: () => EnvironmentService;

  public environments: IEnvironment[] = [];

  @Inject('dsSchemaService') private dsSchemaService: () => DsSchemaService;

  public dsSchemas: IDsSchema[] = [];

  @Inject('analyzerJobService') private analyzerJobService: () => AnalyzerJobService;

  public analyzerJobs: IAnalyzerJob[] = [];

  @Inject('analyzerResultService') private analyzerResultService: () => AnalyzerResultService;

  public analyzerResults: IAnalyzerResult[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dataSourceId) {
        vm.retrieveDataSource(to.params.dataSourceId);
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
    if (this.dataSource.id) {
      this.dataSourceService()
        .update(this.dataSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DataSource is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dataSourceService()
        .create(this.dataSource)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DataSource is created with identifier ' + param.id;
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
      this.dataSource[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dataSource[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.dataSource[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dataSource[field] = null;
    }
  }

  public retrieveDataSource(dataSourceId): void {
    this.dataSourceService()
      .find(dataSourceId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.dataSource = res;
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
    this.dsSchemaService()
      .retrieve()
      .then(res => {
        this.dsSchemas = res.data;
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
