<template>
  <div>
    <h2 id="page-heading" data-cy="FileSourceHeading">
      <span id="file-source-heading">File Sources</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FileSourceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-file-source"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new File Source </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fileSources && fileSources.length === 0">
      <span>No fileSources found</span>
    </div>
    <div class="table-responsive" v-if="fileSources && fileSources.length > 0">
      <table class="table table-striped" aria-describedby="fileSources">
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
            <th scope="row" v-on:click="changeOrder('hostname')">
              <span>Hostname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hostname'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('port')">
              <span>Port</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'port'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('username')">
              <span>Username</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'username'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('password')">
              <span>Password</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'password'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('icon')">
              <span>Icon</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'icon'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('connectionType')">
              <span>Connection Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'connectionType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span>Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('region')">
              <span>Region</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'region'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ignoreDottedFiles')">
              <span>Ignore Dotted Files</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ignoreDottedFiles'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('recurse')">
              <span>Recurse</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recurse'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pathFilterRegex')">
              <span>Path Filter Regex</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pathFilterRegex'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('remotePath')">
              <span>Remote Path</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'remotePath'"></jhi-sort-indicator>
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
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fileSource in fileSources" :key="fileSource.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: fileSource.id } }">{{ fileSource.id }}</router-link>
            </td>
            <td>{{ fileSource.name }}</td>
            <td>{{ fileSource.detail }}</td>
            <td>{{ fileSource.hostname }}</td>
            <td>{{ fileSource.port }}</td>
            <td>{{ fileSource.username }}</td>
            <td>{{ fileSource.password }}</td>
            <td>{{ fileSource.icon }}</td>
            <td>{{ fileSource.connectionType }}</td>
            <td>{{ fileSource.type }}</td>
            <td>{{ fileSource.region }}</td>
            <td>{{ fileSource.ignoreDottedFiles }}</td>
            <td>{{ fileSource.recurse }}</td>
            <td>{{ fileSource.pathFilterRegex }}</td>
            <td>{{ fileSource.remotePath }}</td>
            <td>{{ fileSource.creationDate | formatDate }}</td>
            <td>{{ fileSource.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="fileSource.environment">
                <router-link :to="{ name: 'EnvironmentView', params: { environmentId: fileSource.environment.id } }">{{
                  fileSource.environment.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: fileSource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FileSourceEdit', params: { fileSourceId: fileSource.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fileSource)"
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
        ><span id="dataelyAppV2App.fileSource.delete.question" data-cy="fileSourceDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fileSource-heading">Are you sure you want to delete this File Source?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fileSource"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFileSource()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="fileSources && fileSources.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-source.component.ts"></script>
