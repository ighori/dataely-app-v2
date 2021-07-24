import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AnalyzerJobUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.analyzerJob.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#analyzer-job-name'));

  detailInput: ElementFinder = element(by.css('input#analyzer-job-detail'));

  endTimeInput: ElementFinder = element(by.css('input#analyzer-job-endTime'));

  startTimeInput: ElementFinder = element(by.css('input#analyzer-job-startTime'));

  statusSelect = element(by.css('select#analyzer-job-status'));

  previousRunTimeInput: ElementFinder = element(by.css('input#analyzer-job-previousRunTime'));

  creationDateInput: ElementFinder = element(by.css('input#analyzer-job-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#analyzer-job-lastUpdated'));

  environmentSelect = element(by.css('select#analyzer-job-environment'));

  dataSourceSelect = element(by.css('select#analyzer-job-dataSource'));

  fileSourceSelect = element(by.css('select#analyzer-job-fileSource'));

  userSelect = element(by.css('select#analyzer-job-user'));
}
