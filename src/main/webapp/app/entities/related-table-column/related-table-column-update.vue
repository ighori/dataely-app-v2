<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.relatedTableColumn.home.createOrEditLabel" data-cy="RelatedTableColumnCreateUpdateHeading">
          Create or edit a RelatedTableColumn
        </h2>
        <div>
          <div class="form-group" v-if="relatedTableColumn.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="relatedTableColumn.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-column-columnName">Column Name</label>
            <input
              type="text"
              class="form-control"
              name="columnName"
              id="related-table-column-columnName"
              data-cy="columnName"
              :class="{ valid: !$v.relatedTableColumn.columnName.$invalid, invalid: $v.relatedTableColumn.columnName.$invalid }"
              v-model="$v.relatedTableColumn.columnName.$model"
              required
            />
            <div v-if="$v.relatedTableColumn.columnName.$anyDirty && $v.relatedTableColumn.columnName.$invalid">
              <small class="form-text text-danger" v-if="!$v.relatedTableColumn.columnName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-column-columnType">Column Type</label>
            <input
              type="text"
              class="form-control"
              name="columnType"
              id="related-table-column-columnType"
              data-cy="columnType"
              :class="{ valid: !$v.relatedTableColumn.columnType.$invalid, invalid: $v.relatedTableColumn.columnType.$invalid }"
              v-model="$v.relatedTableColumn.columnType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-column-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="related-table-column-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.relatedTableColumn.creationDate.$invalid, invalid: $v.relatedTableColumn.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.relatedTableColumn.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-column-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="related-table-column-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.relatedTableColumn.lastUpdated.$invalid, invalid: $v.relatedTableColumn.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.relatedTableColumn.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-column-relatedTable">Related Table</label>
            <select
              class="form-control"
              id="related-table-column-relatedTable"
              data-cy="relatedTable"
              name="relatedTable"
              v-model="relatedTableColumn.relatedTable"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  relatedTableColumn.relatedTable && relatedTableOption.id === relatedTableColumn.relatedTable.id
                    ? relatedTableColumn.relatedTable
                    : relatedTableOption
                "
                v-for="relatedTableOption in relatedTables"
                :key="relatedTableOption.id"
              >
                {{ relatedTableOption.tableName }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.relatedTableColumn.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./related-table-column-update.component.ts"></script>
