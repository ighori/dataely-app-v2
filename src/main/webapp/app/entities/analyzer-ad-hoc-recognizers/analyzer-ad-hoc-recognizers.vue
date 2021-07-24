<template>
  <div>
    <h2 id="page-heading" data-cy="AnalyzerAdHocRecognizersHeading">
      <span id="analyzer-ad-hoc-recognizers-heading">Analyzer Ad Hoc Recognizers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalyzerAdHocRecognizersCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analyzer-ad-hoc-recognizers"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Analyzer Ad Hoc Recognizers </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analyzerAdHocRecognizers && analyzerAdHocRecognizers.length === 0">
      <span>No analyzerAdHocRecognizers found</span>
    </div>
    <div class="table-responsive" v-if="analyzerAdHocRecognizers && analyzerAdHocRecognizers.length > 0">
      <table class="table table-striped" aria-describedby="analyzerAdHocRecognizers">
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
            <th scope="row" v-on:click="changeOrder('supportedLang')">
              <span>Supported Lang</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supportedLang'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('patternName')">
              <span>Pattern Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'patternName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('regex')">
              <span>Regex</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'regex'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('score')">
              <span>Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'score'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('context')">
              <span>Context</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'context'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('denyList')">
              <span>Deny List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'denyList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supportedEntity')">
              <span>Supported Entity</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supportedEntity'"></jhi-sort-indicator>
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
          <tr v-for="analyzerAdHocRecognizers in analyzerAdHocRecognizers" :key="analyzerAdHocRecognizers.id" data-cy="entityTable">
            <td>
              <router-link
                :to="{ name: 'AnalyzerAdHocRecognizersView', params: { analyzerAdHocRecognizersId: analyzerAdHocRecognizers.id } }"
                >{{ analyzerAdHocRecognizers.id }}</router-link
              >
            </td>
            <td>{{ analyzerAdHocRecognizers.name }}</td>
            <td>{{ analyzerAdHocRecognizers.detail }}</td>
            <td>{{ analyzerAdHocRecognizers.recognizerName }}</td>
            <td>{{ analyzerAdHocRecognizers.supportedLang }}</td>
            <td>{{ analyzerAdHocRecognizers.patternName }}</td>
            <td>{{ analyzerAdHocRecognizers.regex }}</td>
            <td>{{ analyzerAdHocRecognizers.score }}</td>
            <td>{{ analyzerAdHocRecognizers.context }}</td>
            <td>{{ analyzerAdHocRecognizers.denyList }}</td>
            <td>{{ analyzerAdHocRecognizers.supportedEntity }}</td>
            <td>{{ analyzerAdHocRecognizers.creationDate | formatDate }}</td>
            <td>{{ analyzerAdHocRecognizers.lastUpdated | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AnalyzerAdHocRecognizersView', params: { analyzerAdHocRecognizersId: analyzerAdHocRecognizers.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AnalyzerAdHocRecognizersEdit', params: { analyzerAdHocRecognizersId: analyzerAdHocRecognizers.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analyzerAdHocRecognizers)"
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
        ><span id="dataelyAppV2App.analyzerAdHocRecognizers.delete.question" data-cy="analyzerAdHocRecognizersDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analyzerAdHocRecognizers-heading">Are you sure you want to delete this Analyzer Ad Hoc Recognizers?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analyzerAdHocRecognizers"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAnalyzerAdHocRecognizers()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="analyzerAdHocRecognizers && analyzerAdHocRecognizers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analyzer-ad-hoc-recognizers.component.ts"></script>
