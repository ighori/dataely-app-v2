<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.analyzerResult.home.createOrEditLabel" data-cy="AnalyzerResultCreateUpdateHeading">
          Create or edit a AnalyzerResult
        </h2>
        <div>
          <div class="form-group" v-if="analyzerResult.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="analyzerResult.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="analyzer-result-name"
              data-cy="name"
              :class="{ valid: !$v.analyzerResult.name.$invalid, invalid: $v.analyzerResult.name.$invalid }"
              v-model="$v.analyzerResult.name.$model"
              required
            />
            <div v-if="$v.analyzerResult.name.$anyDirty && $v.analyzerResult.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.analyzerResult.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="analyzer-result-detail"
              data-cy="detail"
              :class="{ valid: !$v.analyzerResult.detail.$invalid, invalid: $v.analyzerResult.detail.$invalid }"
              v-model="$v.analyzerResult.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-objectId">Object Id</label>
            <input
              type="number"
              class="form-control"
              name="objectId"
              id="analyzer-result-objectId"
              data-cy="objectId"
              :class="{ valid: !$v.analyzerResult.objectId.$invalid, invalid: $v.analyzerResult.objectId.$invalid }"
              v-model.number="$v.analyzerResult.objectId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-objectType">Object Type</label>
            <select
              class="form-control"
              name="objectType"
              :class="{ valid: !$v.analyzerResult.objectType.$invalid, invalid: $v.analyzerResult.objectType.$invalid }"
              v-model="$v.analyzerResult.objectType.$model"
              id="analyzer-result-objectType"
              data-cy="objectType"
            >
              <option value="FILE">FILE</option>
              <option value="TABLE">TABLE</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-fieldId">Field Id</label>
            <input
              type="number"
              class="form-control"
              name="fieldId"
              id="analyzer-result-fieldId"
              data-cy="fieldId"
              :class="{ valid: !$v.analyzerResult.fieldId.$invalid, invalid: $v.analyzerResult.fieldId.$invalid }"
              v-model.number="$v.analyzerResult.fieldId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-fieldType">Field Type</label>
            <input
              type="text"
              class="form-control"
              name="fieldType"
              id="analyzer-result-fieldType"
              data-cy="fieldType"
              :class="{ valid: !$v.analyzerResult.fieldType.$invalid, invalid: $v.analyzerResult.fieldType.$invalid }"
              v-model="$v.analyzerResult.fieldType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-entityType">Entity Type</label>
            <input
              type="text"
              class="form-control"
              name="entityType"
              id="analyzer-result-entityType"
              data-cy="entityType"
              :class="{ valid: !$v.analyzerResult.entityType.$invalid, invalid: $v.analyzerResult.entityType.$invalid }"
              v-model="$v.analyzerResult.entityType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-start">Start</label>
            <input
              type="number"
              class="form-control"
              name="start"
              id="analyzer-result-start"
              data-cy="start"
              :class="{ valid: !$v.analyzerResult.start.$invalid, invalid: $v.analyzerResult.start.$invalid }"
              v-model.number="$v.analyzerResult.start.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-end">End</label>
            <input
              type="number"
              class="form-control"
              name="end"
              id="analyzer-result-end"
              data-cy="end"
              :class="{ valid: !$v.analyzerResult.end.$invalid, invalid: $v.analyzerResult.end.$invalid }"
              v-model.number="$v.analyzerResult.end.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-score">Score</label>
            <input
              type="number"
              class="form-control"
              name="score"
              id="analyzer-result-score"
              data-cy="score"
              :class="{ valid: !$v.analyzerResult.score.$invalid, invalid: $v.analyzerResult.score.$invalid }"
              v-model.number="$v.analyzerResult.score.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-recognizer">Recognizer</label>
            <input
              type="text"
              class="form-control"
              name="recognizer"
              id="analyzer-result-recognizer"
              data-cy="recognizer"
              :class="{ valid: !$v.analyzerResult.recognizer.$invalid, invalid: $v.analyzerResult.recognizer.$invalid }"
              v-model="$v.analyzerResult.recognizer.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-patternName">Pattern Name</label>
            <input
              type="text"
              class="form-control"
              name="patternName"
              id="analyzer-result-patternName"
              data-cy="patternName"
              :class="{ valid: !$v.analyzerResult.patternName.$invalid, invalid: $v.analyzerResult.patternName.$invalid }"
              v-model="$v.analyzerResult.patternName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-patternExpr">Pattern Expr</label>
            <input
              type="text"
              class="form-control"
              name="patternExpr"
              id="analyzer-result-patternExpr"
              data-cy="patternExpr"
              :class="{ valid: !$v.analyzerResult.patternExpr.$invalid, invalid: $v.analyzerResult.patternExpr.$invalid }"
              v-model="$v.analyzerResult.patternExpr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-originalScore">Original Score</label>
            <input
              type="text"
              class="form-control"
              name="originalScore"
              id="analyzer-result-originalScore"
              data-cy="originalScore"
              :class="{ valid: !$v.analyzerResult.originalScore.$invalid, invalid: $v.analyzerResult.originalScore.$invalid }"
              v-model="$v.analyzerResult.originalScore.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-textualExplanation">Textual Explanation</label>
            <input
              type="text"
              class="form-control"
              name="textualExplanation"
              id="analyzer-result-textualExplanation"
              data-cy="textualExplanation"
              :class="{ valid: !$v.analyzerResult.textualExplanation.$invalid, invalid: $v.analyzerResult.textualExplanation.$invalid }"
              v-model="$v.analyzerResult.textualExplanation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-supportiveContextWord">Supportive Context Word</label>
            <input
              type="text"
              class="form-control"
              name="supportiveContextWord"
              id="analyzer-result-supportiveContextWord"
              data-cy="supportiveContextWord"
              :class="{
                valid: !$v.analyzerResult.supportiveContextWord.$invalid,
                invalid: $v.analyzerResult.supportiveContextWord.$invalid,
              }"
              v-model="$v.analyzerResult.supportiveContextWord.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-validationResult">Validation Result</label>
            <input
              type="text"
              class="form-control"
              name="validationResult"
              id="analyzer-result-validationResult"
              data-cy="validationResult"
              :class="{ valid: !$v.analyzerResult.validationResult.$invalid, invalid: $v.analyzerResult.validationResult.$invalid }"
              v-model="$v.analyzerResult.validationResult.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="analyzer-result-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.analyzerResult.creationDate.$invalid, invalid: $v.analyzerResult.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerResult.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="analyzer-result-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.analyzerResult.lastUpdated.$invalid, invalid: $v.analyzerResult.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerResult.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-dataSource">Data Source</label>
            <select
              class="form-control"
              id="analyzer-result-dataSource"
              data-cy="dataSource"
              name="dataSource"
              v-model="analyzerResult.dataSource"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  analyzerResult.dataSource && dataSourceOption.id === analyzerResult.dataSource.id
                    ? analyzerResult.dataSource
                    : dataSourceOption
                "
                v-for="dataSourceOption in dataSources"
                :key="dataSourceOption.id"
              >
                {{ dataSourceOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-result-fileSource">File Source</label>
            <select
              class="form-control"
              id="analyzer-result-fileSource"
              data-cy="fileSource"
              name="fileSource"
              v-model="analyzerResult.fileSource"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  analyzerResult.fileSource && fileSourceOption.id === analyzerResult.fileSource.id
                    ? analyzerResult.fileSource
                    : fileSourceOption
                "
                v-for="fileSourceOption in fileSources"
                :key="fileSourceOption.id"
              >
                {{ fileSourceOption.name }}
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
            :disabled="$v.analyzerResult.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./analyzer-result-update.component.ts"></script>
