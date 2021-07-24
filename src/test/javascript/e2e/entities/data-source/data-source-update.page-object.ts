import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DataSourceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.dataSource.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#data-source-name'));

  detailInput: ElementFinder = element(by.css('input#data-source-detail'));

  databaseNameInput: ElementFinder = element(by.css('input#data-source-databaseName'));

  instanceNameInput: ElementFinder = element(by.css('input#data-source-instanceName'));

  schemaNameInput: ElementFinder = element(by.css('input#data-source-schemaName'));

  hostnameInput: ElementFinder = element(by.css('input#data-source-hostname'));

  portInput: ElementFinder = element(by.css('input#data-source-port'));

  usernameInput: ElementFinder = element(by.css('input#data-source-username'));

  passwordInput: ElementFinder = element(by.css('input#data-source-password'));

  iconInput: ElementFinder = element(by.css('input#data-source-icon'));

  dbTypeSelect = element(by.css('select#data-source-dbType'));

  dbVersionInput: ElementFinder = element(by.css('input#data-source-dbVersion'));

  schemaCountInput: ElementFinder = element(by.css('input#data-source-schemaCount'));

  dsTypeSelect = element(by.css('select#data-source-dsType'));

  driverClassNameInput: ElementFinder = element(by.css('input#data-source-driverClassName'));

  jdbcUrlInput: ElementFinder = element(by.css('input#data-source-jdbcUrl'));

  sidInput: ElementFinder = element(by.css('input#data-source-sid'));

  creationDateInput: ElementFinder = element(by.css('input#data-source-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#data-source-lastUpdated'));

  environmentSelect = element(by.css('select#data-source-environment'));
}
