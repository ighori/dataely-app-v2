<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.analyzerJob.home.createOrEditLabel" data-cy="AnalyzerJobCreateUpdateHeading">
          Create or edit a AnalyzerJob
        </h2>
        <div>
          <div class="form-group" v-if="analyzerJob.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="analyzerJob.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="analyzer-job-name"
              data-cy="name"
              :class="{ valid: !$v.analyzerJob.name.$invalid, invalid: $v.analyzerJob.name.$invalid }"
              v-model="$v.analyzerJob.name.$model"
              required
            />
            <div v-if="$v.analyzerJob.name.$anyDirty && $v.analyzerJob.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.analyzerJob.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="analyzer-job-detail"
              data-cy="detail"
              :class="{ valid: !$v.analyzerJob.detail.$invalid, invalid: $v.analyzerJob.detail.$invalid }"
              v-model="$v.analyzerJob.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-endTime">End Time</label>
            <div class="d-flex">
              <input
                id="analyzer-job-endTime"
                data-cy="endTime"
                type="datetime-local"
                class="form-control"
                name="endTime"
                :class="{ valid: !$v.analyzerJob.endTime.$invalid, invalid: $v.analyzerJob.endTime.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerJob.endTime.$model)"
                @change="updateInstantField('endTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-startTime">Start Time</label>
            <div class="d-flex">
              <input
                id="analyzer-job-startTime"
                data-cy="startTime"
                type="datetime-local"
                class="form-control"
                name="startTime"
                :class="{ valid: !$v.analyzerJob.startTime.$invalid, invalid: $v.analyzerJob.startTime.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerJob.startTime.$model)"
                @change="updateInstantField('startTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.analyzerJob.status.$invalid, invalid: $v.analyzerJob.status.$invalid }"
              v-model="$v.analyzerJob.status.$model"
              id="analyzer-job-status"
              data-cy="status"
            >
              <option value="SUCCEEDED">Job Succeeded</option>
              <option value="FAILED">Job Failed</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-previousRunTime">Previous Run Time</label>
            <input
              type="text"
              class="form-control"
              name="previousRunTime"
              id="analyzer-job-previousRunTime"
              data-cy="previousRunTime"
              :class="{ valid: !$v.analyzerJob.previousRunTime.$invalid, invalid: $v.analyzerJob.previousRunTime.$invalid }"
              v-model="$v.analyzerJob.previousRunTime.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="analyzer-job-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.analyzerJob.creationDate.$invalid, invalid: $v.analyzerJob.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerJob.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="analyzer-job-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.analyzerJob.lastUpdated.$invalid, invalid: $v.analyzerJob.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerJob.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-environment">Environment</label>
            <select
              class="form-control"
              id="analyzer-job-environment"
              data-cy="environment"
              name="environment"
              v-model="analyzerJob.environment"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  analyzerJob.environment && environmentOption.id === analyzerJob.environment.id
                    ? analyzerJob.environment
                    : environmentOption
                "
                v-for="environmentOption in environments"
                :key="environmentOption.id"
              >
                {{ environmentOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-dataSource">Data Source</label>
            <select
              class="form-control"
              id="analyzer-job-dataSource"
              data-cy="dataSource"
              name="dataSource"
              v-model="analyzerJob.dataSource"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  analyzerJob.dataSource && dataSourceOption.id === analyzerJob.dataSource.id ? analyzerJob.dataSource : dataSourceOption
                "
                v-for="dataSourceOption in dataSources"
                :key="dataSourceOption.id"
              >
                {{ dataSourceOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-fileSource">File Source</label>
            <select
              class="form-control"
              id="analyzer-job-fileSource"
              data-cy="fileSource"
              name="fileSource"
              v-model="analyzerJob.fileSource"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  analyzerJob.fileSource && fileSourceOption.id === analyzerJob.fileSource.id ? analyzerJob.fileSource : fileSourceOption
                "
                v-for="fileSourceOption in fileSources"
                :key="fileSourceOption.id"
              >
                {{ fileSourceOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-job-user">User</label>
            <select class="form-control" id="analyzer-job-user" data-cy="user" name="user" v-model="analyzerJob.user" required>
              <option v-if="!analyzerJob.user" v-bind:value="null" selected></option>
              <option
                v-bind:value="analyzerJob.user && userOption.id === analyzerJob.user.id ? analyzerJob.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div v-if="$v.analyzerJob.user.$anyDirty && $v.analyzerJob.user.$invalid">
            <small class="form-text text-danger" v-if="!$v.analyzerJob.user.required"> This field is required. </small>
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
            :disabled="$v.analyzerJob.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./analyzer-job-update.component.ts"></script>
