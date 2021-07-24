import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import FileInfoService from '@/entities/file-info/file-info.service';
import { IFileInfo } from '@/shared/model/file-info.model';

import { IFileField, FileField } from '@/shared/model/file-field.model';
import FileFieldService from './file-field.service';

const validations: any = {
  fileField: {
    fieldName: {
      required,
    },
    fieldType: {},
    fieldSize: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class FileFieldUpdate extends Vue {
  @Inject('fileFieldService') private fileFieldService: () => FileFieldService;
  public fileField: IFileField = new FileField();

  @Inject('fileInfoService') private fileInfoService: () => FileInfoService;

  public fileInfos: IFileInfo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileFieldId) {
        vm.retrieveFileField(to.params.fileFieldId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.fileField.id) {
      this.fileFieldService()
        .update(this.fileField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileField is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.fileFieldService()
        .create(this.fileField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileField is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.fileField[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileField[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.fileField[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileField[field] = null;
    }
  }

  public retrieveFileField(fileFieldId): void {
    this.fileFieldService()
      .find(fileFieldId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.fileField = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.fileInfoService()
      .retrieve()
      .then(res => {
        this.fileInfos = res.data;
      });
  }
}
