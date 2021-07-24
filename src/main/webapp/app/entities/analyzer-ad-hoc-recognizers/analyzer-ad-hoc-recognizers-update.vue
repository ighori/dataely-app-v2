<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.analyzerAdHocRecognizers.home.createOrEditLabel" data-cy="AnalyzerAdHocRecognizersCreateUpdateHeading">
          Create or edit a AnalyzerAdHocRecognizers
        </h2>
        <div>
          <div class="form-group" v-if="analyzerAdHocRecognizers.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="analyzerAdHocRecognizers.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="analyzer-ad-hoc-recognizers-name"
              data-cy="name"
              :class="{ valid: !$v.analyzerAdHocRecognizers.name.$invalid, invalid: $v.analyzerAdHocRecognizers.name.$invalid }"
              v-model="$v.analyzerAdHocRecognizers.name.$model"
              required
            />
            <div v-if="$v.analyzerAdHocRecognizers.name.$anyDirty && $v.analyzerAdHocRecognizers.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.analyzerAdHocRecognizers.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="analyzer-ad-hoc-recognizers-detail"
              data-cy="detail"
              :class="{ valid: !$v.analyzerAdHocRecognizers.detail.$invalid, invalid: $v.analyzerAdHocRecognizers.detail.$invalid }"
              v-model="$v.analyzerAdHocRecognizers.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-recognizerName">Recognizer Name</label>
            <input
              type="text"
              class="form-control"
              name="recognizerName"
              id="analyzer-ad-hoc-recognizers-recognizerName"
              data-cy="recognizerName"
              :class="{
                valid: !$v.analyzerAdHocRecognizers.recognizerName.$invalid,
                invalid: $v.analyzerAdHocRecognizers.recognizerName.$invalid,
              }"
              v-model="$v.analyzerAdHocRecognizers.recognizerName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-supportedLang">Supported Lang</label>
            <input
              type="text"
              class="form-control"
              name="supportedLang"
              id="analyzer-ad-hoc-recognizers-supportedLang"
              data-cy="supportedLang"
              :class="{
                valid: !$v.analyzerAdHocRecognizers.supportedLang.$invalid,
                invalid: $v.analyzerAdHocRecognizers.supportedLang.$invalid,
              }"
              v-model="$v.analyzerAdHocRecognizers.supportedLang.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-patternName">Pattern Name</label>
            <input
              type="text"
              class="form-control"
              name="patternName"
              id="analyzer-ad-hoc-recognizers-patternName"
              data-cy="patternName"
              :class="{
                valid: !$v.analyzerAdHocRecognizers.patternName.$invalid,
                invalid: $v.analyzerAdHocRecognizers.patternName.$invalid,
              }"
              v-model="$v.analyzerAdHocRecognizers.patternName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-regex">Regex</label>
            <input
              type="text"
              class="form-control"
              name="regex"
              id="analyzer-ad-hoc-recognizers-regex"
              data-cy="regex"
              :class="{ valid: !$v.analyzerAdHocRecognizers.regex.$invalid, invalid: $v.analyzerAdHocRecognizers.regex.$invalid }"
              v-model="$v.analyzerAdHocRecognizers.regex.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-score">Score</label>
            <input
              type="number"
              class="form-control"
              name="score"
              id="analyzer-ad-hoc-recognizers-score"
              data-cy="score"
              :class="{ valid: !$v.analyzerAdHocRecognizers.score.$invalid, invalid: $v.analyzerAdHocRecognizers.score.$invalid }"
              v-model.number="$v.analyzerAdHocRecognizers.score.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-context">Context</label>
            <input
              type="text"
              class="form-control"
              name="context"
              id="analyzer-ad-hoc-recognizers-context"
              data-cy="context"
              :class="{ valid: !$v.analyzerAdHocRecognizers.context.$invalid, invalid: $v.analyzerAdHocRecognizers.context.$invalid }"
              v-model="$v.analyzerAdHocRecognizers.context.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-denyList">Deny List</label>
            <input
              type="text"
              class="form-control"
              name="denyList"
              id="analyzer-ad-hoc-recognizers-denyList"
              data-cy="denyList"
              :class="{ valid: !$v.analyzerAdHocRecognizers.denyList.$invalid, invalid: $v.analyzerAdHocRecognizers.denyList.$invalid }"
              v-model="$v.analyzerAdHocRecognizers.denyList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-supportedEntity">Supported Entity</label>
            <input
              type="text"
              class="form-control"
              name="supportedEntity"
              id="analyzer-ad-hoc-recognizers-supportedEntity"
              data-cy="supportedEntity"
              :class="{
                valid: !$v.analyzerAdHocRecognizers.supportedEntity.$invalid,
                invalid: $v.analyzerAdHocRecognizers.supportedEntity.$invalid,
              }"
              v-model="$v.analyzerAdHocRecognizers.supportedEntity.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="analyzer-ad-hoc-recognizers-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{
                  valid: !$v.analyzerAdHocRecognizers.creationDate.$invalid,
                  invalid: $v.analyzerAdHocRecognizers.creationDate.$invalid,
                }"
                :value="convertDateTimeFromServer($v.analyzerAdHocRecognizers.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-ad-hoc-recognizers-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="analyzer-ad-hoc-recognizers-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{
                  valid: !$v.analyzerAdHocRecognizers.lastUpdated.$invalid,
                  invalid: $v.analyzerAdHocRecognizers.lastUpdated.$invalid,
                }"
                :value="convertDateTimeFromServer($v.analyzerAdHocRecognizers.lastUpdated.$model)"
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
            :disabled="$v.analyzerAdHocRecognizers.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./analyzer-ad-hoc-recognizers-update.component.ts"></script>
