<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tableColumn">
        <h2 class="jh-entity-heading" data-cy="tableColumnDetailsHeading"><span>TableColumn</span> {{ tableColumn.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Column Name</span>
          </dt>
          <dd>
            <span>{{ tableColumn.columnName }}</span>
          </dd>
          <dt>
            <span>Column Type</span>
          </dt>
          <dd>
            <span>{{ tableColumn.columnType }}</span>
          </dd>
          <dt>
            <span>Column Size</span>
          </dt>
          <dd>
            <span>{{ tableColumn.columnSize }}</span>
          </dd>
          <dt>
            <span>Is Nullable</span>
          </dt>
          <dd>
            <span>{{ tableColumn.isNullable }}</span>
          </dd>
          <dt>
            <span>Is Primary Key</span>
          </dt>
          <dd>
            <span>{{ tableColumn.isPrimaryKey }}</span>
          </dd>
          <dt>
            <span>Is Foreign Key</span>
          </dt>
          <dd>
            <span>{{ tableColumn.isForeignKey }}</span>
          </dd>
          <dt>
            <span>Is Indexed</span>
          </dt>
          <dd>
            <span>{{ tableColumn.isIndexed }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ tableColumn.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ tableColumn.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Tables Definition</span>
          </dt>
          <dd>
            <div v-if="tableColumn.tablesDefinition">
              <router-link :to="{ name: 'TablesDefinitionView', params: { tablesDefinitionId: tableColumn.tablesDefinition.id } }">{{
                tableColumn.tablesDefinition.tableName
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="tableColumn.id"
          :to="{ name: 'TableColumnEdit', params: { tableColumnId: tableColumn.id } }"
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

<script lang="ts" src="./table-column-details.component.ts"></script>
