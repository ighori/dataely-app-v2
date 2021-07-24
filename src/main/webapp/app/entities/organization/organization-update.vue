<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.organization.home.createOrEditLabel" data-cy="OrganizationCreateUpdateHeading">
          Create or edit a Organization
        </h2>
        <div>
          <div class="form-group" v-if="organization.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="organization.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="organization-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="organization-name"
              data-cy="name"
              :class="{ valid: !$v.organization.name.$invalid, invalid: $v.organization.name.$invalid }"
              v-model="$v.organization.name.$model"
              required
            />
            <div v-if="$v.organization.name.$anyDirty && $v.organization.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.organization.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="organization-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="organization-detail"
              data-cy="detail"
              :class="{ valid: !$v.organization.detail.$invalid, invalid: $v.organization.detail.$invalid }"
              v-model="$v.organization.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="organization-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="organization-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.organization.creationDate.$invalid, invalid: $v.organization.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.organization.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="organization-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="organization-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.organization.lastUpdated.$invalid, invalid: $v.organization.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.organization.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="organization-user">User</label>
            <select class="form-control" id="organization-user" data-cy="user" name="user" v-model="organization.user" required>
              <option v-if="!organization.user" v-bind:value="null" selected></option>
              <option
                v-bind:value="organization.user && userOption.id === organization.user.id ? organization.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div v-if="$v.organization.user.$anyDirty && $v.organization.user.$invalid">
            <small class="form-text text-danger" v-if="!$v.organization.user.required"> This field is required. </small>
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
            :disabled="$v.organization.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./organization-update.component.ts"></script>
