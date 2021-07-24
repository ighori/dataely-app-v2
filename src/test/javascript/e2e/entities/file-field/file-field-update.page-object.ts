import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FileFieldUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.fileField.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  fieldNameInput: ElementFinder = element(by.css('input#file-field-fieldName'));

  fieldTypeInput: ElementFinder = element(by.css('input#file-field-fieldType'));

  fieldSizeInput: ElementFinder = element(by.css('input#file-field-fieldSize'));

  creationDateInput: ElementFinder = element(by.css('input#file-field-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#file-field-lastUpdated'));

  fileInfoSelect = element(by.css('select#file-field-fileInfo'));
}
