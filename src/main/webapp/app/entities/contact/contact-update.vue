<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.contact.home.createOrEditLabel" data-cy="ContactCreateUpdateHeading">Create or edit a Contact</h2>
        <div>
          <div class="form-group" v-if="contact.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="contact.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="contact-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.contact.firstName.$invalid, invalid: $v.contact.firstName.$invalid }"
              v-model="$v.contact.firstName.$model"
              required
            />
            <div v-if="$v.contact.firstName.$anyDirty && $v.contact.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.contact.firstName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="contact-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.contact.lastName.$invalid, invalid: $v.contact.lastName.$invalid }"
              v-model="$v.contact.lastName.$model"
              required
            />
            <div v-if="$v.contact.lastName.$anyDirty && $v.contact.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.contact.lastName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="contact-name"
              data-cy="name"
              :class="{ valid: !$v.contact.name.$invalid, invalid: $v.contact.name.$invalid }"
              v-model="$v.contact.name.$model"
              required
            />
            <div v-if="$v.contact.name.$anyDirty && $v.contact.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.contact.name.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-role">Role</label>
            <select
              class="form-control"
              name="role"
              :class="{ valid: !$v.contact.role.$invalid, invalid: $v.contact.role.$invalid }"
              v-model="$v.contact.role.$model"
              id="contact-role"
              data-cy="role"
              required
            >
              <option value="OWNER">OWNER</option>
              <option value="ADMINISTRATOR">ADMINISTRATOR</option>
              <option value="SME">SME</option>
              <option value="REVIEWER">REVIEWER</option>
              <option value="APPROVER">APPROVER</option>
              <option value="ARCHITECT">ARCHITECT</option>
              <option value="ENGINEER">ENGINEER</option>
              <option value="DBA">DBA</option>
              <option value="MANAGER">MANAGER</option>
              <option value="DEVELOPER">DEVELOPER</option>
              <option value="TESTER">TESTER</option>
              <option value="ANALYST">ANALYST</option>
            </select>
            <div v-if="$v.contact.role.$anyDirty && $v.contact.role.$invalid">
              <small class="form-text text-danger" v-if="!$v.contact.role.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="contact-email"
              data-cy="email"
              :class="{ valid: !$v.contact.email.$invalid, invalid: $v.contact.email.$invalid }"
              v-model="$v.contact.email.$model"
              required
            />
            <div v-if="$v.contact.email.$anyDirty && $v.contact.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.contact.email.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.contact.email.pattern"> This field should follow pattern for "Email". </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="contact-phone"
              data-cy="phone"
              :class="{ valid: !$v.contact.phone.$invalid, invalid: $v.contact.phone.$invalid }"
              v-model="$v.contact.phone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-extension">Extension</label>
            <input
              type="text"
              class="form-control"
              name="extension"
              id="contact-extension"
              data-cy="extension"
              :class="{ valid: !$v.contact.extension.$invalid, invalid: $v.contact.extension.$invalid }"
              v-model="$v.contact.extension.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-mobile">Mobile</label>
            <input
              type="text"
              class="form-control"
              name="mobile"
              id="contact-mobile"
              data-cy="mobile"
              :class="{ valid: !$v.contact.mobile.$invalid, invalid: $v.contact.mobile.$invalid }"
              v-model="$v.contact.mobile.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-location">Location</label>
            <input
              type="text"
              class="form-control"
              name="location"
              id="contact-location"
              data-cy="location"
              :class="{ valid: !$v.contact.location.$invalid, invalid: $v.contact.location.$invalid }"
              v-model="$v.contact.location.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-addressLine1">Address Line 1</label>
            <input
              type="text"
              class="form-control"
              name="addressLine1"
              id="contact-addressLine1"
              data-cy="addressLine1"
              :class="{ valid: !$v.contact.addressLine1.$invalid, invalid: $v.contact.addressLine1.$invalid }"
              v-model="$v.contact.addressLine1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-addressLine2">Address Line 2</label>
            <input
              type="text"
              class="form-control"
              name="addressLine2"
              id="contact-addressLine2"
              data-cy="addressLine2"
              :class="{ valid: !$v.contact.addressLine2.$invalid, invalid: $v.contact.addressLine2.$invalid }"
              v-model="$v.contact.addressLine2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-city">City</label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="contact-city"
              data-cy="city"
              :class="{ valid: !$v.contact.city.$invalid, invalid: $v.contact.city.$invalid }"
              v-model="$v.contact.city.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-country">Country</label>
            <input
              type="text"
              class="form-control"
              name="country"
              id="contact-country"
              data-cy="country"
              :class="{ valid: !$v.contact.country.$invalid, invalid: $v.contact.country.$invalid }"
              v-model="$v.contact.country.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="contact-image">Image</label>
            <div>
              <img
                v-bind:src="'data:' + contact.imageContentType + ';base64,' + contact.image"
                style="max-height: 100px"
                v-if="contact.image"
                alt="contact image"
              />
              <div v-if="contact.image" class="form-text text-danger clearfix">
                <span class="pull-left">{{ contact.imageContentType }}, {{ byteSize(contact.image) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('image', 'imageContentType', 'file_image')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_image"
                id="file_image"
                data-cy="image"
                v-on:change="setFileData($event, contact, 'image', true)"
                accept="image/*"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="image"
              id="contact-image"
              data-cy="image"
              :class="{ valid: !$v.contact.image.$invalid, invalid: $v.contact.image.$invalid }"
              v-model="$v.contact.image.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="imageContentType"
              id="contact-imageContentType"
              v-model="contact.imageContentType"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.contact.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./contact-update.component.ts"></script>
