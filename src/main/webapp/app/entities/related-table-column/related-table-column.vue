<template>
  <div>
    <h2 id="page-heading" data-cy="RelatedTableColumnHeading">
      <span id="related-table-column-heading">Related Table Columns</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'RelatedTableColumnCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-related-table-column"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Related Table Column </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && relatedTableColumns && relatedTableColumns.length === 0">
      <span>No relatedTableColumns found</span>
    </div>
    <div class="table-responsive" v-if="relatedTableColumns && relatedTableColumns.length > 0">
      <table class="table table-striped" aria-describedby="relatedTableColumns">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('columnName')">
              <span>Column Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('columnType')">
              <span>Column Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('relatedTable.tableName')">
              <span>Related Table</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'relatedTable.tableName'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="relatedTableColumn in relatedTableColumns" :key="relatedTableColumn.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RelatedTableColumnView', params: { relatedTableColumnId: relatedTableColumn.id } }">{{
                relatedTableColumn.id
              }}</router-link>
            </td>
            <td>{{ relatedTableColumn.columnName }}</td>
            <td>{{ relatedTableColumn.columnType }}</td>
            <td>{{ relatedTableColumn.creationDate | formatDate }}</td>
            <td>{{ relatedTableColumn.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="relatedTableColumn.relatedTable">
                <router-link :to="{ name: 'RelatedTableView', params: { relatedTableId: relatedTableColumn.relatedTable.id } }">{{
                  relatedTableColumn.relatedTable.tableName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RelatedTableColumnView', params: { relatedTableColumnId: relatedTableColumn.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RelatedTableColumnEdit', params: { relatedTableColumnId: relatedTableColumn.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(relatedTableColumn)"
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
        ><span id="dataelyAppV2App.relatedTableColumn.delete.question" data-cy="relatedTableColumnDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-relatedTableColumn-heading">Are you sure you want to delete this Related Table Column?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-relatedTableColumn"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeRelatedTableColumn()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="relatedTableColumns && relatedTableColumns.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./related-table-column.component.ts"></script>
