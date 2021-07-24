<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="environment">
        <h2 class="jh-entity-heading" data-cy="environmentDetailsHeading"><span>Environment</span> {{ environment.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ environment.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ environment.detail }}</span>
          </dd>
          <dt>
            <span>Type</span>
          </dt>
          <dd>
            <span>{{ environment.type }}</span>
          </dd>
          <dt>
            <span>Purpose</span>
          </dt>
          <dd>
            <span>{{ environment.purpose }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ environment.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ environment.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Contact</span>
          </dt>
          <dd>
            <div v-if="environment.contact">
              <router-link :to="{ name: 'ContactView', params: { contactId: environment.contact.id } }">{{
                environment.contact.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Application</span>
          </dt>
          <dd>
            <div v-if="environment.application">
              <router-link :to="{ name: 'ApplicationView', params: { applicationId: environment.application.id } }">{{
                environment.application.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="environment.id"
          :to="{ name: 'EnvironmentEdit', params: { environmentId: environment.id } }"
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

<script lang="ts" src="./environment-details.component.ts"></script>
