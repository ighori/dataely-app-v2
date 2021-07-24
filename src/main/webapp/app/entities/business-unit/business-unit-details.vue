<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="businessUnit">
        <h2 class="jh-entity-heading" data-cy="businessUnitDetailsHeading"><span>BusinessUnit</span> {{ businessUnit.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ businessUnit.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ businessUnit.detail }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ businessUnit.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ businessUnit.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Organization</span>
          </dt>
          <dd>
            <div v-if="businessUnit.organization">
              <router-link :to="{ name: 'OrganizationView', params: { organizationId: businessUnit.organization.id } }">{{
                businessUnit.organization.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="businessUnit.id"
          :to="{ name: 'BusinessUnitEdit', params: { businessUnitId: businessUnit.id } }"
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

<script lang="ts" src="./business-unit-details.component.ts"></script>
