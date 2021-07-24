import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class DsSchemaRelationshipUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.dsSchemaRelationship.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  sourceInput: ElementFinder = element(by.css('input#ds-schema-relationship-source'));

  targetInput: ElementFinder = element(by.css('input#ds-schema-relationship-target'));

  creationDateInput: ElementFinder = element(by.css('input#ds-schema-relationship-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#ds-schema-relationship-lastUpdated'));

  dsSchemaSelect = element(by.css('select#ds-schema-relationship-dsSchema'));
}
