<template>
  <div>
    <h2 id="page-heading" data-cy="DataSourceHeading">
      <span id="data-source-heading">Data Sources</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'DataSourceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-data-source"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Data Source </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dataSources && dataSources.length === 0">
      <span>No dataSources found</span>
    </div>
    <div class="table-responsive" v-if="dataSources && dataSources.length > 0">
      <table class="table table-striped" aria-describedby="dataSources">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('detail')">
              <span>Detail</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'detail'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('databaseName')">
              <span>Database Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'databaseName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('instanceName')">
              <span>Instance Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'instanceName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('schemaName')">
              <span>Schema Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'schemaName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hostname')">
              <span>Hostname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hostname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('port')">
              <span>Port</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'port'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('username')">
              <span>Username</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'username'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('password')">
              <span>Password</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'password'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icon')">
              <span>Icon</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icon'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dbType')">
              <span>Db Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dbType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dbVersion')">
              <span>Db Version</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dbVersion'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('schemaCount')">
              <span>Schema Count</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'schemaCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dsType')">
              <span>Ds Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dsType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('driverClassName')">
              <span>Driver Class Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'driverClassName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('jdbcUrl')">
              <span>Jdbc Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'jdbcUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sid')">
              <span>Sid</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sid'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('environment.name')">
              <span>Environment</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'environment.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dataSource in dataSources" :key="dataSource.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: dataSource.id } }">{{ dataSource.id }}</router-link>
            </td>
            <td>{{ dataSource.name }}</td>
            <td>{{ dataSource.detail }}</td>
            <td>{{ dataSource.databaseName }}</td>
            <td>{{ dataSource.instanceName }}</td>
            <td>{{ dataSource.schemaName }}</td>
            <td>{{ dataSource.hostname }}</td>
            <td>{{ dataSource.port }}</td>
            <td>{{ dataSource.username }}</td>
            <td>{{ dataSource.password }}</td>
            <td>{{ dataSource.icon }}</td>
            <td>{{ dataSource.dbType }}</td>
            <td>{{ dataSource.dbVersion }}</td>
            <td>{{ dataSource.schemaCount }}</td>
            <td>{{ dataSource.dsType }}</td>
            <td>{{ dataSource.driverClassName }}</td>
            <td>{{ dataSource.jdbcUrl }}</td>
            <td>{{ dataSource.sid }}</td>
            <td>{{ dataSource.creationDate | formatDate }}</td>
            <td>{{ dataSource.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="dataSource.environment">
                <router-link :to="{ name: 'EnvironmentView', params: { environmentId: dataSource.environment.id } }">{{
                  dataSource.environment.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: dataSource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DataSourceEdit', params: { dataSourceId: dataSource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dataSource)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="dataelyAppV2App.dataSource.delete.question" data-cy="dataSourceDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-dataSource-heading">Are you sure you want to delete this Data Source?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-dataSource"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeDataSource()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="dataSources && dataSources.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./data-source.component.ts"></script>
