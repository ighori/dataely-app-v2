<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="analyzerResult">
        <h2 class="jh-entity-heading" data-cy="analyzerResultDetailsHeading"><span>AnalyzerResult</span> {{ analyzerResult.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.detail }}</span>
          </dd>
          <dt>
            <span>Object Id</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.objectId }}</span>
          </dd>
          <dt>
            <span>Object Type</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.objectType }}</span>
          </dd>
          <dt>
            <span>Field Id</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.fieldId }}</span>
          </dd>
          <dt>
            <span>Field Type</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.fieldType }}</span>
          </dd>
          <dt>
            <span>Entity Type</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.entityType }}</span>
          </dd>
          <dt>
            <span>Start</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.start }}</span>
          </dd>
          <dt>
            <span>End</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.end }}</span>
          </dd>
          <dt>
            <span>Score</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.score }}</span>
          </dd>
          <dt>
            <span>Recognizer</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.recognizer }}</span>
          </dd>
          <dt>
            <span>Pattern Name</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.patternName }}</span>
          </dd>
          <dt>
            <span>Pattern Expr</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.patternExpr }}</span>
          </dd>
          <dt>
            <span>Original Score</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.originalScore }}</span>
          </dd>
          <dt>
            <span>Textual Explanation</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.textualExplanation }}</span>
          </dd>
          <dt>
            <span>Supportive Context Word</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.supportiveContextWord }}</span>
          </dd>
          <dt>
            <span>Validation Result</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.validationResult }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ analyzerResult.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Data Source</span>
          </dt>
          <dd>
            <div v-if="analyzerResult.dataSource">
              <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: analyzerResult.dataSource.id } }">{{
                analyzerResult.dataSource.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>File Source</span>
          </dt>
          <dd>
            <div v-if="analyzerResult.fileSource">
              <router-link :to="{ name: 'FileSourceView', params: { fileSourceId: analyzerResult.fileSource.id } }">{{
                analyzerResult.fileSource.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="analyzerResult.id"
          :to="{ name: 'AnalyzerResultEdit', params: { analyzerResultId: analyzerResult.id } }"
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

<script lang="ts" src="./analyzer-result-details.component.ts"></script>
