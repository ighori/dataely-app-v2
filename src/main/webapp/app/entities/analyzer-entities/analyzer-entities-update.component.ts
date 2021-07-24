import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IAnalyzerEntities, AnalyzerEntities } from '@/shared/model/analyzer-entities.model';
import AnalyzerEntitiesService from './analyzer-entities.service';

const validations: any = {
  analyzerEntities: {
    name: {
      required,
    },
    detail: {},
    entityName: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class AnalyzerEntitiesUpdate extends Vue {
  @Inject('analyzerEntitiesService') private analyzerEntitiesService: () => AnalyzerEntitiesService;
  public analyzerEntities: IAnalyzerEntities = new AnalyzerEntities();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.analyzerEntitiesId) {
        vm.retrieveAnalyzerEntities(to.params.analyzerEntitiesId);
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
    if (this.analyzerEntities.id) {
      this.analyzerEntitiesService()
        .update(this.analyzerEntities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerEntities is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.analyzerEntitiesService()
        .create(this.analyzerEntities)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A AnalyzerEntities is created with identifier ' + param.id;
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
      this.analyzerEntities[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerEntities[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.analyzerEntities[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.analyzerEntities[field] = null;
    }
  }

  public retrieveAnalyzerEntities(analyzerEntitiesId): void {
    this.analyzerEntitiesService()
      .find(analyzerEntitiesId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.analyzerEntities = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
