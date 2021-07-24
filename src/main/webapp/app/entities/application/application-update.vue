<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.application.home.createOrEditLabel" data-cy="ApplicationCreateUpdateHeading">
          Create or edit a Application
        </h2>
        <div>
          <div class="form-group" v-if="application.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="application.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="application-name"
              data-cy="name"
              :class="{ valid: !$v.application.name.$invalid, invalid: $v.application.name.$invalid }"
              v-model="$v.application.name.$model"
              required
            />
            <div v-if="$v.application.name.$anyDirty && $v.application.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.application.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="application-detail"
              data-cy="detail"
              :class="{ valid: !$v.application.detail.$invalid, invalid: $v.application.detail.$invalid }"
              v-model="$v.application.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-version">Version</label>
            <input
              type="text"
              class="form-control"
              name="version"
              id="application-version"
              data-cy="version"
              :class="{ valid: !$v.application.version.$invalid, invalid: $v.application.version.$invalid }"
              v-model="$v.application.version.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-appType">App Type</label>
            <select
              class="form-control"
              name="appType"
              :class="{ valid: !$v.application.appType.$invalid, invalid: $v.application.appType.$invalid }"
              v-model="$v.application.appType.$model"
              id="application-appType"
              data-cy="appType"
              required
            >
              <option value="CRM">CRM</option>
              <option value="HR">HR</option>
              <option value="FINANCE">FINANCE</option>
              <option value="COMMUNICATION">COMMUNICATION</option>
              <option value="MESSAGING">MESSAGING</option>
              <option value="MARKETING">MARKETING</option>
              <option value="WEB">WEB</option>
              <option value="MOBILE">MOBILE</option>
              <option value="SERVICE">SERVICE</option>
              <option value="DMS">DMS</option>
              <option value="HELPDESK">HELPDESK</option>
              <option value="ACCOUNTING">ACCOUNTING</option>
              <option value="ERP">ERP</option>
              <option value="ANALYTICS">ANALYTICS</option>
              <option value="BI">BI</option>
              <option value="OTHER">OTHER</option>
            </select>
            <div v-if="$v.application.appType.$anyDirty && $v.application.appType.$invalid">
              <small class="form-text text-danger" v-if="!$v.application.appType.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="application-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.application.creationDate.$invalid, invalid: $v.application.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.application.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="application-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.application.lastUpdated.$invalid, invalid: $v.application.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.application.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="application-businessUnit">Business Unit</label>
            <select
              class="form-control"
              id="application-businessUnit"
              data-cy="businessUnit"
              name="businessUnit"
              v-model="application.businessUnit"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  application.businessUnit && businessUnitOption.id === application.businessUnit.id
                    ? application.businessUnit
                    : businessUnitOption
                "
                v-for="businessUnitOption in businessUnits"
                :key="businessUnitOption.id"
              >
                {{ businessUnitOption.name }}
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
            :disabled="$v.application.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./application-update.component.ts"></script>
