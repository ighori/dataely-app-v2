import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class TableRelationshipUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.tableRelationship.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  sourceInput: ElementFinder = element(by.css('input#table-relationship-source'));

  targetInput: ElementFinder = element(by.css('input#table-relationship-target'));

  sourceKeyInput: ElementFinder = element(by.css('input#table-relationship-sourceKey'));

  targetKeyInput: ElementFinder = element(by.css('input#table-relationship-targetKey'));

  creationDateInput: ElementFinder = element(by.css('input#table-relationship-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#table-relationship-lastUpdated'));

  tablesDefinitionSelect = element(by.css('select#table-relationship-tablesDefinition'));
}
