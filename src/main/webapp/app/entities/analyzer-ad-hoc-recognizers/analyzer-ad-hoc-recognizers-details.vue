<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analyzerAdHocRecognizers">
        <h2 class="jh-entity-heading" data-cy="analyzerAdHocRecognizersDetailsHeading">
          <span>AnalyzerAdHocRecognizers</span> {{ analyzerAdHocRecognizers.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.detail }}</span>
          </dd>
          <dt>
            <span>Recognizer Name</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.recognizerName }}</span>
          </dd>
          <dt>
            <span>Supported Lang</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.supportedLang }}</span>
          </dd>
          <dt>
            <span>Pattern Name</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.patternName }}</span>
          </dd>
          <dt>
            <span>Regex</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.regex }}</span>
          </dd>
          <dt>
            <span>Score</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.score }}</span>
          </dd>
          <dt>
            <span>Context</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.context }}</span>
          </dd>
          <dt>
            <span>Deny List</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.denyList }}</span>
          </dd>
          <dt>
            <span>Supported Entity</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.supportedEntity }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ analyzerAdHocRecognizers.lastUpdated | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="analyzerAdHocRecognizers.id"
          :to="{ name: 'AnalyzerAdHocRecognizersEdit', params: { analyzerAdHocRecognizersId: analyzerAdHocRecognizers.id } }"
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

<script lang="ts" src="./analyzer-ad-hoc-recognizers-details.component.ts"></script>
