<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analyzerJob">
        <h2 class="jh-entity-heading" data-cy="analyzerJobDetailsHeading"><span>AnalyzerJob</span> {{ analyzerJob.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.detail }}</span>
          </dd>
          <dt>
            <span>End Time</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.endTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Start Time</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.startTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.status }}</span>
          </dd>
          <dt>
            <span>Previous Run Time</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.previousRunTime }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ analyzerJob.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Environment</span>
          </dt>
          <dd>
            <div v-if="analyzerJob.environment">
              <router-link :to="{ name: 'EnvironmentView', params: { environmentId: analyzerJob.environment.id } }">{{
                analyzerJob.environment.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Data Source</span>
          </dt>
          <dd>
            <div v-if="analyzerJob.dataSource">
              <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: analyzerJob.dataSource.id } }">{{
                analyzerJob.dataSource.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>File Source</span>
          </dt>
          <dd>
            <div v-if="analyzerJob.fileSource">
              <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: analyzerJob.fileSource.id } }">{{
                analyzerJob.fileSource.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>User</span>
          </dt>
          <dd>
            {{ analyzerJob.user ? analyzerJob.user.login : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="analyzerJob.id"
          :to="{ name: 'AnalyzerJobEdit', params: { analyzerJobId: analyzerJob.id } }"
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

<script lang="ts" src="./analyzer-job-details.component.ts"></script>
