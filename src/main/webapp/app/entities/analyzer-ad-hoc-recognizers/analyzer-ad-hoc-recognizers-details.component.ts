import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalyzerAdHocRecognizers } from '@/shared/model/analyzer-ad-hoc-recognizers.model';
import AnalyzerAdHocRecognizersService from './analyzer-ad-hoc-recognizers.service';

@Component
export default class AnalyzerAdHocRecognizersDetails extends Vue {
  @Inject('analyzerAdHocRecognizersService') private analyzerAdHocRecognizersService: () => AnalyzerAdHocRecognizersService;
  public analyzerAdHocRecognizers: IAnalyzerAdHocRecognizers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerAdHocRecognizersId) {
        vm.retrieveAnalyzerAdHocRecognizers(to.params.analyzerAdHocRecognizersId);
      }
    });
  }

  public retrieveAnalyzerAdHocRecognizers(analyzerAdHocRecognizersId) {
    this.analyzerAdHocRecognizersService()
      .find(analyzerAdHocRecognizersId)
      .then(res => {
        this.analyzerAdHocRecognizers = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
