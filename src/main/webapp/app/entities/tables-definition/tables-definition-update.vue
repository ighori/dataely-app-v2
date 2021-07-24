<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="dataelyAppV2App.tablesDefinition.home.createOrEditLabel" data-cy="TablesDefinitionCreateUpdateHeading">
          Create or edit a TablesDefinition
        </h2>
        <div>
          <div class="form-group" v-if="tablesDefinition.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tablesDefinition.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-tableName">Table Name</label>
            <input
              type="text"
              class="form-control"
              name="tableName"
              id="tables-definition-tableName"
              data-cy="tableName"
              :class="{ valid: !$v.tablesDefinition.tableName.$invalid, invalid: $v.tablesDefinition.tableName.$invalid }"
              v-model="$v.tablesDefinition.tableName.$model"
              required
            />
            <div v-if="$v.tablesDefinition.tableName.$anyDirty && $v.tablesDefinition.tableName.$invalid">
              <small class="form-text text-danger" v-if="!$v.tablesDefinition.tableName.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-value">Value</label>
            <input
              type="number"
              class="form-control"
              name="value"
              id="tables-definition-value"
              data-cy="value"
              :class="{ valid: !$v.tablesDefinition.value.$invalid, invalid: $v.tablesDefinition.value.$invalid }"
              v-model.number="$v.tablesDefinition.value.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-symbolSize">Symbol Size</label>
            <input
              type="number"
              class="form-control"
              name="symbolSize"
              id="tables-definition-symbolSize"
              data-cy="symbolSize"
              :class="{ valid: !$v.tablesDefinition.symbolSize.$invalid, invalid: $v.tablesDefinition.symbolSize.$invalid }"
              v-model.number="$v.tablesDefinition.symbolSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-category">Category</label>
            <input
              type="number"
              class="form-control"
              name="category"
              id="tables-definition-category"
              data-cy="category"
              :class="{ valid: !$v.tablesDefinition.category.$invalid, invalid: $v.tablesDefinition.category.$invalid }"
              v-model.number="$v.tablesDefinition.category.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCnt">Col Cnt</label>
            <input
              type="number"
              class="form-control"
              name="colCnt"
              id="tables-definition-colCnt"
              data-cy="colCnt"
              :class="{ valid: !$v.tablesDefinition.colCnt.$invalid, invalid: $v.tablesDefinition.colCnt.$invalid }"
              v-model.number="$v.tablesDefinition.colCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntNbr">Col Cnt Nbr</label>
            <input
              type="number"
              class="form-control"
              name="colCntNbr"
              id="tables-definition-colCntNbr"
              data-cy="colCntNbr"
              :class="{ valid: !$v.tablesDefinition.colCntNbr.$invalid, invalid: $v.tablesDefinition.colCntNbr.$invalid }"
              v-model.number="$v.tablesDefinition.colCntNbr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntTB">Col Cnt TB</label>
            <input
              type="number"
              class="form-control"
              name="colCntTB"
              id="tables-definition-colCntTB"
              data-cy="colCntTB"
              :class="{ valid: !$v.tablesDefinition.colCntTB.$invalid, invalid: $v.tablesDefinition.colCntTB.$invalid }"
              v-model.number="$v.tablesDefinition.colCntTB.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntSTR">Col Cnt STR</label>
            <input
              type="number"
              class="form-control"
              name="colCntSTR"
              id="tables-definition-colCntSTR"
              data-cy="colCntSTR"
              :class="{ valid: !$v.tablesDefinition.colCntSTR.$invalid, invalid: $v.tablesDefinition.colCntSTR.$invalid }"
              v-model.number="$v.tablesDefinition.colCntSTR.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntBL">Col Cnt BL</label>
            <input
              type="number"
              class="form-control"
              name="colCntBL"
              id="tables-definition-colCntBL"
              data-cy="colCntBL"
              :class="{ valid: !$v.tablesDefinition.colCntBL.$invalid, invalid: $v.tablesDefinition.colCntBL.$invalid }"
              v-model.number="$v.tablesDefinition.colCntBL.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntPK">Col Cnt PK</label>
            <input
              type="number"
              class="form-control"
              name="colCntPK"
              id="tables-definition-colCntPK"
              data-cy="colCntPK"
              :class="{ valid: !$v.tablesDefinition.colCntPK.$invalid, invalid: $v.tablesDefinition.colCntPK.$invalid }"
              v-model.number="$v.tablesDefinition.colCntPK.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntFK">Col Cnt FK</label>
            <input
              type="number"
              class="form-control"
              name="colCntFK"
              id="tables-definition-colCntFK"
              data-cy="colCntFK"
              :class="{ valid: !$v.tablesDefinition.colCntFK.$invalid, invalid: $v.tablesDefinition.colCntFK.$invalid }"
              v-model.number="$v.tablesDefinition.colCntFK.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-colCntIX">Col Cnt IX</label>
            <input
              type="number"
              class="form-control"
              name="colCntIX"
              id="tables-definition-colCntIX"
              data-cy="colCntIX"
              :class="{ valid: !$v.tablesDefinition.colCntIX.$invalid, invalid: $v.tablesDefinition.colCntIX.$invalid }"
              v-model.number="$v.tablesDefinition.colCntIX.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-creationDate">Creation Date</label>
            <div class="d-flex">
              <input
                id="tables-definition-creationDate"
                data-cy="creationDate"
                type="datetime-local"
                class="form-control"
                name="creationDate"
                :class="{ valid: !$v.tablesDefinition.creationDate.$invalid, invalid: $v.tablesDefinition.creationDate.$invalid }"
                :value="convertDateTimeFromServer($v.tablesDefinition.creationDate.$model)"
                @change="updateInstantField('creationDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-lastUpdated">Last Updated</label>
            <div class="d-flex">
              <input
                id="tables-definition-lastUpdated"
                data-cy="lastUpdated"
                type="datetime-local"
                class="form-control"
                name="lastUpdated"
                :class="{ valid: !$v.tablesDefinition.lastUpdated.$invalid, invalid: $v.tablesDefinition.lastUpdated.$invalid }"
                :value="convertDateTimeFromServer($v.tablesDefinition.lastUpdated.$model)"
                @change="updateInstantField('lastUpdated', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="tables-definition-dsSchema">Ds Schema</label>
            <select
              class="form-control"
              id="tables-definition-dsSchema"
              data-cy="dsSchema"
              name="dsSchema"
              v-model="tablesDefinition.dsSchema"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  tablesDefinition.dsSchema && dsSchemaOption.id === tablesDefinition.dsSchema.id
                    ? tablesDefinition.dsSchema
                    : dsSchemaOption
                "
                v-for="dsSchemaOption in dsSchemas"
                :key="dsSchemaOption.id"
              >
                {{ dsSchemaOption.name }}
              </option>
            </select>
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
            :disabled="$v.tablesDefinition.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tables-definition-update.component.ts"></script>
