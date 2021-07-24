import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DsSchemaUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.dsSchema.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#ds-schema-name'));

  detailInput: ElementFinder = element(by.css('input#ds-schema-detail'));

  tableCountInput: ElementFinder = element(by.css('input#ds-schema-tableCount'));

  tableRelCountInput: ElementFinder = element(by.css('input#ds-schema-tableRelCount'));

  creationDateInput: ElementFinder = element(by.css('input#ds-schema-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#ds-schema-lastUpdated'));

  dataSourceSelect = element(by.css('select#ds-schema-dataSource'));
}
