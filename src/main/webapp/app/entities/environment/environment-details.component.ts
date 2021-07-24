import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEnvironment } from '@/shared/model/environment.model';
import EnvironmentService from './environment.service';

@Component
export default class EnvironmentDetails extends Vue {
  @Inject('environmentService') private environmentService: () => EnvironmentService;
  public environment: IEnvironment = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.environmentId) {
        vm.retrieveEnvironment(to.params.environmentId);
      }
    });
  }

  public retrieveEnvironment(environmentId) {
    this.environmentService()
      .find(environmentId)
      .then(res => {
        this.environment = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
