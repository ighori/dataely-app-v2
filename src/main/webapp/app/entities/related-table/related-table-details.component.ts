import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRelatedTable } from '@/shared/model/related-table.model';
import RelatedTableService from './related-table.service';

@Component
export default class RelatedTableDetails extends Vue {
  @Inject('relatedTableService') private relatedTableService: () => RelatedTableService;
  public relatedTable: IRelatedTable = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.relatedTableId) {
        vm.retrieveRelatedTable(to.params.relatedTableId);
      }
    });
  }

  public retrieveRelatedTable(relatedTableId) {
    this.relatedTableService()
      .find(relatedTableId)
      .then(res => {
        this.relatedTable = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
