import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITablesDefinition } from '@/shared/model/tables-definition.model';
import TablesDefinitionService from './tables-definition.service';

@Component
export default class TablesDefinitionDetails extends Vue {
  @Inject('tablesDefinitionService') private tablesDefinitionService: () => TablesDefinitionService;
  public tablesDefinition: ITablesDefinition = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tablesDefinitionId) {
        vm.retrieveTablesDefinition(to.params.tablesDefinitionId);
      }
    });
  }

  public retrieveTablesDefinition(tablesDefinitionId) {
    this.tablesDefinitionService()
      .find(tablesDefinitionId)
      .then(res => {
        this.tablesDefinition = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
