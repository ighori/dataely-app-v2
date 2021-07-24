<template>
  <div>
    <h2 id="page-heading" data-cy="AnalyzerRecognizersHeading">
      <span id="analyzer-recognizers-heading">Analyzer Recognizers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalyzerRecognizersCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analyzer-recognizers"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Analyzer Recognizers </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analyzerRecognizers && analyzerRecognizers.length === 0">
      <span>No analyzerRecognizers found</span>
    </div>
    <div class="table-responsive" v-if="analyzerRecognizers && analyzerRecognizers.length > 0">
      <table class="table table-striped" aria-describedby="analyzerRecognizers">
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
            <th scope="row" v-on:click="changeOrder('recognizerName')">
              <span>Recognizer Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recognizerName'"></jhi-sort-indicator>
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
          <tr v-for="analyzerRecognizers in analyzerRecognizers" :key="analyzerRecognizers.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnalyzerRecognizersView', params: { analyzerRecognizersId: analyzerRecognizers.id } }">{{
                analyzerRecognizers.id
              }}</router-link>
            </td>
            <td>{{ analyzerRecognizers.name }}</td>
            <td>{{ analyzerRecognizers.detail }}</td>
            <td>{{ analyzerRecognizers.recognizerName }}</td>
            <td>{{ analyzerRecognizers.creationDate | formatDate }}</td>
            <td>{{ analyzerRecognizers.lastUpdated | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AnalyzerRecognizersView', params: { analyzerRecognizersId: analyzerRecognizers.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AnalyzerRecognizersEdit', params: { analyzerRecognizersId: analyzerRecognizers.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analyzerRecognizers)"
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
        ><span id="dataelyAppV2App.analyzerRecognizers.delete.question" data-cy="analyzerRecognizersDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analyzerRecognizers-heading">Are you sure you want to delete this Analyzer Recognizers?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analyzerRecognizers"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAnalyzerRecognizers()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="analyzerRecognizers && analyzerRecognizers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analyzer-recognizers.component.ts"></script>
