<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.tableColumn.home.createOrEditLabel" data-cy="TableColumnCreateUpdateHeading">
          Create or edit a TableColumn
        </h2>
        <div>
          <div class="form-group" v-if="tableColumn.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tableColumn.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-columnName">Column Name</label>
            <input
              type="text"
              class="form-control"
              name="columnName"
              id="table-column-columnName"
              data-cy="columnName"
              :class="{ valid: !$v.tableColumn.columnName.$invalid, invalid: $v.tableColumn.columnName.$invalid }"
              v-model="$v.tableColumn.columnName.$model"
              required
            />
            <div v-if="$v.tableColumn.columnName.$anyDirty && $v.tableColumn.columnName.$invalid">
              <small class="form-text text-danger" v-if="!$v.tableColumn.columnName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-columnType">Column Type</label>
            <input
              type="text"
              class="form-control"
              name="columnType"
              id="table-column-columnType"
              data-cy="columnType"
              :class="{ valid: !$v.tableColumn.columnType.$invalid, invalid: $v.tableColumn.columnType.$invalid }"
              v-model="$v.tableColumn.columnType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-columnSize">Column Size</label>
            <input
              type="number"
              class="form-control"
              name="columnSize"
              id="table-column-columnSize"
              data-cy="columnSize"
              :class="{ valid: !$v.tableColumn.columnSize.$invalid, invalid: $v.tableColumn.columnSize.$invalid }"
              v-model.number="$v.tableColumn.columnSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-isNullable">Is Nullable</label>
            <input
              type="checkbox"
              class="form-check"
              name="isNullable"
              id="table-column-isNullable"
              data-cy="isNullable"
              :class="{ valid: !$v.tableColumn.isNullable.$invalid, invalid: $v.tableColumn.isNullable.$invalid }"
              v-model="$v.tableColumn.isNullable.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-isPrimaryKey">Is Primary Key</label>
            <input
              type="checkbox"
              class="form-check"
              name="isPrimaryKey"
              id="table-column-isPrimaryKey"
              data-cy="isPrimaryKey"
              :class="{ valid: !$v.tableColumn.isPrimaryKey.$invalid, invalid: $v.tableColumn.isPrimaryKey.$invalid }"
              v-model="$v.tableColumn.isPrimaryKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-isForeignKey">Is Foreign Key</label>
            <input
              type="checkbox"
              class="form-check"
              name="isForeignKey"
              id="table-column-isForeignKey"
              data-cy="isForeignKey"
              :class="{ valid: !$v.tableColumn.isForeignKey.$invalid, invalid: $v.tableColumn.isForeignKey.$invalid }"
              v-model="$v.tableColumn.isForeignKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-isIndexed">Is Indexed</label>
            <input
              type="checkbox"
              class="form-check"
              name="isIndexed"
              id="table-column-isIndexed"
              data-cy="isIndexed"
              :class="{ valid: !$v.tableColumn.isIndexed.$invalid, invalid: $v.tableColumn.isIndexed.$invalid }"
              v-model="$v.tableColumn.isIndexed.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="table-column-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.tableColumn.creationDate.$invalid, invalid: $v.tableColumn.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.tableColumn.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="table-column-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.tableColumn.lastUpdated.$invalid, invalid: $v.tableColumn.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.tableColumn.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-column-tablesDefinition">Tables Definition</label>
            <select
              class="form-control"
              id="table-column-tablesDefinition"
              data-cy="tablesDefinition"
              name="tablesDefinition"
              v-model="tableColumn.tablesDefinition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  tableColumn.tablesDefinition && tablesDefinitionOption.id === tableColumn.tablesDefinition.id
                    ? tableColumn.tablesDefinition
                    : tablesDefinitionOption
                "
                v-for="tablesDefinitionOption in tablesDefinitions"
                :key="tablesDefinitionOption.id"
              >
                {{ tablesDefinitionOption.tableName }}
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
            :disabled="$v.tableColumn.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./table-column-update.component.ts"></script>
