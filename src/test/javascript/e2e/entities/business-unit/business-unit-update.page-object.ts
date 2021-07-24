import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class BusinessUnitUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.businessUnit.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#business-unit-name'));

  detailInput: ElementFinder = element(by.css('input#business-unit-detail'));

  creationDateInput: ElementFinder = element(by.css('input#business-unit-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#business-unit-lastUpdated'));

  organizationSelect = element(by.css('select#business-unit-organization'));
}
