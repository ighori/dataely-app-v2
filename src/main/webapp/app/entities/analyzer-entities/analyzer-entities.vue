<template>
  <div>
    <h2 id="page-heading" data-cy="AnalyzerEntitiesHeading">
      <span id="analyzer-entities-heading">Analyzer Entities</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalyzerEntitiesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analyzer-entities"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Analyzer Entities </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analyzerEntities && analyzerEntities.length === 0">
      <span>No analyzerEntities found</span>
    </div>
    <div class="table-responsive" v-if="analyzerEntities && analyzerEntities.length > 0">
      <table class="table table-striped" aria-describedby="analyzerEntities">
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
            <th scope="row" v-on:click="changeOrder('entityName')">
              <span>Entity Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'entityName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="analyzerEntities in analyzerEntities" :key="analyzerEntities.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnalyzerEntitiesView', params: { analyzerEntitiesId: analyzerEntities.id } }">{{
                analyzerEntities.id
              }}</router-link>
            </td>
            <td>{{ analyzerEntities.name }}</td>
            <td>{{ analyzerEntities.detail }}</td>
            <td>{{ analyzerEntities.entityName }}</td>
            <td>{{ analyzerEntities.creationDate | formatDate }}</td>
            <td>{{ analyzerEntities.lastUpdated | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AnalyzerEntitiesView', params: { analyzerEntitiesId: analyzerEntities.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AnalyzerEntitiesEdit', params: { analyzerEntitiesId: analyzerEntities.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analyzerEntities)"
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
        ><span id="dataelyAppV2App.analyzerEntities.delete.question" data-cy="analyzerEntitiesDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analyzerEntities-heading">Are you sure you want to delete this Analyzer Entities?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analyzerEntities"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAnalyzerEntities()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="analyzerEntities && analyzerEntities.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analyzer-entities.component.ts"></script>
