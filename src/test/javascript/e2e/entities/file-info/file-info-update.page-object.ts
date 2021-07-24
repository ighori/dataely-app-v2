import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FileInfoUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.fileInfo.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#file-info-name'));

  detailInput: ElementFinder = element(by.css('input#file-info-detail'));

  fileTypeSelect = element(by.css('select#file-info-fileType'));

  filePathInput: ElementFinder = element(by.css('input#file-info-filePath'));

  columnNameLineNumberInput: ElementFinder = element(by.css('input#file-info-columnNameLineNumber'));

  encodingInput: ElementFinder = element(by.css('input#file-info-encoding'));

  separatorCharInput: ElementFinder = element(by.css('input#file-info-separatorChar'));

  quoteCharInput: ElementFinder = element(by.css('input#file-info-quoteChar'));

  escapeCharInput: ElementFinder = element(by.css('input#file-info-escapeChar'));

  fixedValueWidthInput: ElementFinder = element(by.css('input#file-info-fixedValueWidth'));

  skipEmptyLinesInput: ElementFinder = element(by.css('input#file-info-skipEmptyLines'));

  skipEmptyColumnsInput: ElementFinder = element(by.css('input#file-info-skipEmptyColumns'));

  failOnInconsistentLineWidthInput: ElementFinder = element(by.css('input#file-info-failOnInconsistentLineWidth'));

  skipEbcdicHeaderInput: ElementFinder = element(by.css('input#file-info-skipEbcdicHeader'));

  eolPresentInput: ElementFinder = element(by.css('input#file-info-eolPresent'));

  creationDateInput: ElementFinder = element(by.css('input#file-info-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#file-info-lastUpdated'));

  fileSourceSelect = element(by.css('select#file-info-fileSource'));

  fileConfigSelect = element(by.css('select#file-info-fileConfig'));
}
