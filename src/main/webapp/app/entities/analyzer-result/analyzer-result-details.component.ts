import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalyzerResult } from '@/shared/model/analyzer-result.model';
import AnalyzerResultService from './analyzer-result.service';

@Component
export default class AnalyzerResultDetails extends Vue {
  @Inject('analyzerResultService') private analyzerResultService: () => AnalyzerResultService;
  public analyzerResult: IAnalyzerResult = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerResultId) {
        vm.retrieveAnalyzerResult(to.params.analyzerResultId);
      }
    });
  }

  public retrieveAnalyzerResult(analyzerResultId) {
    this.analyzerResultService()
      .find(analyzerResultId)
      .then(res => {
        this.analyzerResult = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
