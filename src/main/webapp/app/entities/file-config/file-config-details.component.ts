import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFileConfig } from '@/shared/model/file-config.model';
import FileConfigService from './file-config.service';

@Component
export default class FileConfigDetails extends Vue {
  @Inject('fileConfigService') private fileConfigService: () => FileConfigService;
  public fileConfig: IFileConfig = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileConfigId) {
        vm.retrieveFileConfig(to.params.fileConfigId);
      }
    });
  }

  public retrieveFileConfig(fileConfigId) {
    this.fileConfigService()
      .find(fileConfigId)
      .then(res => {
        this.fileConfig = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
