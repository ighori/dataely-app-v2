import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import { ITablesDefinition } from '@/shared/model/tables-definition.model';

import { ITableColumn, TableColumn } from '@/shared/model/table-column.model';
import TableColumnService from './table-column.service';

const validations: any = {
  tableColumn: {
    columnName: {
      required,
    },
    columnType: {},
    columnSize: {},
    isNullable: {},
    isPrimaryKey: {},
    isForeignKey: {},
    isIndexed: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class TableColumnUpdate extends Vue {
  @Inject('tableColumnService') private tableColumnService: () => TableColumnService;
  public tableColumn: ITableColumn = new TableColumn();

  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;

  public tablesDefinitions: ITablesDefinition[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tableColumnId) {
        vm.retrieveTableColumn(to.params.tableColumnId);
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
    if (this.tableColumn.id) {
      this.tableColumnService()
        .update(this.tableColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TableColumn is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tableColumnService()
        .create(this.tableColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TableColumn is created with identifier ' + param.id;
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
      this.tableColumn[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tableColumn[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.tableColumn[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tableColumn[field] = null;
    }
  }

  public retrieveTableColumn(tableColumnId): void {
    this.tableColumnService()
      .find(tableColumnId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.tableColumn = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tablesDefinitionService()
      .retrieve()
      .then(res => {
        this.tablesDefinitions = res.data;
      });
  }
}
