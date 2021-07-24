<template>
  <div>
    <h2 id="page-heading" data-cy="TablesDefinitionHeading">
      <span id="tables-definition-heading">Tables Definitions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TablesDefinitionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-tables-definition"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Tables Definition </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && tablesDefinitions && tablesDefinitions.length === 0">
      <span>No tablesDefinitions found</span>
    </div>
    <div class="table-responsive" v-if="tablesDefinitions && tablesDefinitions.length > 0">
      <table class="table table-striped" aria-describedby="tablesDefinitions">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tableName')">
              <span>Table Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tableName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('value')">
              <span>Value</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'value'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('symbolSize')">
              <span>Symbol Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'symbolSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category')">
              <span>Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCnt')">
              <span>Col Cnt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntNbr')">
              <span>Col Cnt Nbr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntNbr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntTB')">
              <span>Col Cnt TB</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntTB'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntSTR')">
              <span>Col Cnt STR</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntSTR'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntBL')">
              <span>Col Cnt BL</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntBL'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntPK')">
              <span>Col Cnt PK</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntPK'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntFK')">
              <span>Col Cnt FK</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntFK'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('colCntIX')">
              <span>Col Cnt IX</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'colCntIX'"></jhi-sort-indicator>
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
          <tr v-for="tablesDefinition in tablesDefinitions" :key="tablesDefinition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tablesDefinition.id } }">{{
                tablesDefinition.id
              }}</router-link>
            </td>
            <td>{{ tablesDefinition.tableName }}</td>
            <td>{{ tablesDefinition.value }}</td>
            <td>{{ tablesDefinition.symbolSize }}</td>
            <td>{{ tablesDefinition.category }}</td>
            <td>{{ tablesDefinition.colCnt }}</td>
            <td>{{ tablesDefinition.colCntNbr }}</td>
            <td>{{ tablesDefinition.colCntTB }}</td>
            <td>{{ tablesDefinition.colCntSTR }}</td>
            <td>{{ tablesDefinition.colCntBL }}</td>
            <td>{{ tablesDefinition.colCntPK }}</td>
            <td>{{ tablesDefinition.colCntFK }}</td>
            <td>{{ tablesDefinition.colCntIX }}</td>
            <td>{{ tablesDefinition.creationDate | formatDate }}</td>
            <td>{{ tablesDefinition.lastUpdated | formatDate }}</td>
            <td>
              <div v-if="tablesDefinition.dsSchema">
                <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: tablesDefinition.dsSchema.id } }">{{
                  tablesDefinition.dsSchema.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tablesDefinition.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TablesDefinitionEdit', params: { tablesDefinitionId: tablesDefinition.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(tablesDefinition)"
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
        ><span id="dataelyAppV2App.tablesDefinition.delete.question" data-cy="tablesDefinitionDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-tablesDefinition-heading">Are you sure you want to delete this Tables Definition?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-tablesDefinition"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTablesDefinition()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="tablesDefinitions && tablesDefinitions.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tables-definition.component.ts"></script>
