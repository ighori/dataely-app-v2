<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analyzerRecognizers">
        <h2 class="jh-entity-heading" data-cy="analyzerRecognizersDetailsHeading">
          <span>AnalyzerRecognizers</span> {{ analyzerRecognizers.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ analyzerRecognizers.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ analyzerRecognizers.detail }}</span>
          </dd>
          <dt>
            <span>Recognizer Name</span>
          </dt>
          <dd>
            <span>{{ analyzerRecognizers.recognizerName }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ analyzerRecognizers.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ analyzerRecognizers.lastUpdated | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="analyzerRecognizers.id"
          :to="{ name: 'AnalyzerRecognizersEdit', params: { analyzerRecognizersId: analyzerRecognizers.id } }"
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

<script lang="ts" src="./analyzer-recognizers-details.component.ts"></script>
