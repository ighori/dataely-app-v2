// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import { ToastPlugin } from 'bootstrap-vue';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import InfiniteLoading from 'vue-infinite-loading';
import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ConfigurationService from '@/admin/configuration/configuration.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';
import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';

import UserOAuth2Service from '@/entities/user/user.oauth2.service';
/* tslint:disable */

import OrganizationService from '@/entities/organization/organization.service';
import BusinessUnitService from '@/entities/business-unit/business-unit.service';
import ApplicationService from '@/entities/application/application.service';
import ContactService from '@/entities/contact/contact.service';
import EnvironmentService from '@/entities/environment/environment.service';
import DataSourceService from '@/entities/data-source/data-source.service';
import DsSchemaService from '@/entities/ds-schema/ds-schema.service';
import DsSchemaRelationshipService from '@/entities/ds-schema-relationship/ds-schema-relationship.service';
import TablesDefinitionService from '@/entities/tables-definition/tables-definition.service';
import TableColumnService from '@/entities/table-column/table-column.service';
import TableRelationshipService from '@/entities/table-relationship/table-relationship.service';
import RelatedTableService from '@/entities/related-table/related-table.service';
import RelatedTableColumnService from '@/entities/related-table-column/related-table-column.service';
import FileSourceService from '@/entities/file-source/file-source.service';
import FileConfigService from '@/entities/file-config/file-config.service';
import FileInfoService from '@/entities/file-info/file-info.service';
import FileFieldService from '@/entities/file-field/file-field.service';
import AnalyzerEntitiesService from '@/entities/analyzer-entities/analyzer-entities.service';
import AnalyzerRecognizersService from '@/entities/analyzer-recognizers/analyzer-recognizers.service';
import AnalyzerAdHocRecognizersService from '@/entities/analyzer-ad-hoc-recognizers/analyzer-ad-hoc-recognizers.service';
import AnalyzerResultService from '@/entities/analyzer-result/analyzer-result.service';
import AnalyzerJobService from '@/entities/analyzer-job/analyzer-job.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

/* tslint:enable */
Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.use(ToastPlugin);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);
Vue.component('jhi-sort-indicator', JhiSortIndicatorComponent);
Vue.component('infinite-loading', InfiniteLoading);
const store = config.initVueXStore(Vue);

const loginService = new LoginService();
const accountService = new AccountService(store, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities).then(value => {
      if (!value) {
        sessionStorage.setItem('requested-url', to.fullPath);
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),
    healthService: () => new HealthService(),
    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),

    userOAuth2Service: () => new UserOAuth2Service(),
    organizationService: () => new OrganizationService(),
    businessUnitService: () => new BusinessUnitService(),
    applicationService: () => new ApplicationService(),
    contactService: () => new ContactService(),
    environmentService: () => new EnvironmentService(),
    dataSourceService: () => new DataSourceService(),
    dsSchemaService: () => new DsSchemaService(),
    dsSchemaRelationshipService: () => new DsSchemaRelationshipService(),
    tablesDefinitionService: () => new TablesDefinitionService(),
    tableColumnService: () => new TableColumnService(),
    tableRelationshipService: () => new TableRelationshipService(),
    relatedTableService: () => new RelatedTableService(),
    relatedTableColumnService: () => new RelatedTableColumnService(),
    fileSourceService: () => new FileSourceService(),
    fileConfigService: () => new FileConfigService(),
    fileInfoService: () => new FileInfoService(),
    fileFieldService: () => new FileFieldService(),
    analyzerEntitiesService: () => new AnalyzerEntitiesService(),
    analyzerRecognizersService: () => new AnalyzerRecognizersService(),
    analyzerAdHocRecognizersService: () => new AnalyzerAdHocRecognizersService(),
    analyzerResultService: () => new AnalyzerResultService(),
    analyzerJobService: () => new AnalyzerJobService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    accountService: () => accountService,
  },
  store,
});
