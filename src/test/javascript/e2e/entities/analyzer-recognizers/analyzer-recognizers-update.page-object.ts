import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AnalyzerRecognizersUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.analyzerRecognizers.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#analyzer-recognizers-name'));

  detailInput: ElementFinder = element(by.css('input#analyzer-recognizers-detail'));

  recognizerNameInput: ElementFinder = element(by.css('input#analyzer-recognizers-recognizerName'));

  creationDateInput: ElementFinder = element(by.css('input#analyzer-recognizers-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#analyzer-recognizers-lastUpdated'));
}
