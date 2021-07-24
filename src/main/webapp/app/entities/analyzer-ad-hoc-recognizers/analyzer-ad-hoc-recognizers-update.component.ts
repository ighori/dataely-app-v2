import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IAnalyzerAdHocRecognizers, AnalyzerAdHocRecognizers } from '@/shared/model/analyzer-ad-hoc-recognizers.model';
import AnalyzerAdHocRecognizersService from './analyzer-ad-hoc-recognizers.service';

const validations: any = {
  analyzerAdHocRecognizers: {
    name: {
      required,
    },
    detail: {},
    recognizerName: {},
    supportedLang: {},
    patternName: {},
    regex: {},
    score: {},
    context: {},
    denyList: {},
    supportedEntity: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class AnalyzerAdHocRecognizersUpdate extends Vue {
  @Inject('analyzerAdHocRecognizersService') private analyzerAdHocRecognizersService: () => AnalyzerAdHocRecognizersService;
  public analyzerAdHocRecognizers: IAnalyzerAdHocRecognizers = new AnalyzerAdHocRecognizers();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerAdHocRecognizersId) {
        vm.retrieveAnalyzerAdHocRecognizers(to.params.analyzerAdHocRecognizersId);
      }
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
    if (this.analyzerAdHocRecognizers.id) {
      this.analyzerAdHocRecognizersService()
        .update(this.analyzerAdHocRecognizers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerAdHocRecognizers is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.analyzerAdHocRecognizersService()
        .create(this.analyzerAdHocRecognizers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerAdHocRecognizers is created with identifier ' + param.id;
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
      this.analyzerAdHocRecognizers[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerAdHocRecognizers[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.analyzerAdHocRecognizers[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerAdHocRecognizers[field] = null;
    }
  }

  public retrieveAnalyzerAdHocRecognizers(analyzerAdHocRecognizersId): void {
    this.analyzerAdHocRecognizersService()
      .find(analyzerAdHocRecognizersId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.analyzerAdHocRecognizers = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
