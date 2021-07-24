<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="contact">
        <h2 class="jh-entity-heading" data-cy="contactDetailsHeading"><span>Contact</span> {{ contact.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>First Name</span>
          </dt>
          <dd>
            <span>{{ contact.firstName }}</span>
          </dd>
          <dt>
            <span>Last Name</span>
          </dt>
          <dd>
            <span>{{ contact.lastName }}</span>
          </dd>
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ contact.name }}</span>
          </dd>
          <dt>
            <span>Role</span>
          </dt>
          <dd>
            <span>{{ contact.role }}</span>
          </dd>
          <dt>
            <span>Email</span>
          </dt>
          <dd>
            <span>{{ contact.email }}</span>
          </dd>
          <dt>
            <span>Phone</span>
          </dt>
          <dd>
            <span>{{ contact.phone }}</span>
          </dd>
          <dt>
            <span>Extension</span>
          </dt>
          <dd>
            <span>{{ contact.extension }}</span>
          </dd>
          <dt>
            <span>Mobile</span>
          </dt>
          <dd>
            <span>{{ contact.mobile }}</span>
          </dd>
          <dt>
            <span>Location</span>
          </dt>
          <dd>
            <span>{{ contact.location }}</span>
          </dd>
          <dt>
            <span>Address Line 1</span>
          </dt>
          <dd>
            <span>{{ contact.addressLine1 }}</span>
          </dd>
          <dt>
            <span>Address Line 2</span>
          </dt>
          <dd>
            <span>{{ contact.addressLine2 }}</span>
          </dd>
          <dt>
            <span>City</span>
          </dt>
          <dd>
            <span>{{ contact.city }}</span>
          </dd>
          <dt>
            <span>Country</span>
          </dt>
          <dd>
            <span>{{ contact.country }}</span>
          </dd>
          <dt>
            <span>Image</span>
          </dt>
          <dd>
            <div v-if="contact.image">
              <a v-on:click="openFile(contact.imageContentType, contact.image)">
                <img
                  v-bind:src="'data:' + contact.imageContentType + ';base64,' + contact.image"
                  style="max-width: 100%"
                  alt="contact image"
                />
              </a>
              {{ contact.imageContentType }}, {{ byteSize(contact.image) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="contact.id" :to="{ name: 'ContactEdit', params: { contactId: contact.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./contact-details.component.ts"></script>
