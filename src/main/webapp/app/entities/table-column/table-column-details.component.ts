import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITableColumn } from '@/shared/model/table-column.model';
import TableColumnService from './table-column.service';

@Component
export default class TableColumnDetails extends Vue {
  @Inject('tableColumnService') private tableColumnService: () => TableColumnService;
  public tableColumn: ITableColumn = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tableColumnId) {
        vm.retrieveTableColumn(to.params.tableColumnId);
      }
    });
  }

  public retrieveTableColumn(tableColumnId) {
    this.tableColumnService()
      .find(tableColumnId)
      .then(res => {
        this.tableColumn = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
