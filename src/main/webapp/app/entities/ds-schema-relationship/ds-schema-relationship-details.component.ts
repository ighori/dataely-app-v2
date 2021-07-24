import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDsSchemaRelationship } from '@/shared/model/ds-schema-relationship.model';
import DsSchemaRelationshipService from './ds-schema-relationship.service';

@Component
export default class DsSchemaRelationshipDetails extends Vue {
  @Inject('dsSchemaRelationshipService') private dsSchemaRelationshipService: () => DsSchemaRelationshipService;
  public dsSchemaRelationship: IDsSchemaRelationship = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dsSchemaRelationshipId) {
        vm.retrieveDsSchemaRelationship(to.params.dsSchemaRelationshipId);
      }
    });
  }

  public retrieveDsSchemaRelationship(dsSchemaRelationshipId) {
    this.dsSchemaRelationshipService()
      .find(dsSchemaRelationshipId)
      .then(res => {
        this.dsSchemaRelationship = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
