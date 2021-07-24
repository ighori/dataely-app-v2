import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import FileInfoService from '@/entities/file-info/file-info.service';
import { IFileInfo } from '@/shared/model/file-info.model';

import { IFileConfig, FileConfig } from '@/shared/model/file-config.model';
import FileConfigService from './file-config.service';

const validations: any = {
  fileConfig: {
    name: {
      required,
    },
    detail: {},
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
    fileType: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class FileConfigUpdate extends Vue {
  @Inject('fileConfigService') private fileConfigService: () => FileConfigService;
  public fileConfig: IFileConfig = new FileConfig();

  @Inject('fileInfoService') private fileInfoService: () => FileInfoService;

  public fileInfos: IFileInfo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fileConfigId) {
        vm.retrieveFileConfig(to.params.fileConfigId);
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
    if (this.fileConfig.id) {
      this.fileConfigService()
        .update(this.fileConfig)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileConfig is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.fileConfigService()
        .create(this.fileConfig)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A FileConfig is created with identifier ' + param.id;
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
      this.fileConfig[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileConfig[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.fileConfig[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.fileConfig[field] = null;
    }
  }

  public retrieveFileConfig(fileConfigId): void {
    this.fileConfigService()
      .find(fileConfigId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.fileConfig = res;
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
