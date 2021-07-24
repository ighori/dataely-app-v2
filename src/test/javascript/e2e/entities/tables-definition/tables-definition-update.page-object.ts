import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class TablesDefinitionUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.tablesDefinition.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  tableNameInput: ElementFinder = element(by.css('input#tables-definition-tableName'));

  valueInput: ElementFinder = element(by.css('input#tables-definition-value'));

  symbolSizeInput: ElementFinder = element(by.css('input#tables-definition-symbolSize'));

  categoryInput: ElementFinder = element(by.css('input#tables-definition-category'));

  colCntInput: ElementFinder = element(by.css('input#tables-definition-colCnt'));

  colCntNbrInput: ElementFinder = element(by.css('input#tables-definition-colCntNbr'));

  colCntTBInput: ElementFinder = element(by.css('input#tables-definition-colCntTB'));

  colCntSTRInput: ElementFinder = element(by.css('input#tables-definition-colCntSTR'));

  colCntBLInput: ElementFinder = element(by.css('input#tables-definition-colCntBL'));

  colCntPKInput: ElementFinder = element(by.css('input#tables-definition-colCntPK'));

  colCntFKInput: ElementFinder = element(by.css('input#tables-definition-colCntFK'));

  colCntIXInput: ElementFinder = element(by.css('input#tables-definition-colCntIX'));

  creationDateInput: ElementFinder = element(by.css('input#tables-definition-creationDate'));

  lastUpdatedInput: ElementFinder = element(by.css('input#tables-definition-lastUpdated'));

  dsSchemaSelect = element(by.css('select#tables-definition-dsSchema'));
}
