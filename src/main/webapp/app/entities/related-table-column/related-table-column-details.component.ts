import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRelatedTableColumn } from '@/shared/model/related-table-column.model';
import RelatedTableColumnService from './related-table-column.service';

@Component
export default class RelatedTableColumnDetails extends Vue {
  @Inject('relatedTableColumnService') private relatedTableColumnService: () => RelatedTableColumnService;
  public relatedTableColumn: IRelatedTableColumn = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.relatedTableColumnId) {
        vm.retrieveRelatedTableColumn(to.params.relatedTableColumnId);
      }
    });
  }

  public retrieveRelatedTableColumn(relatedTableColumnId) {
    this.relatedTableColumnService()
      .find(relatedTableColumnId)
      .then(res => {
        this.relatedTableColumn = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
