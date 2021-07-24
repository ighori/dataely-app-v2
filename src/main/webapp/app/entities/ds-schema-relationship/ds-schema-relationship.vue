<template>
  <div>
    <h2 id="page-heading" data-cy="DsSchemaRelationshipHeading">
      <span id="ds-schema-relationship-heading">Ds Schema Relationships</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'DsSchemaRelationshipCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-ds-schema-relationship"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Ds Schema Relationship </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dsSchemaRelationships && dsSchemaRelationships.length === 0">
      <span>No dsSchemaRelationships found</span>
    </div>
    <div class="table-responsive" v-if="dsSchemaRelationships && dsSchemaRelationships.length > 0">
      <table class="table table-striped" aria-describedby="dsSchemaRelationships">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('source')">
              <span>Source</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('target')">
              <span>Target</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'target'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('creationDate')">
              <span>Creation Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'creationDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdated')">
              <span>Last Updated</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdated'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('dsSchema.name')">
              <span>Ds Schema</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dsSchema.name'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dsSchemaRelationship in dsSchemaRelationships" :key="dsSchemaRelationship.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DsSchemaRelationshipView', params: { dsSchemaRelationshipId: dsSchemaRelationship.id } }">{{
                dsSchemaRelationship.id
              }}</router-link>
            </td>
            <td>{{ dsSchemaRelationship.source }}</td>
            <td>{{ dsSchemaRelationship.target }}</td>
            <td>{{ dsSchemaRelationship.creationDate | formatDate }}</td>
            <td>{{ dsSchemaRelationship.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="dsSchemaRelationship.dsSchema">
                <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: dsSchemaRelationship.dsSchema.id } }">{{
                  dsSchemaRelationship.dsSchema.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DsSchemaRelationshipView', params: { dsSchemaRelationshipId: dsSchemaRelationship.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'DsSchemaRelationshipEdit', params: { dsSchemaRelationshipId: dsSchemaRelationship.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dsSchemaRelationship)"
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
        ><span id="dataelyAppV2App.dsSchemaRelationship.delete.question" data-cy="dsSchemaRelationshipDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-dsSchemaRelationship-heading">Are you sure you want to delete this Ds Schema Relationship?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-dsSchemaRelationship"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeDsSchemaRelationship()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="dsSchemaRelationships && dsSchemaRelationships.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ds-schema-relationship.component.ts"></script>
