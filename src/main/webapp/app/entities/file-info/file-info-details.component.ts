import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFileInfo } from '@/shared/model/file-info.model';
import FileInfoService from './file-info.service';

@Component
export default class FileInfoDetails extends Vue {
  @Inject('fileInfoService') private fileInfoService: () => FileInfoService;
  public fileInfo: IFileInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileInfoId) {
        vm.retrieveFileInfo(to.params.fileInfoId);
      }
    });
  }

  public retrieveFileInfo(fileInfoId) {
    this.fileInfoService()
      .find(fileInfoId)
      .then(res => {
        this.fileInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
