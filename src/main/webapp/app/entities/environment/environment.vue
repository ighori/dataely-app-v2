<template>
  <div>
    <h2 id="page-heading" data-cy="EnvironmentHeading">
      <span id="environment-heading">Environments</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'EnvironmentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-environment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Environment </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && environments && environments.length === 0">
      <span>No environments found</span>
    </div>
    <div class="table-responsive" v-if="environments && environments.length > 0">
      <table class="table table-striped" aria-describedby="environments">
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
            <th scope="row" v-on:click="changeOrder('type')">
              <span>Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('purpose')">
              <span>Purpose</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'purpose'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contact.name')">
              <span>Contact</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contact.name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('application.name')">
              <span>Application</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'application.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="environment in environments" :key="environment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EnvironmentView', params: { environmentId: environment.id } }">{{ environment.id }}</router-link>
            </td>
            <td>{{ environment.name }}</td>
            <td>{{ environment.detail }}</td>
            <td>{{ environment.type }}</td>
            <td>{{ environment.purpose }}</td>
            <td>{{ environment.creationDate | formatDate }}</td>
            <td>{{ environment.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="environment.contact">
                <router-link :to="{ name: 'ContactView', params: { contactId: environment.contact.id } }">{{
                  environment.contact.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="environment.application">
                <router-link :to="{ name: 'ApplicationView', params: { applicationId: environment.application.id } }">{{
                  environment.application.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EnvironmentView', params: { environmentId: environment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EnvironmentEdit', params: { environmentId: environment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(environment)"
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
        ><span id="dataelyAppV2App.environment.delete.question" data-cy="environmentDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-environment-heading">Are you sure you want to delete this Environment?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-environment"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeEnvironment()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="environments && environments.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./environment.component.ts"></script>
