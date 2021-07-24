<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fileField">
        <h2 class="jh-entity-heading" data-cy="fileFieldDetailsHeading"><span>FileField</span> {{ fileField.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Field Name</span>
          </dt>
          <dd>
            <span>{{ fileField.fieldName }}</span>
          </dd>
          <dt>
            <span>Field Type</span>
          </dt>
          <dd>
            <span>{{ fileField.fieldType }}</span>
          </dd>
          <dt>
            <span>Field Size</span>
          </dt>
          <dd>
            <span>{{ fileField.fieldSize }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ fileField.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ fileField.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>File Info</span>
          </dt>
          <dd>
            <div v-if="fileField.fileInfo">
              <router-link :to="{ name: 'FileInfoView', params: { fileInfoId: fileField.fileInfo.id } }">{{
                fileField.fileInfo.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="fileField.id"
          :to="{ name: 'FileFieldEdit', params: { fileFieldId: fileField.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-field-details.component.ts"></script>
