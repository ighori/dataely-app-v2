import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class RelatedTableColumnUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.relatedTableColumn.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  columnNameInput: ElementFinder = element(by.css('input#related-table-column-columnName'));

  columnTypeInput: ElementFinder = element(by.css('input#related-table-column-columnType'));

  creationDateInput: ElementFinder = element(by.css('input#related-table-column-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#related-table-column-lastUpdated'));

  relatedTableSelect = element(by.css('select#related-table-column-relatedTable'));
}
