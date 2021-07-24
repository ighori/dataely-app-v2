<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.businessUnit.home.createOrEditLabel" data-cy="BusinessUnitCreateUpdateHeading">
          Create or edit a BusinessUnit
        </h2>
        <div>
          <div class="form-group" v-if="businessUnit.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="businessUnit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="business-unit-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="business-unit-name"
              data-cy="name"
              :class="{ valid: !$v.businessUnit.name.$invalid, invalid: $v.businessUnit.name.$invalid }"
              v-model="$v.businessUnit.name.$model"
              required
            />
            <div v-if="$v.businessUnit.name.$anyDirty && $v.businessUnit.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.businessUnit.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="business-unit-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="business-unit-detail"
              data-cy="detail"
              :class="{ valid: !$v.businessUnit.detail.$invalid, invalid: $v.businessUnit.detail.$invalid }"
              v-model="$v.businessUnit.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="business-unit-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="business-unit-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.businessUnit.creationDate.$invalid, invalid: $v.businessUnit.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.businessUnit.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="business-unit-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="business-unit-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.businessUnit.lastUpdated.$invalid, invalid: $v.businessUnit.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.businessUnit.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="business-unit-organization">Organization</label>
            <select
              class="form-control"
              id="business-unit-organization"
              data-cy="organization"
              name="organization"
              v-model="businessUnit.organization"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  businessUnit.organization && organizationOption.id === businessUnit.organization.id
                    ? businessUnit.organization
                    : organizationOption
                "
                v-for="organizationOption in organizations"
                :key="organizationOption.id"
              >
                {{ organizationOption.name }}
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
            :disabled="$v.businessUnit.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./business-unit-update.component.ts"></script>
