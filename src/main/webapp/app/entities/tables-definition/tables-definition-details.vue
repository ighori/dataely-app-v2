<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tablesDefinition">
        <h2 class="jh-entity-heading" data-cy="tablesDefinitionDetailsHeading"><span>TablesDefinition</span> {{ tablesDefinition.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Table Name</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.tableName }}</span>
          </dd>
          <dt>
            <span>Value</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.value }}</span>
          </dd>
          <dt>
            <span>Symbol Size</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.symbolSize }}</span>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.category }}</span>
          </dd>
          <dt>
            <span>Col Cnt</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCnt }}</span>
          </dd>
          <dt>
            <span>Col Cnt Nbr</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntNbr }}</span>
          </dd>
          <dt>
            <span>Col Cnt TB</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntTB }}</span>
          </dd>
          <dt>
            <span>Col Cnt STR</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntSTR }}</span>
          </dd>
          <dt>
            <span>Col Cnt BL</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntBL }}</span>
          </dd>
          <dt>
            <span>Col Cnt PK</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntPK }}</span>
          </dd>
          <dt>
            <span>Col Cnt FK</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntFK }}</span>
          </dd>
          <dt>
            <span>Col Cnt IX</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.colCntIX }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ tablesDefinition.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Ds Schema</span>
          </dt>
          <dd>
            <div v-if="tablesDefinition.dsSchema">
              <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: tablesDefinition.dsSchema.id } }">{{
                tablesDefinition.dsSchema.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="tablesDefinition.id"
          :to="{ name: 'TablesDefinitionEdit', params: { tablesDefinitionId: tablesDefinition.id } }"
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

<script lang="ts" src="./tables-definition-details.component.ts"></script>
