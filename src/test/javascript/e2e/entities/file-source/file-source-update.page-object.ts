import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class FileSourceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.fileSource.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#file-source-name'));

  detailInput: ElementFinder = element(by.css('input#file-source-detail'));

  hostnameInput: ElementFinder = element(by.css('input#file-source-hostname'));

  portInput: ElementFinder = element(by.css('input#file-source-port'));

  usernameInput: ElementFinder = element(by.css('input#file-source-username'));

  passwordInput: ElementFinder = element(by.css('input#file-source-password'));

  iconInput: ElementFinder = element(by.css('input#file-source-icon'));

  connectionTypeInput: ElementFinder = element(by.css('input#file-source-connectionType'));

  typeSelect = element(by.css('select#file-source-type'));

  regionInput: ElementFinder = element(by.css('input#file-source-region'));

  ignoreDottedFilesInput: ElementFinder = element(by.css('input#file-source-ignoreDottedFiles'));

  recurseInput: ElementFinder = element(by.css('input#file-source-recurse'));

  pathFilterRegexInput: ElementFinder = element(by.css('input#file-source-pathFilterRegex'));

  remotePathInput: ElementFinder = element(by.css('input#file-source-remotePath'));

  creationDateInput: ElementFinder = element(by.css('input#file-source-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#file-source-lastUpdated'));

  environmentSelect = element(by.css('select#file-source-environment'));
}
