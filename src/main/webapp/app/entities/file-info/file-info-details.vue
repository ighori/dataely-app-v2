<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fileInfo">
        <h2 class="jh-entity-heading" data-cy="fileInfoDetailsHeading"><span>FileInfo</span> {{ fileInfo.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ fileInfo.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ fileInfo.detail }}</span>
          </dd>
          <dt>
            <span>File Type</span>
          </dt>
          <dd>
            <span>{{ fileInfo.fileType }}</span>
          </dd>
          <dt>
            <span>File Path</span>
          </dt>
          <dd>
            <span>{{ fileInfo.filePath }}</span>
          </dd>
          <dt>
            <span>Column Name Line Number</span>
          </dt>
          <dd>
            <span>{{ fileInfo.columnNameLineNumber }}</span>
          </dd>
          <dt>
            <span>Encoding</span>
          </dt>
          <dd>
            <span>{{ fileInfo.encoding }}</span>
          </dd>
          <dt>
            <span>Separator Char</span>
          </dt>
          <dd>
            <span>{{ fileInfo.separatorChar }}</span>
          </dd>
          <dt>
            <span>Quote Char</span>
          </dt>
          <dd>
            <span>{{ fileInfo.quoteChar }}</span>
          </dd>
          <dt>
            <span>Escape Char</span>
          </dt>
          <dd>
            <span>{{ fileInfo.escapeChar }}</span>
          </dd>
          <dt>
            <span>Fixed Value Width</span>
          </dt>
          <dd>
            <span>{{ fileInfo.fixedValueWidth }}</span>
          </dd>
          <dt>
            <span>Skip Empty Lines</span>
          </dt>
          <dd>
            <span>{{ fileInfo.skipEmptyLines }}</span>
          </dd>
          <dt>
            <span>Skip Empty Columns</span>
          </dt>
          <dd>
            <span>{{ fileInfo.skipEmptyColumns }}</span>
          </dd>
          <dt>
            <span>Fail On Inconsistent Line Width</span>
          </dt>
          <dd>
            <span>{{ fileInfo.failOnInconsistentLineWidth }}</span>
          </dd>
          <dt>
            <span>Skip Ebcdic Header</span>
          </dt>
          <dd>
            <span>{{ fileInfo.skipEbcdicHeader }}</span>
          </dd>
          <dt>
            <span>Eol Present</span>
          </dt>
          <dd>
            <span>{{ fileInfo.eolPresent }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ fileInfo.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ fileInfo.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>File Source</span>
          </dt>
          <dd>
            <div v-if="fileInfo.fileSource">
              <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: fileInfo.fileSource.id } }">{{
                fileInfo.fileSource.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>File Config</span>
          </dt>
          <dd>
            <div v-if="fileInfo.fileConfig">
              <router-link :to="{ name: 'FileConfigView', params: { fileConfigId: fileInfo.fileConfig.id } }">{{
                fileInfo.fileConfig.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="fileInfo.id" :to="{ name: 'FileInfoEdit', params: { fileInfoId: fileInfo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./file-info-details.component.ts"></script>
