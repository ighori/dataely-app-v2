<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.fileSource.home.createOrEditLabel" data-cy="FileSourceCreateUpdateHeading">Create or edit a FileSource</h2>
        <div>
          <div class="form-group" v-if="fileSource.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fileSource.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="file-source-name"
              data-cy="name"
              :class="{ valid: !$v.fileSource.name.$invalid, invalid: $v.fileSource.name.$invalid }"
              v-model="$v.fileSource.name.$model"
              required
            />
            <div v-if="$v.fileSource.name.$anyDirty && $v.fileSource.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileSource.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="file-source-detail"
              data-cy="detail"
              :class="{ valid: !$v.fileSource.detail.$invalid, invalid: $v.fileSource.detail.$invalid }"
              v-model="$v.fileSource.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-hostname">Hostname</label>
            <input
              type="text"
              class="form-control"
              name="hostname"
              id="file-source-hostname"
              data-cy="hostname"
              :class="{ valid: !$v.fileSource.hostname.$invalid, invalid: $v.fileSource.hostname.$invalid }"
              v-model="$v.fileSource.hostname.$model"
              required
            />
            <div v-if="$v.fileSource.hostname.$anyDirty && $v.fileSource.hostname.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileSource.hostname.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-port">Port</label>
            <input
              type="number"
              class="form-control"
              name="port"
              id="file-source-port"
              data-cy="port"
              :class="{ valid: !$v.fileSource.port.$invalid, invalid: $v.fileSource.port.$invalid }"
              v-model.number="$v.fileSource.port.$model"
              required
            />
            <div v-if="$v.fileSource.port.$anyDirty && $v.fileSource.port.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileSource.port.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.fileSource.port.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-username">Username</label>
            <input
              type="text"
              class="form-control"
              name="username"
              id="file-source-username"
              data-cy="username"
              :class="{ valid: !$v.fileSource.username.$invalid, invalid: $v.fileSource.username.$invalid }"
              v-model="$v.fileSource.username.$model"
              required
            />
            <div v-if="$v.fileSource.username.$anyDirty && $v.fileSource.username.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileSource.username.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-password">Password</label>
            <input
              type="text"
              class="form-control"
              name="password"
              id="file-source-password"
              data-cy="password"
              :class="{ valid: !$v.fileSource.password.$invalid, invalid: $v.fileSource.password.$invalid }"
              v-model="$v.fileSource.password.$model"
              required
            />
            <div v-if="$v.fileSource.password.$anyDirty && $v.fileSource.password.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileSource.password.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-icon">Icon</label>
            <input
              type="text"
              class="form-control"
              name="icon"
              id="file-source-icon"
              data-cy="icon"
              :class="{ valid: !$v.fileSource.icon.$invalid, invalid: $v.fileSource.icon.$invalid }"
              v-model="$v.fileSource.icon.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-connectionType">Connection Type</label>
            <input
              type="text"
              class="form-control"
              name="connectionType"
              id="file-source-connectionType"
              data-cy="connectionType"
              :class="{ valid: !$v.fileSource.connectionType.$invalid, invalid: $v.fileSource.connectionType.$invalid }"
              v-model="$v.fileSource.connectionType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.fileSource.type.$invalid, invalid: $v.fileSource.type.$invalid }"
              v-model="$v.fileSource.type.$model"
              id="file-source-type"
              data-cy="type"
            >
              <option value="JDBC">JDBC</option>
              <option value="JSON">JSON</option>
              <option value="XML">XML</option>
              <option value="CSV">CSV</option>
              <option value="PARQUET">PARQUET</option>
              <option value="FIXED">FIXED</option>
              <option value="EBCDIC">EBCDIC</option>
              <option value="NOSQL">NOSQL</option>
              <option value="ARVO">ARVO</option>
              <option value="QMS">QMS</option>
              <option value="XLS">XLS</option>
              <option value="XLSX">XLSX</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-region">Region</label>
            <input
              type="text"
              class="form-control"
              name="region"
              id="file-source-region"
              data-cy="region"
              :class="{ valid: !$v.fileSource.region.$invalid, invalid: $v.fileSource.region.$invalid }"
              v-model="$v.fileSource.region.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-ignoreDottedFiles">Ignore Dotted Files</label>
            <input
              type="checkbox"
              class="form-check"
              name="ignoreDottedFiles"
              id="file-source-ignoreDottedFiles"
              data-cy="ignoreDottedFiles"
              :class="{ valid: !$v.fileSource.ignoreDottedFiles.$invalid, invalid: $v.fileSource.ignoreDottedFiles.$invalid }"
              v-model="$v.fileSource.ignoreDottedFiles.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-recurse">Recurse</label>
            <input
              type="checkbox"
              class="form-check"
              name="recurse"
              id="file-source-recurse"
              data-cy="recurse"
              :class="{ valid: !$v.fileSource.recurse.$invalid, invalid: $v.fileSource.recurse.$invalid }"
              v-model="$v.fileSource.recurse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-pathFilterRegex">Path Filter Regex</label>
            <input
              type="text"
              class="form-control"
              name="pathFilterRegex"
              id="file-source-pathFilterRegex"
              data-cy="pathFilterRegex"
              :class="{ valid: !$v.fileSource.pathFilterRegex.$invalid, invalid: $v.fileSource.pathFilterRegex.$invalid }"
              v-model="$v.fileSource.pathFilterRegex.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-remotePath">Remote Path</label>
            <input
              type="text"
              class="form-control"
              name="remotePath"
              id="file-source-remotePath"
              data-cy="remotePath"
              :class="{ valid: !$v.fileSource.remotePath.$invalid, invalid: $v.fileSource.remotePath.$invalid }"
              v-model="$v.fileSource.remotePath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="file-source-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.fileSource.creationDate.$invalid, invalid: $v.fileSource.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.fileSource.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="file-source-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.fileSource.lastUpdated.$invalid, invalid: $v.fileSource.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.fileSource.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-source-environment">Environment</label>
            <select
              class="form-control"
              id="file-source-environment"
              data-cy="environment"
              name="environment"
              v-model="fileSource.environment"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  fileSource.environment && environmentOption.id === fileSource.environment.id ? fileSource.environment : environmentOption
                "
                v-for="environmentOption in environments"
                :key="environmentOption.id"
              >
                {{ environmentOption.name }}
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
            :disabled="$v.fileSource.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-source-update.component.ts"></script>
