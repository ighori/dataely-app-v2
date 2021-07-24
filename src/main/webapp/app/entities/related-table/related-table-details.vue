<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="relatedTable">
        <h2 class="jh-entity-heading" data-cy="relatedTableDetailsHeading"><span>RelatedTable</span> {{ relatedTable.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Table Name</span>
          </dt>
          <dd>
            <span>{{ relatedTable.tableName }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ relatedTable.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ relatedTable.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Tables Definition</span>
          </dt>
          <dd>
            <div v-if="relatedTable.tablesDefinition">
              <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: relatedTable.tablesDefinition.id } }">{{
                relatedTable.tablesDefinition.tableName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="relatedTable.id"
          :to="{ name: 'RelatedTableEdit', params: { relatedTableId: relatedTable.id } }"
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

<script lang="ts" src="./related-table-details.component.ts"></script>
