<template>
  <div>
    <h2 id="page-heading" data-cy="TableRelationshipHeading">
      <span id="table-relationship-heading">Table Relationships</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TableRelationshipCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-table-relationship"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Table Relationship </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tableRelationships && tableRelationships.length === 0">
      <span>No tableRelationships found</span>
    </div>
    <div class="table-responsive" v-if="tableRelationships && tableRelationships.length > 0">
      <table class="table table-striped" aria-describedby="tableRelationships">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('source')">
              <span>Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('target')">
              <span>Target</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'target'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sourceKey')">
              <span>Source Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sourceKey'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('targetKey')">
              <span>Target Key</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'targetKey'"></jhi-sort-indicator>
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
          <tr v-for="tableRelationship in tableRelationships" :key="tableRelationship.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TableRelationshipView', params: { tableRelationshipId: tableRelationship.id } }">{{
                tableRelationship.id
              }}</router-link>
            </td>
            <td>{{ tableRelationship.source }}</td>
            <td>{{ tableRelationship.target }}</td>
            <td>{{ tableRelationship.sourceKey }}</td>
            <td>{{ tableRelationship.targetKey }}</td>
            <td>{{ tableRelationship.creationDate | formatDate }}</td>
            <td>{{ tableRelationship.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="tableRelationship.tablesDefinition">
                <router-link
                  :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tableRelationship.tablesDefinition.id } }"
                  >{{ tableRelationship.tablesDefinition.tableName }}</router-link
                >
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TableRelationshipView', params: { tableRelationshipId: tableRelationship.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TableRelationshipEdit', params: { tableRelationshipId: tableRelationship.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tableRelationship)"
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
        ><span id="dataelyAppV2App.tableRelationship.delete.question" data-cy="tableRelationshipDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tableRelationship-heading">Are you sure you want to delete this Table Relationship?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tableRelationship"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTableRelationship()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="tableRelationships && tableRelationships.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./table-relationship.component.ts"></script>
