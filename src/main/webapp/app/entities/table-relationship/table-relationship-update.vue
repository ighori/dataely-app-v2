<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.tableRelationship.home.createOrEditLabel" data-cy="TableRelationshipCreateUpdateHeading">
          Create or edit a TableRelationship
        </h2>
        <div>
          <div class="form-group" v-if="tableRelationship.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tableRelationship.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-source">Source</label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="table-relationship-source"
              data-cy="source"
              :class="{ valid: !$v.tableRelationship.source.$invalid, invalid: $v.tableRelationship.source.$invalid }"
              v-model="$v.tableRelationship.source.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-target">Target</label>
            <input
              type="text"
              class="form-control"
              name="target"
              id="table-relationship-target"
              data-cy="target"
              :class="{ valid: !$v.tableRelationship.target.$invalid, invalid: $v.tableRelationship.target.$invalid }"
              v-model="$v.tableRelationship.target.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-sourceKey">Source Key</label>
            <input
              type="text"
              class="form-control"
              name="sourceKey"
              id="table-relationship-sourceKey"
              data-cy="sourceKey"
              :class="{ valid: !$v.tableRelationship.sourceKey.$invalid, invalid: $v.tableRelationship.sourceKey.$invalid }"
              v-model="$v.tableRelationship.sourceKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-targetKey">Target Key</label>
            <input
              type="text"
              class="form-control"
              name="targetKey"
              id="table-relationship-targetKey"
              data-cy="targetKey"
              :class="{ valid: !$v.tableRelationship.targetKey.$invalid, invalid: $v.tableRelationship.targetKey.$invalid }"
              v-model="$v.tableRelationship.targetKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="table-relationship-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.tableRelationship.creationDate.$invalid, invalid: $v.tableRelationship.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.tableRelationship.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="table-relationship-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.tableRelationship.lastUpdated.$invalid, invalid: $v.tableRelationship.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.tableRelationship.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="table-relationship-tablesDefinition">Tables Definition</label>
            <select
              class="form-control"
              id="table-relationship-tablesDefinition"
              data-cy="tablesDefinition"
              name="tablesDefinition"
              v-model="tableRelationship.tablesDefinition"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  tableRelationship.tablesDefinition && tablesDefinitionOption.id === tableRelationship.tablesDefinition.id
                    ? tableRelationship.tablesDefinition
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
            :disabled="$v.tableRelationship.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./table-relationship-update.component.ts"></script>
