<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.environment.home.createOrEditLabel" data-cy="EnvironmentCreateUpdateHeading">
          Create or edit a Environment
        </h2>
        <div>
          <div class="form-group" v-if="environment.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="environment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="environment-name"
              data-cy="name"
              :class="{ valid: !$v.environment.name.$invalid, invalid: $v.environment.name.$invalid }"
              v-model="$v.environment.name.$model"
              required
            />
            <div v-if="$v.environment.name.$anyDirty && $v.environment.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.environment.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="environment-detail"
              data-cy="detail"
              :class="{ valid: !$v.environment.detail.$invalid, invalid: $v.environment.detail.$invalid }"
              v-model="$v.environment.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.environment.type.$invalid, invalid: $v.environment.type.$invalid }"
              v-model="$v.environment.type.$model"
              id="environment-type"
              data-cy="type"
              required
            >
              <option value="STANDALONE">STANDALONE</option>
              <option value="INTEGRATED">INTEGRATED</option>
              <option value="DEVELOPMENT">DEVELOPMENT</option>
              <option value="TESTING">TESTING</option>
              <option value="TRAINING">TRAINING</option>
              <option value="STAGING">STAGING</option>
              <option value="PRODUCTION">PRODUCTION</option>
              <option value="OTHER">OTHER</option>
            </select>
            <div v-if="$v.environment.type.$anyDirty && $v.environment.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.environment.type.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-purpose">Purpose</label>
            <select
              class="form-control"
              name="purpose"
              :class="{ valid: !$v.environment.purpose.$invalid, invalid: $v.environment.purpose.$invalid }"
              v-model="$v.environment.purpose.$model"
              id="environment-purpose"
              data-cy="purpose"
              required
            >
              <option value="PROFILING">PROFILING</option>
              <option value="MASKING">MASKING</option>
              <option value="REPORTING">REPORTING</option>
            </select>
            <div v-if="$v.environment.purpose.$anyDirty && $v.environment.purpose.$invalid">
              <small class="form-text text-danger" v-if="!$v.environment.purpose.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="environment-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.environment.creationDate.$invalid, invalid: $v.environment.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.environment.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="environment-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.environment.lastUpdated.$invalid, invalid: $v.environment.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.environment.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-contact">Contact</label>
            <select class="form-control" id="environment-contact" data-cy="contact" name="contact" v-model="environment.contact">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="environment.contact && contactOption.id === environment.contact.id ? environment.contact : contactOption"
                v-for="contactOption in contacts"
                :key="contactOption.id"
              >
                {{ contactOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="environment-application">Application</label>
            <select
              class="form-control"
              id="environment-application"
              data-cy="application"
              name="application"
              v-model="environment.application"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  environment.application && applicationOption.id === environment.application.id
                    ? environment.application
                    : applicationOption
                "
                v-for="applicationOption in applications"
                :key="applicationOption.id"
              >
                {{ applicationOption.name }}
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
            :disabled="$v.environment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./environment-update.component.ts"></script>
