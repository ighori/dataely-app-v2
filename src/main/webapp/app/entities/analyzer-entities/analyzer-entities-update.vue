<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.analyzerEntities.home.createOrEditLabel" data-cy="AnalyzerEntitiesCreateUpdateHeading">
          Create or edit a AnalyzerEntities
        </h2>
        <div>
          <div class="form-group" v-if="analyzerEntities.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="analyzerEntities.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-entities-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="analyzer-entities-name"
              data-cy="name"
              :class="{ valid: !$v.analyzerEntities.name.$invalid, invalid: $v.analyzerEntities.name.$invalid }"
              v-model="$v.analyzerEntities.name.$model"
              required
            />
            <div v-if="$v.analyzerEntities.name.$anyDirty && $v.analyzerEntities.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.analyzerEntities.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-entities-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="analyzer-entities-detail"
              data-cy="detail"
              :class="{ valid: !$v.analyzerEntities.detail.$invalid, invalid: $v.analyzerEntities.detail.$invalid }"
              v-model="$v.analyzerEntities.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-entities-entityName">Entity Name</label>
            <input
              type="text"
              class="form-control"
              name="entityName"
              id="analyzer-entities-entityName"
              data-cy="entityName"
              :class="{ valid: !$v.analyzerEntities.entityName.$invalid, invalid: $v.analyzerEntities.entityName.$invalid }"
              v-model="$v.analyzerEntities.entityName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-entities-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="analyzer-entities-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.analyzerEntities.creationDate.$invalid, invalid: $v.analyzerEntities.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerEntities.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-entities-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="analyzer-entities-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.analyzerEntities.lastUpdated.$invalid, invalid: $v.analyzerEntities.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerEntities.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
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
            :disabled="$v.analyzerEntities.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./analyzer-entities-update.component.ts"></script>
