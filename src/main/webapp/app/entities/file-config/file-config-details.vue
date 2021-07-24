<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fileConfig">
        <h2 class="jh-entity-heading" data-cy="fileConfigDetailsHeading"><span>FileConfig</span> {{ fileConfig.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ fileConfig.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ fileConfig.detail }}</span>
          </dd>
          <dt>
            <span>Column Name Line Number</span>
          </dt>
          <dd>
            <span>{{ fileConfig.columnNameLineNumber }}</span>
          </dd>
          <dt>
            <span>Encoding</span>
          </dt>
          <dd>
            <span>{{ fileConfig.encoding }}</span>
          </dd>
          <dt>
            <span>Separator Char</span>
          </dt>
          <dd>
            <span>{{ fileConfig.separatorChar }}</span>
          </dd>
          <dt>
            <span>Quote Char</span>
          </dt>
          <dd>
            <span>{{ fileConfig.quoteChar }}</span>
          </dd>
          <dt>
            <span>Escape Char</span>
          </dt>
          <dd>
            <span>{{ fileConfig.escapeChar }}</span>
          </dd>
          <dt>
            <span>Fixed Value Width</span>
          </dt>
          <dd>
            <span>{{ fileConfig.fixedValueWidth }}</span>
          </dd>
          <dt>
            <span>Skip Empty Lines</span>
          </dt>
          <dd>
            <span>{{ fileConfig.skipEmptyLines }}</span>
          </dd>
          <dt>
            <span>Skip Empty Columns</span>
          </dt>
          <dd>
            <span>{{ fileConfig.skipEmptyColumns }}</span>
          </dd>
          <dt>
            <span>Fail On Inconsistent Line Width</span>
          </dt>
          <dd>
            <span>{{ fileConfig.failOnInconsistentLineWidth }}</span>
          </dd>
          <dt>
            <span>Skip Ebcdic Header</span>
          </dt>
          <dd>
            <span>{{ fileConfig.skipEbcdicHeader }}</span>
          </dd>
          <dt>
            <span>Eol Present</span>
          </dt>
          <dd>
            <span>{{ fileConfig.eolPresent }}</span>
          </dd>
          <dt>
            <span>File Type</span>
          </dt>
          <dd>
            <span>{{ fileConfig.fileType }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ fileConfig.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ fileConfig.lastUpdated | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="fileConfig.id"
          :to="{ name: 'FileConfigEdit', params: { fileConfigId: fileConfig.id } }"
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

<script lang="ts" src="./file-config-details.component.ts"></script>
