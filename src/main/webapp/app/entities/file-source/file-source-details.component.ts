import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFileSource } from '@/shared/model/file-source.model';
import FileSourceService from './file-source.service';

@Component
export default class FileSourceDetails extends Vue {
  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;
  public fileSource: IFileSource = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileSourceId) {
        vm.retrieveFileSource(to.params.fileSourceId);
      }
    });
  }

  public retrieveFileSource(fileSourceId) {
    this.fileSourceService()
      .find(fileSourceId)
      .then(res => {
        this.fileSource = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
