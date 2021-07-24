import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DsSchemaService from '@/entities/ds-schema/ds-schema.service';
import { IDsSchema } from '@/shared/model/ds-schema.model';

import { IDsSchemaRelationship, DsSchemaRelationship } from '@/shared/model/ds-schema-relationship.model';
import DsSchemaRelationshipService from './ds-schema-relationship.service';

const validations: any = {
  dsSchemaRelationship: {
    source: {},
    target: {},
    creationDate: {},
    lastUpdated: {},
  },
};

@Component({
  validations,
})
export default class DsSchemaRelationshipUpdate extends Vue {
  @Inject('dsSchemaRelationshipService') private dsSchemaRelationshipService: () => DsSchemaRelationshipService;
  public dsSchemaRelationship: IDsSchemaRelationship = new DsSchemaRelationship();

  @Inject('dsSchemaService') private dsSchemaService: () => DsSchemaService;

  public dsSchemas: IDsSchema[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dsSchemaRelationshipId) {
        vm.retrieveDsSchemaRelationship(to.params.dsSchemaRelationshipId);
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
    if (this.dsSchemaRelationship.id) {
      this.dsSchemaRelationshipService()
        .update(this.dsSchemaRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DsSchemaRelationship is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dsSchemaRelationshipService()
        .create(this.dsSchemaRelationship)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DsSchemaRelationship is created with identifier ' + param.id;
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
      this.dsSchemaRelationship[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dsSchemaRelationship[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.dsSchemaRelationship[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.dsSchemaRelationship[field] = null;
    }
  }

  public retrieveDsSchemaRelationship(dsSchemaRelationshipId): void {
    this.dsSchemaRelationshipService()
      .find(dsSchemaRelationshipId)
      .then(res => {
        res.creationDate = new Date(res.creationDate);
        res.lastUpdated = new Date(res.lastUpdated);
        this.dsSchemaRelationship = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.dsSchemaService()
      .retrieve()
      .then(res => {
        this.dsSchemas = res.data;
      });
  }
}
