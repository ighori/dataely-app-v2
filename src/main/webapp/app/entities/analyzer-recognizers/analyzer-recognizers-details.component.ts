import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalyzerRecognizers } from '@/shared/model/analyzer-recognizers.model';
import AnalyzerRecognizersService from './analyzer-recognizers.service';

@Component
export default class AnalyzerRecognizersDetails extends Vue {
  @Inject('analyzerRecognizersService') private analyzerRecognizersService: () => AnalyzerRecognizersService;
  public analyzerRecognizers: IAnalyzerRecognizers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerRecognizersId) {
        vm.retrieveAnalyzerRecognizers(to.params.analyzerRecognizersId);
      }
    });
  }

  public retrieveAnalyzerRecognizers(analyzerRecognizersId) {
    this.analyzerRecognizersService()
      .find(analyzerRecognizersId)
      .then(res => {
        this.analyzerRecognizers = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
