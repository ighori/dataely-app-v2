import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FileConfigUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.fileConfig.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#file-config-name'));

  detailInput: ElementFinder = element(by.css('input#file-config-detail'));

  columnNameLineNumberInput: ElementFinder = element(by.css('input#file-config-columnNameLineNumber'));

  encodingInput: ElementFinder = element(by.css('input#file-config-encoding'));

  separatorCharInput: ElementFinder = element(by.css('input#file-config-separatorChar'));

  quoteCharInput: ElementFinder = element(by.css('input#file-config-quoteChar'));

  escapeCharInput: ElementFinder = element(by.css('input#file-config-escapeChar'));

  fixedValueWidthInput: ElementFinder = element(by.css('input#file-config-fixedValueWidth'));

  skipEmptyLinesInput: ElementFinder = element(by.css('input#file-config-skipEmptyLines'));

  skipEmptyColumnsInput: ElementFinder = element(by.css('input#file-config-skipEmptyColumns'));

  failOnInconsistentLineWidthInput: ElementFinder = element(by.css('input#file-config-failOnInconsistentLineWidth'));

  skipEbcdicHeaderInput: ElementFinder = element(by.css('input#file-config-skipEbcdicHeader'));

  eolPresentInput: ElementFinder = element(by.css('input#file-config-eolPresent'));

  fileTypeInput: ElementFinder = element(by.css('input#file-config-fileType'));

  creationDateInput: ElementFinder = element(by.css('input#file-config-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#file-config-lastUpdated'));
}
