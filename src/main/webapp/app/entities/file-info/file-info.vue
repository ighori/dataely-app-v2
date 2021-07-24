<template>
  <div>
    <h2 id="page-heading" data-cy="FileInfoHeading">
      <span id="file-info-heading">File Infos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FileInfoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-file-info"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new File Info </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fileInfos && fileInfos.length === 0">
      <span>No fileInfos found</span>
    </div>
    <div class="table-responsive" v-if="fileInfos && fileInfos.length > 0">
      <table class="table table-striped" aria-describedby="fileInfos">
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
            <th scope="row" v-on:click="changeOrder('fileType')">
              <span>File Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('filePath')">
              <span>File Path</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'filePath'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSource.name')">
              <span>File Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSource.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileConfig.name')">
              <span>File Config</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileConfig.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fileInfo in fileInfos" :key="fileInfo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: fileInfo.id } }">{{ fileInfo.id }}</router-link>
            </td>
            <td>{{ fileInfo.name }}</td>
            <td>{{ fileInfo.detail }}</td>
            <td>{{ fileInfo.fileType }}</td>
            <td>{{ fileInfo.filePath }}</td>
            <td>{{ fileInfo.columnNameLineNumber }}</td>
            <td>{{ fileInfo.encoding }}</td>
            <td>{{ fileInfo.separatorChar }}</td>
            <td>{{ fileInfo.quoteChar }}</td>
            <td>{{ fileInfo.escapeChar }}</td>
            <td>{{ fileInfo.fixedValueWidth }}</td>
            <td>{{ fileInfo.skipEmptyLines }}</td>
            <td>{{ fileInfo.skipEmptyColumns }}</td>
            <td>{{ fileInfo.failOnInconsistentLineWidth }}</td>
            <td>{{ fileInfo.skipEbcdicHeader }}</td>
            <td>{{ fileInfo.eolPresent }}</td>
            <td>{{ fileInfo.creationDate | formatDate }}</td>
            <td>{{ fileInfo.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="fileInfo.fileSource">
                <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: fileInfo.fileSource.id } }">{{
                  fileInfo.fileSource.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="fileInfo.fileConfig">
                <router-link :to="{ name: 'FileConfigView', params: { fileConfigId: fileInfo.fileConfig.id } }">{{
                  fileInfo.fileConfig.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: fileInfo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FileInfoEdit', params: { fileInfoId: fileInfo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fileInfo)"
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
        ><span id="dataelyAppV2App.fileInfo.delete.question" data-cy="fileInfoDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fileInfo-heading">Are you sure you want to delete this File Info?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fileInfo"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFileInfo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="fileInfos && fileInfos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-info.component.ts"></script>
