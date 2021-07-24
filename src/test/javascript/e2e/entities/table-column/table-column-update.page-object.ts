import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class TableColumnUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.tableColumn.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  columnNameInput: ElementFinder = element(by.css('input#table-column-columnName'));

  columnTypeInput: ElementFinder = element(by.css('input#table-column-columnType'));

  columnSizeInput: ElementFinder = element(by.css('input#table-column-columnSize'));

  isNullableInput: ElementFinder = element(by.css('input#table-column-isNullable'));

  isPrimaryKeyInput: ElementFinder = element(by.css('input#table-column-isPrimaryKey'));

  isForeignKeyInput: ElementFinder = element(by.css('input#table-column-isForeignKey'));

  isIndexedInput: ElementFinder = element(by.css('input#table-column-isIndexed'));

  creationDateInput: ElementFinder = element(by.css('input#table-column-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#table-column-lastUpdated'));

  tablesDefinitionSelect = element(by.css('select#table-column-tablesDefinition'));
}
