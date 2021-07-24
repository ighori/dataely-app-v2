<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.fileInfo.home.createOrEditLabel" data-cy="FileInfoCreateUpdateHeading">Create or edit a FileInfo</h2>
        <div>
          <div class="form-group" v-if="fileInfo.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fileInfo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="file-info-name"
              data-cy="name"
              :class="{ valid: !$v.fileInfo.name.$invalid, invalid: $v.fileInfo.name.$invalid }"
              v-model="$v.fileInfo.name.$model"
              required
            />
            <div v-if="$v.fileInfo.name.$anyDirty && $v.fileInfo.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileInfo.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="file-info-detail"
              data-cy="detail"
              :class="{ valid: !$v.fileInfo.detail.$invalid, invalid: $v.fileInfo.detail.$invalid }"
              v-model="$v.fileInfo.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-fileType">File Type</label>
            <select
              class="form-control"
              name="fileType"
              :class="{ valid: !$v.fileInfo.fileType.$invalid, invalid: $v.fileInfo.fileType.$invalid }"
              v-model="$v.fileInfo.fileType.$model"
              id="file-info-fileType"
              data-cy="fileType"
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
            <div v-if="$v.fileInfo.fileType.$anyDirty && $v.fileInfo.fileType.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileInfo.fileType.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-filePath">File Path</label>
            <input
              type="text"
              class="form-control"
              name="filePath"
              id="file-info-filePath"
              data-cy="filePath"
              :class="{ valid: !$v.fileInfo.filePath.$invalid, invalid: $v.fileInfo.filePath.$invalid }"
              v-model="$v.fileInfo.filePath.$model"
              required
            />
            <div v-if="$v.fileInfo.filePath.$anyDirty && $v.fileInfo.filePath.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileInfo.filePath.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-columnNameLineNumber">Column Name Line Number</label>
            <input
              type="number"
              class="form-control"
              name="columnNameLineNumber"
              id="file-info-columnNameLineNumber"
              data-cy="columnNameLineNumber"
              :class="{ valid: !$v.fileInfo.columnNameLineNumber.$invalid, invalid: $v.fileInfo.columnNameLineNumber.$invalid }"
              v-model.number="$v.fileInfo.columnNameLineNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-encoding">Encoding</label>
            <input
              type="text"
              class="form-control"
              name="encoding"
              id="file-info-encoding"
              data-cy="encoding"
              :class="{ valid: !$v.fileInfo.encoding.$invalid, invalid: $v.fileInfo.encoding.$invalid }"
              v-model="$v.fileInfo.encoding.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-separatorChar">Separator Char</label>
            <input
              type="text"
              class="form-control"
              name="separatorChar"
              id="file-info-separatorChar"
              data-cy="separatorChar"
              :class="{ valid: !$v.fileInfo.separatorChar.$invalid, invalid: $v.fileInfo.separatorChar.$invalid }"
              v-model="$v.fileInfo.separatorChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-quoteChar">Quote Char</label>
            <input
              type="text"
              class="form-control"
              name="quoteChar"
              id="file-info-quoteChar"
              data-cy="quoteChar"
              :class="{ valid: !$v.fileInfo.quoteChar.$invalid, invalid: $v.fileInfo.quoteChar.$invalid }"
              v-model="$v.fileInfo.quoteChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-escapeChar">Escape Char</label>
            <input
              type="text"
              class="form-control"
              name="escapeChar"
              id="file-info-escapeChar"
              data-cy="escapeChar"
              :class="{ valid: !$v.fileInfo.escapeChar.$invalid, invalid: $v.fileInfo.escapeChar.$invalid }"
              v-model="$v.fileInfo.escapeChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-fixedValueWidth">Fixed Value Width</label>
            <input
              type="number"
              class="form-control"
              name="fixedValueWidth"
              id="file-info-fixedValueWidth"
              data-cy="fixedValueWidth"
              :class="{ valid: !$v.fileInfo.fixedValueWidth.$invalid, invalid: $v.fileInfo.fixedValueWidth.$invalid }"
              v-model.number="$v.fileInfo.fixedValueWidth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-skipEmptyLines">Skip Empty Lines</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEmptyLines"
              id="file-info-skipEmptyLines"
              data-cy="skipEmptyLines"
              :class="{ valid: !$v.fileInfo.skipEmptyLines.$invalid, invalid: $v.fileInfo.skipEmptyLines.$invalid }"
              v-model="$v.fileInfo.skipEmptyLines.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-skipEmptyColumns">Skip Empty Columns</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEmptyColumns"
              id="file-info-skipEmptyColumns"
              data-cy="skipEmptyColumns"
              :class="{ valid: !$v.fileInfo.skipEmptyColumns.$invalid, invalid: $v.fileInfo.skipEmptyColumns.$invalid }"
              v-model="$v.fileInfo.skipEmptyColumns.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-failOnInconsistentLineWidth">Fail On Inconsistent Line Width</label>
            <input
              type="checkbox"
              class="form-check"
              name="failOnInconsistentLineWidth"
              id="file-info-failOnInconsistentLineWidth"
              data-cy="failOnInconsistentLineWidth"
              :class="{
                valid: !$v.fileInfo.failOnInconsistentLineWidth.$invalid,
                invalid: $v.fileInfo.failOnInconsistentLineWidth.$invalid,
              }"
              v-model="$v.fileInfo.failOnInconsistentLineWidth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-skipEbcdicHeader">Skip Ebcdic Header</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEbcdicHeader"
              id="file-info-skipEbcdicHeader"
              data-cy="skipEbcdicHeader"
              :class="{ valid: !$v.fileInfo.skipEbcdicHeader.$invalid, invalid: $v.fileInfo.skipEbcdicHeader.$invalid }"
              v-model="$v.fileInfo.skipEbcdicHeader.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-eolPresent">Eol Present</label>
            <input
              type="checkbox"
              class="form-check"
              name="eolPresent"
              id="file-info-eolPresent"
              data-cy="eolPresent"
              :class="{ valid: !$v.fileInfo.eolPresent.$invalid, invalid: $v.fileInfo.eolPresent.$invalid }"
              v-model="$v.fileInfo.eolPresent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="file-info-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.fileInfo.creationDate.$invalid, invalid: $v.fileInfo.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.fileInfo.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="file-info-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.fileInfo.lastUpdated.$invalid, invalid: $v.fileInfo.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.fileInfo.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-fileSource">File Source</label>
            <select class="form-control" id="file-info-fileSource" data-cy="fileSource" name="fileSource" v-model="fileInfo.fileSource">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  fileInfo.fileSource && fileSourceOption.id === fileInfo.fileSource.id ? fileInfo.fileSource : fileSourceOption
                "
                v-for="fileSourceOption in fileSources"
                :key="fileSourceOption.id"
              >
                {{ fileSourceOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-info-fileConfig">File Config</label>
            <select class="form-control" id="file-info-fileConfig" data-cy="fileConfig" name="fileConfig" v-model="fileInfo.fileConfig">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  fileInfo.fileConfig && fileConfigOption.id === fileInfo.fileConfig.id ? fileInfo.fileConfig : fileConfigOption
                "
                v-for="fileConfigOption in fileConfigs"
                :key="fileConfigOption.id"
              >
                {{ fileConfigOption.name }}
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
            :disabled="$v.fileInfo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-info-update.component.ts"></script>
