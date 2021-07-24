import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDataSource } from '@/shared/model/data-source.model';
import DataSourceService from './data-source.service';

@Component
export default class DataSourceDetails extends Vue {
  @Inject('dataSourceService') private dataSourceService: () => DataSourceService;
  public dataSource: IDataSource = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dataSourceId) {
        vm.retrieveDataSource(to.params.dataSourceId);
      }
    });
  }

  public retrieveDataSource(dataSourceId) {
    this.dataSourceService()
      .find(dataSourceId)
      .then(res => {
        this.dataSource = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
