import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';
import { IDsSchema } from '@/shared/model/ds-schema.model';

import TableColumnService from '@/entities/table-column/table-column.service';
import { ITableColumn } from '@/shared/model/table-column.model';

import { ITablesDefinition, TablesDefinition } from '@/shared/model/tables-definition.model';
import TablesDefinitionService from './tables-definition.service';

const validations: any = {
  tablesDefinition: {
    tableName: {
      required,
    },
    value: {},
    symbolSize: {},
    category: {},
    colCnt: {},
    colCntNbr: {},
    colCntTB: {},
    colCntSTR: {},
    colCntBL: {},
    colCntPK: {},
    colCntFK: {},
    colCntIX: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class TablesDefinitionUpdate extends Vue {
  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;
  public tablesDefinition: ITablesDefinition = new TablesDefinition();

  @Inject('dsSchemaService') private dsSchemaService: () => DsSchemaService;

  public dsSchemas: IDsSchema[] = [];

  @Inject('tableColumnService') private tableColumnService: () => TableColumnService;

  public tableColumns: ITableColumn[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tablesDefinitionId) {
        vm.retrieveTablesDefinition(to.params.tablesDefinitionId);
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
    if (this.tablesDefinition.id) {
      this.tablesDefinitionService()
        .update(this.tablesDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TablesDefinition is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tablesDefinitionService()
        .create(this.tablesDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TablesDefinition is created with identifier ' + param.id;
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
      this.tablesDefinition[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tablesDefinition[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.tablesDefinition[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tablesDefinition[field] = null;
    }
  }

  public retrieveTablesDefinition(tablesDefinitionId): void {
    this.tablesDefinitionService()
      .find(tablesDefinitionId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.tablesDefinition = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.dsSchemaService()
      .retrieve()
      .then(res => {
        this.dsSchemas = res.data;
      });
    this.tableColumnService()
      .retrieve()
      .then(res => {
        this.tableColumns = res.data;
      });
  }
}
