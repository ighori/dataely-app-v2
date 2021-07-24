<template>
  <div>
    <h2 id="page-heading" data-cy="RelatedTableHeading">
      <span id="related-table-heading">Related Tables</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'RelatedTableCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-related-table"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Related Table </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && relatedTables && relatedTables.length === 0">
      <span>No relatedTables found</span>
    </div>
    <div class="table-responsive" v-if="relatedTables && relatedTables.length > 0">
      <table class="table table-striped" aria-describedby="relatedTables">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tableName')">
              <span>Table Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tableName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tablesDefinition.tableName')">
              <span>Tables Definition</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'tablesDefinition.tableName'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="relatedTable in relatedTables" :key="relatedTable.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RelatedTableView', params: { relatedTableId: relatedTable.id } }">{{
                relatedTable.id
              }}</router-link>
            </td>
            <td>{{ relatedTable.tableName }}</td>
            <td>{{ relatedTable.creationDate | formatDate }}</td>
            <td>{{ relatedTable.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="relatedTable.tablesDefinition">
                <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: relatedTable.tablesDefinition.id } }">{{
                  relatedTable.tablesDefinition.tableName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RelatedTableView', params: { relatedTableId: relatedTable.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RelatedTableEdit', params: { relatedTableId: relatedTable.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(relatedTable)"
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
        ><span id="dataelyAppV2App.relatedTable.delete.question" data-cy="relatedTableDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-relatedTable-heading">Are you sure you want to delete this Related Table?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-relatedTable"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeRelatedTable()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="relatedTables && relatedTables.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./related-table.component.ts"></script>
