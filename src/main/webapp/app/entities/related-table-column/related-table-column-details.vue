<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="relatedTableColumn">
        <h2 class="jh-entity-heading" data-cy="relatedTableColumnDetailsHeading">
          <span>RelatedTableColumn</span> {{ relatedTableColumn.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Column Name</span>
          </dt>
          <dd>
            <span>{{ relatedTableColumn.columnName }}</span>
          </dd>
          <dt>
            <span>Column Type</span>
          </dt>
          <dd>
            <span>{{ relatedTableColumn.columnType }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ relatedTableColumn.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ relatedTableColumn.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Related Table</span>
          </dt>
          <dd>
            <div v-if="relatedTableColumn.relatedTable">
              <router-link :to="{ name: 'RelatedTableView', params: { relatedTableId: relatedTableColumn.relatedTable.id } }">{{
                relatedTableColumn.relatedTable.tableName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="relatedTableColumn.id"
          :to="{ name: 'RelatedTableColumnEdit', params: { relatedTableColumnId: relatedTableColumn.id } }"
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

<script lang="ts" src="./related-table-column-details.component.ts"></script>
