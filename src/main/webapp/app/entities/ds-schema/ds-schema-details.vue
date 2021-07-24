<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dsSchema">
        <h2 class="jh-entity-heading" data-cy="dsSchemaDetailsHeading"><span>DsSchema</span> {{ dsSchema.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ dsSchema.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ dsSchema.detail }}</span>
          </dd>
          <dt>
            <span>Table Count</span>
          </dt>
          <dd>
            <span>{{ dsSchema.tableCount }}</span>
          </dd>
          <dt>
            <span>Table Rel Count</span>
          </dt>
          <dd>
            <span>{{ dsSchema.tableRelCount }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ dsSchema.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ dsSchema.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Data Source</span>
          </dt>
          <dd>
            <div v-if="dsSchema.dataSource">
              <router-link :to="{ name: 'DataSourceView', params: { dataSourceId: dsSchema.dataSource.id } }">{{
                dsSchema.dataSource.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="dsSchema.id" :to="{ name: 'DsSchemaEdit', params: { dsSchemaId: dsSchema.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ds-schema-details.component.ts"></script>
