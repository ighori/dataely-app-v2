import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITableRelationship } from '@/shared/model/table-relationship.model';
import TableRelationshipService from './table-relationship.service';

@Component
export default class TableRelationshipDetails extends Vue {
  @Inject('tableRelationshipService') private tableRelationshipService: () => TableRelationshipService;
  public tableRelationship: ITableRelationship = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tableRelationshipId) {
        vm.retrieveTableRelationship(to.params.tableRelationshipId);
      }
    });
  }

  public retrieveTableRelationship(tableRelationshipId) {
    this.tableRelationshipService()
      .find(tableRelationshipId)
      .then(res => {
        this.tableRelationship = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
