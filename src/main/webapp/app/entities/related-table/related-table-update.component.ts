import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import { ITablesDefinition } from '@/shared/model/tables-definition.model';

import { IRelatedTable, RelatedTable } from '@/shared/model/related-table.model';
import RelatedTableService from './related-table.service';

const validations: any = {
  relatedTable: {
    tableName: {
      required,
    },
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class RelatedTableUpdate extends Vue {
  @Inject('relatedTableService') private relatedTableService: () => RelatedTableService;
  public relatedTable: IRelatedTable = new RelatedTable();

  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;

  public tablesDefinitions: ITablesDefinition[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.relatedTableId) {
        vm.retrieveRelatedTable(to.params.relatedTableId);
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
    if (this.relatedTable.id) {
      this.relatedTableService()
        .update(this.relatedTable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A RelatedTable is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.relatedTableService()
        .create(this.relatedTable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A RelatedTable is created with identifier ' + param.id;
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
      this.relatedTable[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.relatedTable[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.relatedTable[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.relatedTable[field] = null;
    }
  }

  public retrieveRelatedTable(relatedTableId): void {
    this.relatedTableService()
      .find(relatedTableId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.relatedTable = res;
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
