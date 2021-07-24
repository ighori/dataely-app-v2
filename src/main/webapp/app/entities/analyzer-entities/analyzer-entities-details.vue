<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analyzerEntities">
        <h2 class="jh-entity-heading" data-cy="analyzerEntitiesDetailsHeading"><span>AnalyzerEntities</span> {{ analyzerEntities.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ analyzerEntities.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ analyzerEntities.detail }}</span>
          </dd>
          <dt>
            <span>Entity Name</span>
          </dt>
          <dd>
            <span>{{ analyzerEntities.entityName }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ analyzerEntities.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ analyzerEntities.lastUpdated | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="analyzerEntities.id"
          :to="{ name: 'AnalyzerEntitiesEdit', params: { analyzerEntitiesId: analyzerEntities.id } }"
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

<script lang="ts" src="./analyzer-entities-details.component.ts"></script>
