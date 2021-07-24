import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ApplicationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.application.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#application-name'));

  detailInput: ElementFinder = element(by.css('input#application-detail'));

  versionInput: ElementFinder = element(by.css('input#application-version'));

  appTypeSelect = element(by.css('select#application-appType'));

  creationDateInput: ElementFinder = element(by.css('input#application-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#application-lastUpdated'));

  businessUnitSelect = element(by.css('select#application-businessUnit'));
}
