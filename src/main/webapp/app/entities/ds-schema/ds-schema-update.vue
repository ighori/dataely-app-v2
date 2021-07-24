<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.dsSchema.home.createOrEditLabel" data-cy="DsSchemaCreateUpdateHeading">Create or edit a DsSchema</h2>
        <div>
          <div class="form-group" v-if="dsSchema.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dsSchema.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="ds-schema-name"
              data-cy="name"
              :class="{ valid: !$v.dsSchema.name.$invalid, invalid: $v.dsSchema.name.$invalid }"
              v-model="$v.dsSchema.name.$model"
              required
            />
            <div v-if="$v.dsSchema.name.$anyDirty && $v.dsSchema.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.dsSchema.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="ds-schema-detail"
              data-cy="detail"
              :class="{ valid: !$v.dsSchema.detail.$invalid, invalid: $v.dsSchema.detail.$invalid }"
              v-model="$v.dsSchema.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-tableCount">Table Count</label>
            <input
              type="number"
              class="form-control"
              name="tableCount"
              id="ds-schema-tableCount"
              data-cy="tableCount"
              :class="{ valid: !$v.dsSchema.tableCount.$invalid, invalid: $v.dsSchema.tableCount.$invalid }"
              v-model.number="$v.dsSchema.tableCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-tableRelCount">Table Rel Count</label>
            <input
              type="number"
              class="form-control"
              name="tableRelCount"
              id="ds-schema-tableRelCount"
              data-cy="tableRelCount"
              :class="{ valid: !$v.dsSchema.tableRelCount.$invalid, invalid: $v.dsSchema.tableRelCount.$invalid }"
              v-model.number="$v.dsSchema.tableRelCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="ds-schema-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.dsSchema.creationDate.$invalid, invalid: $v.dsSchema.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.dsSchema.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="ds-schema-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.dsSchema.lastUpdated.$invalid, invalid: $v.dsSchema.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.dsSchema.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="ds-schema-dataSource">Data Source</label>
            <select class="form-control" id="ds-schema-dataSource" data-cy="dataSource" name="dataSource" v-model="dsSchema.dataSource">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  dsSchema.dataSource && dataSourceOption.id === dsSchema.dataSource.id ? dsSchema.dataSource : dataSourceOption
                "
                v-for="dataSourceOption in dataSources"
                :key="dataSourceOption.id"
              >
                {{ dataSourceOption.name }}
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
            :disabled="$v.dsSchema.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ds-schema-update.component.ts"></script>
