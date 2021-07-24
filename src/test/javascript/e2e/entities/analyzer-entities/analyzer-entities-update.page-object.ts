import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AnalyzerEntitiesUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.analyzerEntities.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#analyzer-entities-name'));

  detailInput: ElementFinder = element(by.css('input#analyzer-entities-detail'));

  entityNameInput: ElementFinder = element(by.css('input#analyzer-entities-entityName'));

  creationDateInput: ElementFinder = element(by.css('input#analyzer-entities-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#analyzer-entities-lastUpdated'));
}
