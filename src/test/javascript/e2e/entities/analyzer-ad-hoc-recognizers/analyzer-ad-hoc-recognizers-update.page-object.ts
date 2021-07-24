import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AnalyzerAdHocRecognizersUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.analyzerAdHocRecognizers.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-name'));

  detailInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-detail'));

  recognizerNameInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-recognizerName'));

  supportedLangInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-supportedLang'));

  patternNameInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-patternName'));

  regexInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-regex'));

  scoreInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-score'));

  contextInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-context'));

  denyListInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-denyList'));

  supportedEntityInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-supportedEntity'));

  creationDateInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#analyzer-ad-hoc-recognizers-lastUpdated'));
}
