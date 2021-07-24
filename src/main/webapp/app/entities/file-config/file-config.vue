<template>
  <div>
    <h2 id="page-heading" data-cy="FileConfigHeading">
      <span id="file-config-heading">File Configs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FileConfigCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-file-config"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new File Config </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fileConfigs && fileConfigs.length === 0">
      <span>No fileConfigs found</span>
    </div>
    <div class="table-responsive" v-if="fileConfigs && fileConfigs.length > 0">
      <table class="table table-striped" aria-describedby="fileConfigs">
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
            <th scope="row" v-on:click="changeOrder('columnNameLineNumber')">
              <span>Column Name Line Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'columnNameLineNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('encoding')">
              <span>Encoding</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'encoding'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('separatorChar')">
              <span>Separator Char</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'separatorChar'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('quoteChar')">
              <span>Quote Char</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quoteChar'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('escapeChar')">
              <span>Escape Char</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'escapeChar'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fixedValueWidth')">
              <span>Fixed Value Width</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fixedValueWidth'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skipEmptyLines')">
              <span>Skip Empty Lines</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skipEmptyLines'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skipEmptyColumns')">
              <span>Skip Empty Columns</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skipEmptyColumns'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('failOnInconsistentLineWidth')">
              <span>Fail On Inconsistent Line Width</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'failOnInconsistentLineWidth'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('skipEbcdicHeader')">
              <span>Skip Ebcdic Header</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'skipEbcdicHeader'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eolPresent')">
              <span>Eol Present</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eolPresent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileType')">
              <span>File Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator>
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
          <tr v-for="fileConfig in fileConfigs" :key="fileConfig.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileConfigView', params: { fileConfigId: fileConfig.id } }">{{ fileConfig.id }}</router-link>
            </td>
            <td>{{ fileConfig.name }}</td>
            <td>{{ fileConfig.detail }}</td>
            <td>{{ fileConfig.columnNameLineNumber }}</td>
            <td>{{ fileConfig.encoding }}</td>
            <td>{{ fileConfig.separatorChar }}</td>
            <td>{{ fileConfig.quoteChar }}</td>
            <td>{{ fileConfig.escapeChar }}</td>
            <td>{{ fileConfig.fixedValueWidth }}</td>
            <td>{{ fileConfig.skipEmptyLines }}</td>
            <td>{{ fileConfig.skipEmptyColumns }}</td>
            <td>{{ fileConfig.failOnInconsistentLineWidth }}</td>
            <td>{{ fileConfig.skipEbcdicHeader }}</td>
            <td>{{ fileConfig.eolPresent }}</td>
            <td>{{ fileConfig.fileType }}</td>
            <td>{{ fileConfig.creationDate | formatDate }}</td>
            <td>{{ fileConfig.lastUpdated | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FileConfigView', params: { fileConfigId: fileConfig.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FileConfigEdit', params: { fileConfigId: fileConfig.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fileConfig)"
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
        ><span id="dataelyAppV2App.fileConfig.delete.question" data-cy="fileConfigDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fileConfig-heading">Are you sure you want to delete this File Config?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fileConfig"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFileConfig()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="fileConfigs && fileConfigs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-config.component.ts"></script>
