<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dataSource">
        <h2 class="jh-entity-heading" data-cy="dataSourceDetailsHeading"><span>DataSource</span> {{ dataSource.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ dataSource.name }}</span>
          </dd>
          <dt>
            <span>Detail</span>
          </dt>
          <dd>
            <span>{{ dataSource.detail }}</span>
          </dd>
          <dt>
            <span>Database Name</span>
          </dt>
          <dd>
            <span>{{ dataSource.databaseName }}</span>
          </dd>
          <dt>
            <span>Instance Name</span>
          </dt>
          <dd>
            <span>{{ dataSource.instanceName }}</span>
          </dd>
          <dt>
            <span>Schema Name</span>
          </dt>
          <dd>
            <span>{{ dataSource.schemaName }}</span>
          </dd>
          <dt>
            <span>Hostname</span>
          </dt>
          <dd>
            <span>{{ dataSource.hostname }}</span>
          </dd>
          <dt>
            <span>Port</span>
          </dt>
          <dd>
            <span>{{ dataSource.port }}</span>
          </dd>
          <dt>
            <span>Username</span>
          </dt>
          <dd>
            <span>{{ dataSource.username }}</span>
          </dd>
          <dt>
            <span>Password</span>
          </dt>
          <dd>
            <span>{{ dataSource.password }}</span>
          </dd>
          <dt>
            <span>Icon</span>
          </dt>
          <dd>
            <span>{{ dataSource.icon }}</span>
          </dd>
          <dt>
            <span>Db Type</span>
          </dt>
          <dd>
            <span>{{ dataSource.dbType }}</span>
          </dd>
          <dt>
            <span>Db Version</span>
          </dt>
          <dd>
            <span>{{ dataSource.dbVersion }}</span>
          </dd>
          <dt>
            <span>Schema Count</span>
          </dt>
          <dd>
            <span>{{ dataSource.schemaCount }}</span>
          </dd>
          <dt>
            <span>Ds Type</span>
          </dt>
          <dd>
            <span>{{ dataSource.dsType }}</span>
          </dd>
          <dt>
            <span>Driver Class Name</span>
          </dt>
          <dd>
            <span>{{ dataSource.driverClassName }}</span>
          </dd>
          <dt>
            <span>Jdbc Url</span>
          </dt>
          <dd>
            <span>{{ dataSource.jdbcUrl }}</span>
          </dd>
          <dt>
            <span>Sid</span>
          </dt>
          <dd>
            <span>{{ dataSource.sid }}</span>
          </dd>
          <dt>
            <span>Creation Date</span>
          </dt>
          <dd>
            <span>{{ dataSource.creationDate | formatDate }}</span>
          </dd>
          <dt>
            <span>Last Updated</span>
          </dt>
          <dd>
            <span>{{ dataSource.lastUpdated | formatDate }}</span>
          </dd>
          <dt>
            <span>Environment</span>
          </dt>
          <dd>
            <div v-if="dataSource.environment">
              <router-link :to="{ name: 'EnvironmentView', params: { environmentId: dataSource.environment.id } }">{{
                dataSource.environment.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="dataSource.id"
          :to="{ name: 'DataSourceEdit', params: { dataSourceId: dataSource.id } }"
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

<script lang="ts" src="./data-source-details.component.ts"></script>
