<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tableRelationship">
        <h2 class="jh-entity-heading" data-cy="tableRelationshipDetailsHeading">
          <span>TableRelationship</span> {{ tableRelationship.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Source</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.source }}</span>
          </dd>
          <dt>
            <span>Target</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.target }}</span>
          </dd>
          <dt>
            <span>Source Key</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.sourceKey }}</span>
          </dd>
          <dt>
            <span>Target Key</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.targetKey }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ tableRelationship.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Tables Definition</span>
          </dt>
          <dd>
            <div v-if="tableRelationship.tablesDefinition">
              <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tableRelationship.tablesDefinition.id } }">{{
                tableRelationship.tablesDefinition.tableName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="tableRelationship.id"
          :to="{ name: 'TableRelationshipEdit', params: { tableRelationshipId: tableRelationship.id } }"
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

<script lang="ts" src="./table-relationship-details.component.ts"></script>
