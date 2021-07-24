<template>
  <div>
    <h2 id="page-heading" data-cy="AnalyzerResultHeading">
      <span id="analyzer-result-heading">Analyzer Results</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalyzerResultCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analyzer-result"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Analyzer Result </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analyzerResults && analyzerResults.length === 0">
      <span>No analyzerResults found</span>
    </div>
    <div class="table-responsive" v-if="analyzerResults && analyzerResults.length > 0">
      <table class="table table-striped" aria-describedby="analyzerResults">
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
            <th scope="row" v-on:click="changeOrder('objectId')">
              <span>Object Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objectId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('objectType')">
              <span>Object Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'objectType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldId')">
              <span>Field Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldType')">
              <span>Field Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('entityType')">
              <span>Entity Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'entityType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('start')">
              <span>Start</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'start'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('end')">
              <span>End</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'end'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('score')">
              <span>Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'score'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('recognizer')">
              <span>Recognizer</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recognizer'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('patternName')">
              <span>Pattern Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'patternName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('patternExpr')">
              <span>Pattern Expr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'patternExpr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('originalScore')">
              <span>Original Score</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'originalScore'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('textualExplanation')">
              <span>Textual Explanation</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'textualExplanation'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supportiveContextWord')">
              <span>Supportive Context Word</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supportiveContextWord'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('validationResult')">
              <span>Validation Result</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'validationResult'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('fileSource.name')">
              <span>File Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSource.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="analyzerResult in analyzerResults" :key="analyzerResult.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnalyzerResultView', params: { analyzerResultId: analyzerResult.id } }">{{
                analyzerResult.id
              }}</router-link>
            </td>
            <td>{{ analyzerResult.name }}</td>
            <td>{{ analyzerResult.detail }}</td>
            <td>{{ analyzerResult.objectId }}</td>
            <td>{{ analyzerResult.objectType }}</td>
            <td>{{ analyzerResult.fieldId }}</td>
            <td>{{ analyzerResult.fieldType }}</td>
            <td>{{ analyzerResult.entityType }}</td>
            <td>{{ analyzerResult.start }}</td>
            <td>{{ analyzerResult.end }}</td>
            <td>{{ analyzerResult.score }}</td>
            <td>{{ analyzerResult.recognizer }}</td>
            <td>{{ analyzerResult.patternName }}</td>
            <td>{{ analyzerResult.patternExpr }}</td>
            <td>{{ analyzerResult.originalScore }}</td>
            <td>{{ analyzerResult.textualExplanation }}</td>
            <td>{{ analyzerResult.supportiveContextWord }}</td>
            <td>{{ analyzerResult.validationResult }}</td>
            <td>{{ analyzerResult.creationDate | formatDate }}</td>
            <td>{{ analyzerResult.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="analyzerResult.dataSource">
                <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: analyzerResult.dataSource.id } }">{{
                  analyzerResult.dataSource.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="analyzerResult.fileSource">
                <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: analyzerResult.fileSource.id } }">{{
                  analyzerResult.fileSource.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AnalyzerResultView', params: { analyzerResultId: analyzerResult.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AnalyzerResultEdit', params: { analyzerResultId: analyzerResult.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analyzerResult)"
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
        ><span id="dataelyAppV2App.analyzerResult.delete.question" data-cy="analyzerResultDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analyzerResult-heading">Are you sure you want to delete this Analyzer Result?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analyzerResult"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAnalyzerResult()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="analyzerResults && analyzerResults.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analyzer-result.component.ts"></script>
