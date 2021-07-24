<template>
  <div>
    <h2 id="page-heading" data-cy="FileFieldHeading">
      <span id="file-field-heading">File Fields</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'FileFieldCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-file-field"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new File Field </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && fileFields && fileFields.length === 0">
      <span>No fileFields found</span>
    </div>
    <div class="table-responsive" v-if="fileFields && fileFields.length > 0">
      <table class="table table-striped" aria-describedby="fileFields">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldName')">
              <span>Field Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldType')">
              <span>Field Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldSize')">
              <span>Field Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileInfo.name')">
              <span>File Info</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileInfo.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="fileField in fileFields" :key="fileField.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FileFieldView', params: { fileFieldId: fileField.id } }">{{ fileField.id }}</router-link>
            </td>
            <td>{{ fileField.fieldName }}</td>
            <td>{{ fileField.fieldType }}</td>
            <td>{{ fileField.fieldSize }}</td>
            <td>{{ fileField.creationDate | formatDate }}</td>
            <td>{{ fileField.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="fileField.fileInfo">
                <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: fileField.fileInfo.id } }">{{
                  fileField.fileInfo.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FileFieldView', params: { fileFieldId: fileField.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FileFieldEdit', params: { fileFieldId: fileField.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(fileField)"
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
        ><span id="dataelyAppV2App.fileField.delete.question" data-cy="fileFieldDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-fileField-heading">Are you sure you want to delete this File Field?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-fileField"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeFileField()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="fileFields && fileFields.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-field.component.ts"></script>
