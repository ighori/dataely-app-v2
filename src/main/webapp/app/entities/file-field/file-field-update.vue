<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.fileField.home.createOrEditLabel" data-cy="FileFieldCreateUpdateHeading">Create or edit a FileField</h2>
        <div>
          <div class="form-group" v-if="fileField.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fileField.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-fieldName">Field Name</label>
            <input
              type="text"
              class="form-control"
              name="fieldName"
              id="file-field-fieldName"
              data-cy="fieldName"
              :class="{ valid: !$v.fileField.fieldName.$invalid, invalid: $v.fileField.fieldName.$invalid }"
              v-model="$v.fileField.fieldName.$model"
              required
            />
            <div v-if="$v.fileField.fieldName.$anyDirty && $v.fileField.fieldName.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileField.fieldName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-fieldType">Field Type</label>
            <input
              type="text"
              class="form-control"
              name="fieldType"
              id="file-field-fieldType"
              data-cy="fieldType"
              :class="{ valid: !$v.fileField.fieldType.$invalid, invalid: $v.fileField.fieldType.$invalid }"
              v-model="$v.fileField.fieldType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-fieldSize">Field Size</label>
            <input
              type="number"
              class="form-control"
              name="fieldSize"
              id="file-field-fieldSize"
              data-cy="fieldSize"
              :class="{ valid: !$v.fileField.fieldSize.$invalid, invalid: $v.fileField.fieldSize.$invalid }"
              v-model.number="$v.fileField.fieldSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="file-field-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.fileField.creationDate.$invalid, invalid: $v.fileField.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.fileField.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="file-field-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.fileField.lastUpdated.$invalid, invalid: $v.fileField.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.fileField.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-field-fileInfo">File Info</label>
            <select class="form-control" id="file-field-fileInfo" data-cy="fileInfo" name="fileInfo" v-model="fileField.fileInfo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="fileField.fileInfo && fileInfoOption.id === fileField.fileInfo.id ? fileField.fileInfo : fileInfoOption"
                v-for="fileInfoOption in fileInfos"
                :key="fileInfoOption.id"
              >
                {{ fileInfoOption.name }}
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
            :disabled="$v.fileField.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-field-update.component.ts"></script>
