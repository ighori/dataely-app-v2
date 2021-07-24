import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import { ITablesDefinition } from '@/shared/model/tables-definition.model';

import { ITableRelationship, TableRelationship } from '@/shared/model/table-relationship.model';
import TableRelationshipService from './table-relationship.service';

const validations: any = {
  tableRelationship: {
    source: {},
    target: {},
    sourceKey: {},
    targetKey: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class TableRelationshipUpdate extends Vue {
  @Inject('tableRelationshipService') private tableRelationshipService: () => TableRelationshipService;
  public tableRelationship: ITableRelationship = new TableRelationship();

  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;

  public tablesDefinitions: ITablesDefinition[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tableRelationshipId) {
        vm.retrieveTableRelationship(to.params.tableRelationshipId);
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
    if (this.tableRelationship.id) {
      this.tableRelationshipService()
        .update(this.tableRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TableRelationship is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tableRelationshipService()
        .create(this.tableRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A TableRelationship is created with identifier ' + param.id;
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
      this.tableRelationship[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tableRelationship[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.tableRelationship[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tableRelationship[field] = null;
    }
  }

  public retrieveTableRelationship(tableRelationshipId): void {
    this.tableRelationshipService()
      .find(tableRelationshipId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.tableRelationship = res;
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
