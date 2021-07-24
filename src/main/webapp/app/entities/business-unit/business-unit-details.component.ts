import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBusinessUnit } from '@/shared/model/business-unit.model';
import BusinessUnitService from './business-unit.service';

@Component
export default class BusinessUnitDetails extends Vue {
  @Inject('businessUnitService') private businessUnitService: () => BusinessUnitService;
  public businessUnit: IBusinessUnit = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.businessUnitId) {
        vm.retrieveBusinessUnit(to.params.businessUnitId);
      }
    });
  }

  public retrieveBusinessUnit(businessUnitId) {
    this.businessUnitService()
      .find(businessUnitId)
      .then(res => {
        this.businessUnit = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
