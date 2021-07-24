<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.dsSchemaRelationship.home.createOrEditLabel" data-cy="DsSchemaRelationshipCreateUpdateHeading">
          Create or edit a DsSchemaRelationship
        </h2>
        <div>
          <div class="form-group" v-if="dsSchemaRelationship.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dsSchemaRelationship.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-relationship-source">Source</label>
            <input
              type="text"
              class="form-control"
              name="source"
              id="ds-schema-relationship-source"
              data-cy="source"
              :class="{ valid: !$v.dsSchemaRelationship.source.$invalid, invalid: $v.dsSchemaRelationship.source.$invalid }"
              v-model="$v.dsSchemaRelationship.source.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-relationship-target">Target</label>
            <input
              type="text"
              class="form-control"
              name="target"
              id="ds-schema-relationship-target"
              data-cy="target"
              :class="{ valid: !$v.dsSchemaRelationship.target.$invalid, invalid: $v.dsSchemaRelationship.target.$invalid }"
              v-model="$v.dsSchemaRelationship.target.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-relationship-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="ds-schema-relationship-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.dsSchemaRelationship.creationDate.$invalid, invalid: $v.dsSchemaRelationship.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.dsSchemaRelationship.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-relationship-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="ds-schema-relationship-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.dsSchemaRelationship.lastUpdated.$invalid, invalid: $v.dsSchemaRelationship.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.dsSchemaRelationship.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-relationship-dsSchema">Ds Schema</label>
            <select
              class="form-control"
              id="ds-schema-relationship-dsSchema"
              data-cy="dsSchema"
              name="dsSchema"
              v-model="dsSchemaRelationship.dsSchema"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  dsSchemaRelationship.dsSchema && dsSchemaOption.id === dsSchemaRelationship.dsSchema.id
                    ? dsSchemaRelationship.dsSchema
                    : dsSchemaOption
                "
                v-for="dsSchemaOption in dsSchemas"
                :key="dsSchemaOption.id"
              >
                {{ dsSchemaOption.name }}
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
            :disabled="$v.dsSchemaRelationship.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ds-schema-relationship-update.component.ts"></script>
