<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.fileConfig.home.createOrEditLabel" data-cy="FileConfigCreateUpdateHeading">Create or edit a FileConfig</h2>
        <div>
          <div class="form-group" v-if="fileConfig.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="fileConfig.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="file-config-name"
              data-cy="name"
              :class="{ valid: !$v.fileConfig.name.$invalid, invalid: $v.fileConfig.name.$invalid }"
              v-model="$v.fileConfig.name.$model"
              required
            />
            <div v-if="$v.fileConfig.name.$anyDirty && $v.fileConfig.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.fileConfig.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="file-config-detail"
              data-cy="detail"
              :class="{ valid: !$v.fileConfig.detail.$invalid, invalid: $v.fileConfig.detail.$invalid }"
              v-model="$v.fileConfig.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-columnNameLineNumber">Column Name Line Number</label>
            <input
              type="number"
              class="form-control"
              name="columnNameLineNumber"
              id="file-config-columnNameLineNumber"
              data-cy="columnNameLineNumber"
              :class="{ valid: !$v.fileConfig.columnNameLineNumber.$invalid, invalid: $v.fileConfig.columnNameLineNumber.$invalid }"
              v-model.number="$v.fileConfig.columnNameLineNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-encoding">Encoding</label>
            <input
              type="text"
              class="form-control"
              name="encoding"
              id="file-config-encoding"
              data-cy="encoding"
              :class="{ valid: !$v.fileConfig.encoding.$invalid, invalid: $v.fileConfig.encoding.$invalid }"
              v-model="$v.fileConfig.encoding.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-separatorChar">Separator Char</label>
            <input
              type="text"
              class="form-control"
              name="separatorChar"
              id="file-config-separatorChar"
              data-cy="separatorChar"
              :class="{ valid: !$v.fileConfig.separatorChar.$invalid, invalid: $v.fileConfig.separatorChar.$invalid }"
              v-model="$v.fileConfig.separatorChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-quoteChar">Quote Char</label>
            <input
              type="text"
              class="form-control"
              name="quoteChar"
              id="file-config-quoteChar"
              data-cy="quoteChar"
              :class="{ valid: !$v.fileConfig.quoteChar.$invalid, invalid: $v.fileConfig.quoteChar.$invalid }"
              v-model="$v.fileConfig.quoteChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-escapeChar">Escape Char</label>
            <input
              type="text"
              class="form-control"
              name="escapeChar"
              id="file-config-escapeChar"
              data-cy="escapeChar"
              :class="{ valid: !$v.fileConfig.escapeChar.$invalid, invalid: $v.fileConfig.escapeChar.$invalid }"
              v-model="$v.fileConfig.escapeChar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-fixedValueWidth">Fixed Value Width</label>
            <input
              type="number"
              class="form-control"
              name="fixedValueWidth"
              id="file-config-fixedValueWidth"
              data-cy="fixedValueWidth"
              :class="{ valid: !$v.fileConfig.fixedValueWidth.$invalid, invalid: $v.fileConfig.fixedValueWidth.$invalid }"
              v-model.number="$v.fileConfig.fixedValueWidth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-skipEmptyLines">Skip Empty Lines</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEmptyLines"
              id="file-config-skipEmptyLines"
              data-cy="skipEmptyLines"
              :class="{ valid: !$v.fileConfig.skipEmptyLines.$invalid, invalid: $v.fileConfig.skipEmptyLines.$invalid }"
              v-model="$v.fileConfig.skipEmptyLines.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-skipEmptyColumns">Skip Empty Columns</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEmptyColumns"
              id="file-config-skipEmptyColumns"
              data-cy="skipEmptyColumns"
              :class="{ valid: !$v.fileConfig.skipEmptyColumns.$invalid, invalid: $v.fileConfig.skipEmptyColumns.$invalid }"
              v-model="$v.fileConfig.skipEmptyColumns.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-failOnInconsistentLineWidth">Fail On Inconsistent Line Width</label>
            <input
              type="checkbox"
              class="form-check"
              name="failOnInconsistentLineWidth"
              id="file-config-failOnInconsistentLineWidth"
              data-cy="failOnInconsistentLineWidth"
              :class="{
                valid: !$v.fileConfig.failOnInconsistentLineWidth.$invalid,
                invalid: $v.fileConfig.failOnInconsistentLineWidth.$invalid,
              }"
              v-model="$v.fileConfig.failOnInconsistentLineWidth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-skipEbcdicHeader">Skip Ebcdic Header</label>
            <input
              type="checkbox"
              class="form-check"
              name="skipEbcdicHeader"
              id="file-config-skipEbcdicHeader"
              data-cy="skipEbcdicHeader"
              :class="{ valid: !$v.fileConfig.skipEbcdicHeader.$invalid, invalid: $v.fileConfig.skipEbcdicHeader.$invalid }"
              v-model="$v.fileConfig.skipEbcdicHeader.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-eolPresent">Eol Present</label>
            <input
              type="checkbox"
              class="form-check"
              name="eolPresent"
              id="file-config-eolPresent"
              data-cy="eolPresent"
              :class="{ valid: !$v.fileConfig.eolPresent.$invalid, invalid: $v.fileConfig.eolPresent.$invalid }"
              v-model="$v.fileConfig.eolPresent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-fileType">File Type</label>
            <input
              type="text"
              class="form-control"
              name="fileType"
              id="file-config-fileType"
              data-cy="fileType"
              :class="{ valid: !$v.fileConfig.fileType.$invalid, invalid: $v.fileConfig.fileType.$invalid }"
              v-model="$v.fileConfig.fileType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="file-config-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.fileConfig.creationDate.$invalid, invalid: $v.fileConfig.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.fileConfig.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="file-config-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="file-config-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.fileConfig.lastUpdated.$invalid, invalid: $v.fileConfig.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.fileConfig.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
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
            :disabled="$v.fileConfig.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./file-config-update.component.ts"></script>
