import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AnalyzerResultUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.analyzerResult.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#analyzer-result-name'));

  detailInput: ElementFinder = element(by.css('input#analyzer-result-detail'));

  objectIdInput: ElementFinder = element(by.css('input#analyzer-result-objectId'));

  objectTypeSelect = element(by.css('select#analyzer-result-objectType'));

  fieldIdInput: ElementFinder = element(by.css('input#analyzer-result-fieldId'));

  fieldTypeInput: ElementFinder = element(by.css('input#analyzer-result-fieldType'));

  entityTypeInput: ElementFinder = element(by.css('input#analyzer-result-entityType'));

  startInput: ElementFinder = element(by.css('input#analyzer-result-start'));

  endInput: ElementFinder = element(by.css('input#analyzer-result-end'));

  scoreInput: ElementFinder = element(by.css('input#analyzer-result-score'));

  recognizerInput: ElementFinder = element(by.css('input#analyzer-result-recognizer'));

  patternNameInput: ElementFinder = element(by.css('input#analyzer-result-patternName'));

  patternExprInput: ElementFinder = element(by.css('input#analyzer-result-patternExpr'));

  originalScoreInput: ElementFinder = element(by.css('input#analyzer-result-originalScore'));

  textualExplanationInput: ElementFinder = element(by.css('input#analyzer-result-textualExplanation'));

  supportiveContextWordInput: ElementFinder = element(by.css('input#analyzer-result-supportiveContextWord'));

  validationResultInput: ElementFinder = element(by.css('input#analyzer-result-validationResult'));

  creationDateInput: ElementFinder = element(by.css('input#analyzer-result-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#analyzer-result-lastUpdated'));

  dataSourceSelect = element(by.css('select#analyzer-result-dataSource'));

  fileSourceSelect = element(by.css('select#analyzer-result-fileSource'));
}
