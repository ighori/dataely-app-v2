<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dsSchemaRelationship">
        <h2 class="jh-entity-heading" data-cy="dsSchemaRelationshipDetailsHeading">
          <span>DsSchemaRelationship</span> {{ dsSchemaRelationship.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Source</span>
          </dt>
          <dd>
            <span>{{ dsSchemaRelationship.source }}</span>
          </dd>
          <dt>
            <span>Target</span>
          </dt>
          <dd>
            <span>{{ dsSchemaRelationship.target }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ dsSchemaRelationship.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ dsSchemaRelationship.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Ds Schema</span>
          </dt>
          <dd>
            <div v-if="dsSchemaRelationship.dsSchema">
              <router-link :to="{ name: 'DsSchemaView', params: { dsSchemaId: dsSchemaRelationship.dsSchema.id } }">{{
                dsSchemaRelationship.dsSchema.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="dsSchemaRelationship.id"
          :to="{ name: 'DsSchemaRelationshipEdit', params: { dsSchemaRelationshipId: dsSchemaRelationship.id } }"
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

<script lang="ts" src="./ds-schema-relationship-details.component.ts"></script>
