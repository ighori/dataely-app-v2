import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import RelatedTableService from '@/entities/related-table/related-table.service';
import { IRelatedTable } from '@/shared/model/related-table.model';

import { IRelatedTableColumn, RelatedTableColumn } from '@/shared/model/related-table-column.model';
import RelatedTableColumnService from './related-table-column.service';

const validations: any = {
  relatedTableColumn: {
    columnName: {
      required,
    },
    columnType: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class RelatedTableColumnUpdate extends Vue {
  @Inject('relatedTableColumnService') private relatedTableColumnService: () => RelatedTableColumnService;
  public relatedTableColumn: IRelatedTableColumn = new RelatedTableColumn();

  @Inject('relatedTableService') private relatedTableService: () => RelatedTableService;

  public relatedTables: IRelatedTable[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.relatedTableColumnId) {
        vm.retrieveRelatedTableColumn(to.params.relatedTableColumnId);
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
    if (this.relatedTableColumn.id) {
      this.relatedTableColumnService()
        .update(this.relatedTableColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A RelatedTableColumn is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.relatedTableColumnService()
        .create(this.relatedTableColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A RelatedTableColumn is created with identifier ' + param.id;
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
      this.relatedTableColumn[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.relatedTableColumn[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.relatedTableColumn[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.relatedTableColumn[field] = null;
    }
  }

  public retrieveRelatedTableColumn(relatedTableColumnId): void {
    this.relatedTableColumnService()
      .find(relatedTableColumnId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.relatedTableColumn = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.relatedTableService()
      .retrieve()
      .then(res => {
        this.relatedTables = res.data;
      });
  }
}
