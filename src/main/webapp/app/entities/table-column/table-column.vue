<template>
  <div>
    <h2 id="page-heading" data-cy="TableColumnHeading">
      <span id="table-column-heading">Table Columns</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TableColumnCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-table-column"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Table Column </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tableColumns && tableColumns.length === 0">
      <span>No tableColumns found</span>
    </div>
    <div class="table-responsive" v-if="tableColumns && tableColumns.length > 0">
      <table class="table table-striped" aria-describedby="tableColumns">
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
            <th scope="row" v-on:click="changeOrder('columnSize')">
              <span>Column Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isNullable')">
              <span>Is Nullable</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isNullable'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isPrimaryKey')">
              <span>Is Primary Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isPrimaryKey'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isForeignKey')">
              <span>Is Foreign Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isForeignKey'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isIndexed')">
              <span>Is Indexed</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isIndexed'"></jhi-sort-indicator>
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
          <tr v-for="tableColumn in tableColumns" :key="tableColumn.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TableColumnView', params: { tableColumnId: tableColumn.id } }">{{ tableColumn.id }}</router-link>
            </td>
            <td>{{ tableColumn.columnName }}</td>
            <td>{{ tableColumn.columnType }}</td>
            <td>{{ tableColumn.columnSize }}</td>
            <td>{{ tableColumn.isNullable }}</td>
            <td>{{ tableColumn.isPrimaryKey }}</td>
            <td>{{ tableColumn.isForeignKey }}</td>
            <td>{{ tableColumn.isIndexed }}</td>
            <td>{{ tableColumn.creationDate | formatDate }}</td>
            <td>{{ tableColumn.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="tableColumn.tablesDefinition">
                <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tableColumn.tablesDefinition.id } }">{{
                  tableColumn.tablesDefinition.tableName
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TableColumnView', params: { tableColumnId: tableColumn.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TableColumnEdit', params: { tableColumnId: tableColumn.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tableColumn)"
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
        ><span id="dataelyAppV2App.tableColumn.delete.question" data-cy="tableColumnDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tableColumn-heading">Are you sure you want to delete this Table Column?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tableColumn"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTableColumn()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="tableColumns && tableColumns.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./table-column.component.ts"></script>
