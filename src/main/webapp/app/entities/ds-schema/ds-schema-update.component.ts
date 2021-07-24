import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DataSourceService from '@/entities/data-source/data-source.service';
import { IDataSource } from '@/shared/model/data-source.model';

import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';
import { IDsSchemaRelationship } from '@/shared/model/ds-schema-relationship.model';

import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import { ITablesDefinition } from '@/shared/model/tables-definition.model';

import { IDsSchema, DsSchema } from '@/shared/model/ds-schema.model';
import DsSchemaService from './ds-schema.service';

const validations: any = {
  dsSchema: {
    name: {
      required,
    },
    detail: {},
    tableCount: {},
    tableRelCount: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class DsSchemaUpdate extends Vue {
  @Inject('dsSchemaService') private dsSchemaService: () => DsSchemaService;
  public dsSchema: IDsSchema = new DsSchema();

  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;

  public dataSources: IDataSource[] = [];

  @Inject('dsSchemaRelationshipService') private dsSchemaRelationshipService: () => DsSchemaRelationshipService;

  public dsSchemaRelationships: IDsSchemaRelationship[] = [];

  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;

  public tablesDefinitions: ITablesDefinition[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dsSchemaId) {
        vm.retrieveDsSchema(to.params.dsSchemaId);
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
    if (this.dsSchema.id) {
      this.dsSchemaService()
        .update(this.dsSchema)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DsSchema is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dsSchemaService()
        .create(this.dsSchema)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DsSchema is created with identifier ' + param.id;
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
      this.dsSchema[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dsSchema[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.dsSchema[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dsSchema[field] = null;
    }
  }

  public retrieveDsSchema(dsSchemaId): void {
    this.dsSchemaService()
      .find(dsSchemaId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.dsSchema = res;
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
    this.dsSchemaRelationshipService()
      .retrieve()
      .then(res => {
        this.dsSchemaRelationships = res.data;
      });
    this.tablesDefinitionService()
      .retrieve()
      .then(res => {
        this.tablesDefinitions = res.data;
      });
  }
}
