<template>
  <div>
    <h2 id="page-heading" data-cy="DsSchemaHeading">
      <span id="ds-schema-heading">Ds Schemas</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'DsSchemaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ds-schema"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Ds Schema </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dsSchemas && dsSchemas.length === 0">
      <span>No dsSchemas found</span>
    </div>
    <div class="table-responsive" v-if="dsSchemas && dsSchemas.length > 0">
      <table class="table table-striped" aria-describedby="dsSchemas">
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
            <th scope="row" v-on:click="changeOrder('tableCount')">
              <span>Table Count</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tableCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tableRelCount')">
              <span>Table Rel Count</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tableRelCount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dataSource.name')">
              <span>Data Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataSource.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dsSchema in dsSchemas" :key="dsSchema.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: dsSchema.id } }">{{ dsSchema.id }}</router-link>
            </td>
            <td>{{ dsSchema.name }}</td>
            <td>{{ dsSchema.detail }}</td>
            <td>{{ dsSchema.tableCount }}</td>
            <td>{{ dsSchema.tableRelCount }}</td>
            <td>{{ dsSchema.creationDate | formatDate }}</td>
            <td>{{ dsSchema.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="dsSchema.dataSource">
                <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: dsSchema.dataSource.id } }">{{
                  dsSchema.dataSource.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: dsSchema.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DsSchemaEdit', params: { dsSchemaId: dsSchema.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dsSchema)"
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
        ><span id="dataelyAppV2App.dsSchema.delete.question" data-cy="dsSchemaDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-dsSchema-heading">Are you sure you want to delete this Ds Schema?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-dsSchema"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeDsSchema()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="dsSchemas && dsSchemas.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ds-schema.component.ts"></script>
