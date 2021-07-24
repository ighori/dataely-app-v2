<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fileSource">
        <h2 class="jh-entity-heading" data-cy="fileSourceDetailsHeading"><span>FileSource</span> {{ fileSource.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ fileSource.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ fileSource.detail }}</span>
          </dd>
          <dt>
            <span>Hostname</span>
          </dt>
          <dd>
            <span>{{ fileSource.hostname }}</span>
          </dd>
          <dt>
            <span>Port</span>
          </dt>
          <dd>
            <span>{{ fileSource.port }}</span>
          </dd>
          <dt>
            <span>Username</span>
          </dt>
          <dd>
            <span>{{ fileSource.username }}</span>
          </dd>
          <dt>
            <span>Password</span>
          </dt>
          <dd>
            <span>{{ fileSource.password }}</span>
          </dd>
          <dt>
            <span>Icon</span>
          </dt>
          <dd>
            <span>{{ fileSource.icon }}</span>
          </dd>
          <dt>
            <span>Connection Type</span>
          </dt>
          <dd>
            <span>{{ fileSource.connectionType }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ fileSource.type }}</span>
          </dd>
          <dt>
            <span>Region</span>
          </dt>
          <dd>
            <span>{{ fileSource.region }}</span>
          </dd>
          <dt>
            <span>Ignore Dotted Files</span>
          </dt>
          <dd>
            <span>{{ fileSource.ignoreDottedFiles }}</span>
          </dd>
          <dt>
            <span>Recurse</span>
          </dt>
          <dd>
            <span>{{ fileSource.recurse }}</span>
          </dd>
          <dt>
            <span>Path Filter Regex</span>
          </dt>
          <dd>
            <span>{{ fileSource.pathFilterRegex }}</span>
          </dd>
          <dt>
            <span>Remote Path</span>
          </dt>
          <dd>
            <span>{{ fileSource.remotePath }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ fileSource.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ fileSource.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Environment</span>
          </dt>
          <dd>
            <div v-if="fileSource.environment">
              <router-link :to="{ name: 'EnvironmentView', params: { environmentId: fileSource.environment.id } }">{{
                fileSource.environment.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="fileSource.id"
          :to="{ name: 'FileSourceEdit', params: { fileSourceId: fileSource.id } }"
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

<script lang="ts" src="./file-source-details.component.ts"></script>
