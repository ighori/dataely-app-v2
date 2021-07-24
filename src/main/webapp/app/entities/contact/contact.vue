<template>
  <div>
    <h2 id="page-heading" data-cy="ContactHeading">
      <span id="contact-heading">Contacts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'ContactCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-contact"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Contact </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && contacts && contacts.length === 0">
      <span>No contacts found</span>
    </div>
    <div class="table-responsive" v-if="contacts && contacts.length > 0">
      <table class="table table-striped" aria-describedby="contacts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('firstName')">
              <span>First Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'firstName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastName')">
              <span>Last Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span>Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('role')">
              <span>Role</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'role'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span>Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phone')">
              <span>Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('extension')">
              <span>Extension</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'extension'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mobile')">
              <span>Mobile</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mobile'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('location')">
              <span>Location</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'location'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('addressLine1')">
              <span>Address Line 1</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'addressLine1'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('addressLine2')">
              <span>Address Line 2</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'addressLine2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('city')">
              <span>City</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'city'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('country')">
              <span>Country</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'country'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('image')">
              <span>Image</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'image'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="contact in contacts" :key="contact.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ContactView', params: { contactId: contact.id } }">{{ contact.id }}</router-link>
            </td>
            <td>{{ contact.firstName }}</td>
            <td>{{ contact.lastName }}</td>
            <td>{{ contact.name }}</td>
            <td>{{ contact.role }}</td>
            <td>{{ contact.email }}</td>
            <td>{{ contact.phone }}</td>
            <td>{{ contact.extension }}</td>
            <td>{{ contact.mobile }}</td>
            <td>{{ contact.location }}</td>
            <td>{{ contact.addressLine1 }}</td>
            <td>{{ contact.addressLine2 }}</td>
            <td>{{ contact.city }}</td>
            <td>{{ contact.country }}</td>
            <td>
              <a v-if="contact.image" v-on:click="openFile(contact.imageContentType, contact.image)">
                <img
                  v-bind:src="'data:' + contact.imageContentType + ';base64,' + contact.image"
                  style="max-height: 30px"
                  alt="contact image"
                />
              </a>
              <span v-if="contact.image">{{ contact.imageContentType }}, {{ byteSize(contact.image) }}</span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ContactView', params: { contactId: contact.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ContactEdit', params: { contactId: contact.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(contact)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="dataelyAppV2App.contact.delete.question" data-cy="contactDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-contact-heading">Are you sure you want to delete this Contact?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-contact"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeContact()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="contacts && contacts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./contact.component.ts"></script>
