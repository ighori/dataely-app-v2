import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDsSchema } from '@/shared/model/ds-schema.model';
import DsSchemaService from './ds-schema.service';

@Component
export default class DsSchemaDetails extends Vue {
  @Inject('dsSchemaService') private dsSchemaService: () => DsSchemaService;
  public dsSchema: IDsSchema = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dsSchemaId) {
        vm.retrieveDsSchema(to.params.dsSchemaId);
      }
    });
  }

  public retrieveDsSchema(dsSchemaId) {
    this.dsSchemaService()
      .find(dsSchemaId)
      .then(res => {
        this.dsSchema = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
