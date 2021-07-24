import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class OrganizationUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.organization.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#organization-name'));

  detailInput: ElementFinder = element(by.css('input#organization-detail'));

  creationDateInput: ElementFinder = element(by.css('input#organization-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#organization-lastUpdated'));

  userSelect = element(by.css('select#organization-user'));
}
