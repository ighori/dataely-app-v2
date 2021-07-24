<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.analyzerRecognizers.home.createOrEditLabel" data-cy="AnalyzerRecognizersCreateUpdateHeading">
          Create or edit a AnalyzerRecognizers
        </h2>
        <div>
          <div class="form-group" v-if="analyzerRecognizers.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="analyzerRecognizers.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-recognizers-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="analyzer-recognizers-name"
              data-cy="name"
              :class="{ valid: !$v.analyzerRecognizers.name.$invalid, invalid: $v.analyzerRecognizers.name.$invalid }"
              v-model="$v.analyzerRecognizers.name.$model"
              required
            />
            <div v-if="$v.analyzerRecognizers.name.$anyDirty && $v.analyzerRecognizers.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.analyzerRecognizers.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-recognizers-detail">Detail</label>
            <input
              type="text"
              class="form-control"
              name="detail"
              id="analyzer-recognizers-detail"
              data-cy="detail"
              :class="{ valid: !$v.analyzerRecognizers.detail.$invalid, invalid: $v.analyzerRecognizers.detail.$invalid }"
              v-model="$v.analyzerRecognizers.detail.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-recognizers-recognizerName">Recognizer Name</label>
            <input
              type="text"
              class="form-control"
              name="recognizerName"
              id="analyzer-recognizers-recognizerName"
              data-cy="recognizerName"
              :class="{ valid: !$v.analyzerRecognizers.recognizerName.$invalid, invalid: $v.analyzerRecognizers.recognizerName.$invalid }"
              v-model="$v.analyzerRecognizers.recognizerName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-recognizers-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="analyzer-recognizers-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.analyzerRecognizers.creationDate.$invalid, invalid: $v.analyzerRecognizers.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerRecognizers.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="analyzer-recognizers-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="analyzer-recognizers-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.analyzerRecognizers.lastUpdated.$invalid, invalid: $v.analyzerRecognizers.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.analyzerRecognizers.lastUpdated.$model)"
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
            :disabled="$v.analyzerRecognizers.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./analyzer-recognizers-update.component.ts"></script>
