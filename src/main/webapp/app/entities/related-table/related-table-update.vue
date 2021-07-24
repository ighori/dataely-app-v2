<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.relatedTable.home.createOrEditLabel" data-cy="RelatedTableCreateUpdateHeading">
          Create or edit a RelatedTable
        </h2>
        <div>
          <div class="form-group" v-if="relatedTable.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="relatedTable.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-tableName">Table Name</label>
            <input
              type="text"
              class="form-control"
              name="tableName"
              id="related-table-tableName"
              data-cy="tableName"
              :class="{ valid: !$v.relatedTable.tableName.$invalid, invalid: $v.relatedTable.tableName.$invalid }"
              v-model="$v.relatedTable.tableName.$model"
              required
            />
            <div v-if="$v.relatedTable.tableName.$anyDirty && $v.relatedTable.tableName.$invalid">
              <small class="form-text text-danger" v-if="!$v.relatedTable.tableName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="related-table-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.relatedTable.creationDate.$invalid, invalid: $v.relatedTable.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.relatedTable.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="related-table-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.relatedTable.lastUpdated.$invalid, invalid: $v.relatedTable.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.relatedTable.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="related-table-tablesDefinition">Tables Definition</label>
            <select
              class="form-control"
              id="related-table-tablesDefinition"
              data-cy="tablesDefinition"
              name="tablesDefinition"
              v-model="relatedTable.tablesDefinition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  relatedTable.tablesDefinition && tablesDefinitionOption.id === relatedTable.tablesDefinition.id
                    ? relatedTable.tablesDefinition
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
            :disabled="$v.relatedTable.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./related-table-update.component.ts"></script>
