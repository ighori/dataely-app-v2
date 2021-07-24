import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class EnvironmentUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.environment.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#environment-name'));

  detailInput: ElementFinder = element(by.css('input#environment-detail'));

  typeSelect = element(by.css('select#environment-type'));

  purposeSelect = element(by.css('select#environment-purpose'));

  creationDateInput: ElementFinder = element(by.css('input#environment-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#environment-lastUpdated'));

  contactSelect = element(by.css('select#environment-contact'));

  applicationSelect = element(by.css('select#environment-application'));
}
