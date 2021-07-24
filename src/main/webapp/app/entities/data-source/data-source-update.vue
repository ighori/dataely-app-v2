<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.dataSource.home.createOrEditLabel" data-cy="DataSourceCreateUpdateHeading">Create or edit a DataSource</h2>
        <div>
          <div class="form-group" v-if="dataSource.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dataSource.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="data-source-name"
              data-cy="name"
              :class="{ valid: !$v.dataSource.name.$invalid, invalid: $v.dataSource.name.$invalid }"
              v-model="$v.dataSource.name.$model"
              required
            />
            <div v-if="$v.dataSource.name.$anyDirty && $v.dataSource.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="data-source-detail"
              data-cy="detail"
              :class="{ valid: !$v.dataSource.detail.$invalid, invalid: $v.dataSource.detail.$invalid }"
              v-model="$v.dataSource.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-databaseName">Database Name</label>
            <input
              type="text"
              class="form-control"
              name="databaseName"
              id="data-source-databaseName"
              data-cy="databaseName"
              :class="{ valid: !$v.dataSource.databaseName.$invalid, invalid: $v.dataSource.databaseName.$invalid }"
              v-model="$v.dataSource.databaseName.$model"
              required
            />
            <div v-if="$v.dataSource.databaseName.$anyDirty && $v.dataSource.databaseName.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.databaseName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-instanceName">Instance Name</label>
            <input
              type="text"
              class="form-control"
              name="instanceName"
              id="data-source-instanceName"
              data-cy="instanceName"
              :class="{ valid: !$v.dataSource.instanceName.$invalid, invalid: $v.dataSource.instanceName.$invalid }"
              v-model="$v.dataSource.instanceName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-schemaName">Schema Name</label>
            <input
              type="text"
              class="form-control"
              name="schemaName"
              id="data-source-schemaName"
              data-cy="schemaName"
              :class="{ valid: !$v.dataSource.schemaName.$invalid, invalid: $v.dataSource.schemaName.$invalid }"
              v-model="$v.dataSource.schemaName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-hostname">Hostname</label>
            <input
              type="text"
              class="form-control"
              name="hostname"
              id="data-source-hostname"
              data-cy="hostname"
              :class="{ valid: !$v.dataSource.hostname.$invalid, invalid: $v.dataSource.hostname.$invalid }"
              v-model="$v.dataSource.hostname.$model"
              required
            />
            <div v-if="$v.dataSource.hostname.$anyDirty && $v.dataSource.hostname.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.hostname.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-port">Port</label>
            <input
              type="number"
              class="form-control"
              name="port"
              id="data-source-port"
              data-cy="port"
              :class="{ valid: !$v.dataSource.port.$invalid, invalid: $v.dataSource.port.$invalid }"
              v-model.number="$v.dataSource.port.$model"
              required
            />
            <div v-if="$v.dataSource.port.$anyDirty && $v.dataSource.port.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.port.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.dataSource.port.numeric"> This field should be a number. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-username">Username</label>
            <input
              type="text"
              class="form-control"
              name="username"
              id="data-source-username"
              data-cy="username"
              :class="{ valid: !$v.dataSource.username.$invalid, invalid: $v.dataSource.username.$invalid }"
              v-model="$v.dataSource.username.$model"
              required
            />
            <div v-if="$v.dataSource.username.$anyDirty && $v.dataSource.username.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.username.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-password">Password</label>
            <input
              type="text"
              class="form-control"
              name="password"
              id="data-source-password"
              data-cy="password"
              :class="{ valid: !$v.dataSource.password.$invalid, invalid: $v.dataSource.password.$invalid }"
              v-model="$v.dataSource.password.$model"
              required
            />
            <div v-if="$v.dataSource.password.$anyDirty && $v.dataSource.password.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.password.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-icon">Icon</label>
            <input
              type="text"
              class="form-control"
              name="icon"
              id="data-source-icon"
              data-cy="icon"
              :class="{ valid: !$v.dataSource.icon.$invalid, invalid: $v.dataSource.icon.$invalid }"
              v-model="$v.dataSource.icon.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-dbType">Db Type</label>
            <select
              class="form-control"
              name="dbType"
              :class="{ valid: !$v.dataSource.dbType.$invalid, invalid: $v.dataSource.dbType.$invalid }"
              v-model="$v.dataSource.dbType.$model"
              id="data-source-dbType"
              data-cy="dbType"
              required
            >
              <option value="ORACLE">ORACLE</option>
              <option value="MYSQL">MYSQL</option>
              <option value="SQLSERVER">SQLSERVER</option>
              <option value="POSTGRESQL">POSTGRESQL</option>
              <option value="DERBY">DERBY</option>
              <option value="FIREBIRDDB">FIREBIRDDB</option>
              <option value="DB2">DB2</option>
              <option value="SYBASE">SYBASE</option>
              <option value="TERADATA">TERADATA</option>
              <option value="HIVE2">HIVE2</option>
            </select>
            <div v-if="$v.dataSource.dbType.$anyDirty && $v.dataSource.dbType.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.dbType.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-dbVersion">Db Version</label>
            <input
              type="text"
              class="form-control"
              name="dbVersion"
              id="data-source-dbVersion"
              data-cy="dbVersion"
              :class="{ valid: !$v.dataSource.dbVersion.$invalid, invalid: $v.dataSource.dbVersion.$invalid }"
              v-model="$v.dataSource.dbVersion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-schemaCount">Schema Count</label>
            <input
              type="number"
              class="form-control"
              name="schemaCount"
              id="data-source-schemaCount"
              data-cy="schemaCount"
              :class="{ valid: !$v.dataSource.schemaCount.$invalid, invalid: $v.dataSource.schemaCount.$invalid }"
              v-model.number="$v.dataSource.schemaCount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-dsType">Ds Type</label>
            <select
              class="form-control"
              name="dsType"
              :class="{ valid: !$v.dataSource.dsType.$invalid, invalid: $v.dataSource.dsType.$invalid }"
              v-model="$v.dataSource.dsType.$model"
              id="data-source-dsType"
              data-cy="dsType"
              required
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
            <div v-if="$v.dataSource.dsType.$anyDirty && $v.dataSource.dsType.$invalid">
              <small class="form-text text-danger" v-if="!$v.dataSource.dsType.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-driverClassName">Driver Class Name</label>
            <input
              type="text"
              class="form-control"
              name="driverClassName"
              id="data-source-driverClassName"
              data-cy="driverClassName"
              :class="{ valid: !$v.dataSource.driverClassName.$invalid, invalid: $v.dataSource.driverClassName.$invalid }"
              v-model="$v.dataSource.driverClassName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-jdbcUrl">Jdbc Url</label>
            <input
              type="text"
              class="form-control"
              name="jdbcUrl"
              id="data-source-jdbcUrl"
              data-cy="jdbcUrl"
              :class="{ valid: !$v.dataSource.jdbcUrl.$invalid, invalid: $v.dataSource.jdbcUrl.$invalid }"
              v-model="$v.dataSource.jdbcUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-sid">Sid</label>
            <input
              type="text"
              class="form-control"
              name="sid"
              id="data-source-sid"
              data-cy="sid"
              :class="{ valid: !$v.dataSource.sid.$invalid, invalid: $v.dataSource.sid.$invalid }"
              v-model="$v.dataSource.sid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="data-source-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.dataSource.creationDate.$invalid, invalid: $v.dataSource.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.dataSource.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="data-source-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.dataSource.lastUpdated.$invalid, invalid: $v.dataSource.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.dataSource.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="data-source-environment">Environment</label>
            <select
              class="form-control"
              id="data-source-environment"
              data-cy="environment"
              name="environment"
              v-model="dataSource.environment"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  dataSource.environment && environmentOption.id === dataSource.environment.id ? dataSource.environment : environmentOption
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
            :disabled="$v.dataSource.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./data-source-update.component.ts"></script>
