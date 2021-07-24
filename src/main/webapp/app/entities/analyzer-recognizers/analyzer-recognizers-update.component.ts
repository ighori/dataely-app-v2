import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IAnalyzerRecognizers, AnalyzerRecognizers } from '@/shared/model/analyzer-recognizers.model';
import AnalyzerRecognizersService from './analyzer-recognizers.service';

const validations: any = {
  analyzerRecognizers: {
    name: {
      required,
    },
    detail: {},
    recognizerName: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class AnalyzerRecognizersUpdate extends Vue {
  @Inject('analyzerRecognizersService') private analyzerRecognizersService: () => AnalyzerRecognizersService;
  public analyzerRecognizers: IAnalyzerRecognizers = new AnalyzerRecognizers();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerRecognizersId) {
        vm.retrieveAnalyzerRecognizers(to.params.analyzerRecognizersId);
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
    if (this.analyzerRecognizers.id) {
      this.analyzerRecognizersService()
        .update(this.analyzerRecognizers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerRecognizers is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.analyzerRecognizersService()
        .create(this.analyzerRecognizers)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerRecognizers is created with identifier ' + param.id;
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
      this.analyzerRecognizers[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerRecognizers[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.analyzerRecognizers[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerRecognizers[field] = null;
    }
  }

  public retrieveAnalyzerRecognizers(analyzerRecognizersId): void {
    this.analyzerRecognizersService()
      .find(analyzerRecognizersId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.analyzerRecognizers = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
