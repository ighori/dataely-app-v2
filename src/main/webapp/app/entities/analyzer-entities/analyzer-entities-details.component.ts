import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalyzerEntities } from '@/shared/model/analyzer-entities.model';
import AnalyzerEntitiesService from './analyzer-entities.service';

@Component
export default class AnalyzerEntitiesDetails extends Vue {
  @Inject('analyzerEntitiesService') private analyzerEntitiesService: () => AnalyzerEntitiesService;
  public analyzerEntities: IAnalyzerEntities = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerEntitiesId) {
        vm.retrieveAnalyzerEntities(to.params.analyzerEntitiesId);
      }
    });
  }

  public retrieveAnalyzerEntities(analyzerEntitiesId) {
    this.analyzerEntitiesService()
      .find(analyzerEntitiesId)
      .then(res => {
        this.analyzerEntities = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
