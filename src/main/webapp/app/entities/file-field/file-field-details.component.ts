import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFileField } from '@/shared/model/file-field.model';
import FileFieldService from './file-field.service';

@Component
export default class FileFieldDetails extends Vue {
  @Inject('fileFieldService') private fileFieldService: () => FileFieldService;
  public fileField: IFileField = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileFieldId) {
        vm.retrieveFileField(to.params.fileFieldId);
      }
    });
  }

  public retrieveFileField(fileFieldId) {
    this.fileFieldService()
      .find(fileFieldId)
      .then(res => {
        this.fileField = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
