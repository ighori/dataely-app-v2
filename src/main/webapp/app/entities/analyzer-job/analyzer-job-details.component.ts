import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';
import AnalyzerJobService from './analyzer-job.service';

@Component
export default class AnalyzerJobDetails extends Vue {
  @Inject('analyzerJobService') private analyzerJobService: () => AnalyzerJobService;
  public analyzerJob: IAnalyzerJob = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerJobId) {
        vm.retrieveAnalyzerJob(to.params.analyzerJobId);
      }
    });
  }

  public retrieveAnalyzerJob(analyzerJobId) {
    this.analyzerJobService()
      .find(analyzerJobId)
      .then(res => {
        this.analyzerJob = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
