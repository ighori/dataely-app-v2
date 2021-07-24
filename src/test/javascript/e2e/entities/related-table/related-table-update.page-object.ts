import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class RelatedTableUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.relatedTable.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  tableNameInput: ElementFinder = element(by.css('input#related-table-tableName'));

  creationDateInput: ElementFinder = element(by.css('input#related-table-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#related-table-lastUpdated'));

  tablesDefinitionSelect = element(by.css('select#related-table-tablesDefinition'));
}
