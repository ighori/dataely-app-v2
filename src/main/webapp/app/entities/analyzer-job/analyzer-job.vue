<template>
  <div>
    <h2 id="page-heading" data-cy="AnalyzerJobHeading">
      <span id="analyzer-job-heading">Analyzer Jobs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnalyzerJobCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-analyzer-job"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Analyzer Job </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && analyzerJobs && analyzerJobs.length === 0">
      <span>No analyzerJobs found</span>
    </div>
    <div class="table-responsive" v-if="analyzerJobs && analyzerJobs.length > 0">
      <table class="table table-striped" aria-describedby="analyzerJobs">
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
            <th scope="row" v-on:click="changeOrder('endTime')">
              <span>End Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('startTime')">
              <span>Start Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span>Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('previousRunTime')">
              <span>Previous Run Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'previousRunTime'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('dataSource.name')">
              <span>Data Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dataSource.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSource.name')">
              <span>File Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSource.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('user.login')">
              <span>User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'user.login'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="analyzerJob in analyzerJobs" :key="analyzerJob.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnalyzerJobView', params: { analyzerJobId: analyzerJob.id } }">{{ analyzerJob.id }}</router-link>
            </td>
            <td>{{ analyzerJob.name }}</td>
            <td>{{ analyzerJob.detail }}</td>
            <td>{{ analyzerJob.endTime | formatDate }}</td>
            <td>{{ analyzerJob.startTime | formatDate }}</td>
            <td>{{ analyzerJob.status }}</td>
            <td>{{ analyzerJob.previousRunTime }}</td>
            <td>{{ analyzerJob.creationDate | formatDate }}</td>
            <td>{{ analyzerJob.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="analyzerJob.environment">
                <router-link :to="{ name: 'EnvironmentView', params: { environmentId: analyzerJob.environment.id } }">{{
                  analyzerJob.environment.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="analyzerJob.dataSource">
                <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: analyzerJob.dataSource.id } }">{{
                  analyzerJob.dataSource.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="analyzerJob.fileSource">
                <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: analyzerJob.fileSource.id } }">{{
                  analyzerJob.fileSource.name
                }}</router-link>
              </div>
            </td>
            <td>
              {{ analyzerJob.user ? analyzerJob.user.login : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AnalyzerJobView', params: { analyzerJobId: analyzerJob.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AnalyzerJobEdit', params: { analyzerJobId: analyzerJob.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(analyzerJob)"
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
        ><span id="dataelyAppV2App.analyzerJob.delete.question" data-cy="analyzerJobDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-analyzerJob-heading">Are you sure you want to delete this Analyzer Job?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-analyzerJob"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeAnalyzerJob()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="analyzerJobs && analyzerJobs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./analyzer-job.component.ts"></script>
