import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import FileSourceService from '@/entities/file-source/file-source.service';
import { IFileSource } from '@/shared/model/file-source.model';

import FileConfigService from '@/entities/file-config/file-config.service';
import { IFileConfig } from '@/shared/model/file-config.model';

import { IFileInfo, FileInfo } from '@/shared/model/file-info.model';
import FileInfoService from './file-info.service';

const validations: any = {
  fileInfo: {
    name: {
      required,
    },
    detail: {},
    fileType: {
      required,
    },
    filePath: {
      required,
    },
    columnNameLineNumber: {},
    encoding: {},
    separatorChar: {},
    quoteChar: {},
    escapeChar: {},
    fixedValueWidth: {},
    skipEmptyLines: {},
    skipEmptyColumns: {},
    failOnInconsistentLineWidth: {},
    skipEbcdicHeader: {},
    eolPresent: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class FileInfoUpdate extends Vue {
  @Inject('fileInfoService') private fileInfoService: () => FileInfoService;
  public fileInfo: IFileInfo = new FileInfo();

  @Inject('fileSourceService') private fileSourceService: () => FileSourceService;

  public fileSources: IFileSource[] = [];

  @Inject('fileConfigService') private fileConfigService: () => FileConfigService;

  public fileConfigs: IFileConfig[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileInfoId) {
        vm.retrieveFileInfo(to.params.fileInfoId);
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
    if (this.fileInfo.id) {
      this.fileInfoService()
        .update(this.fileInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileInfo is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.fileInfoService()
        .create(this.fileInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileInfo is created with identifier ' + param.id;
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
      this.fileInfo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileInfo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.fileInfo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileInfo[field] = null;
    }
  }

  public retrieveFileInfo(fileInfoId): void {
    this.fileInfoService()
      .find(fileInfoId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.fileInfo = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.fileSourceService()
      .retrieve()
      .then(res => {
        this.fileSources = res.data;
      });
    this.fileConfigService()
      .retrieve()
      .then(res => {
        this.fileConfigs = res.data;
      });
  }
}
