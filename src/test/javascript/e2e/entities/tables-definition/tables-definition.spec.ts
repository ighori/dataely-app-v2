/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import TablesDefinitionComponentsPage, { TablesDefinitionDeleteDialog } from './tables-definition.page-object';
import TablesDefinitionUpdatePage from './tables-definition-update.page-object';
import TablesDefinitionDetailsPage from './tables-definition-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden,
} from '../../util/utils';

const expect = chai.expect;

describe('TablesDefinition e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: TablesDefinitionUpdatePage;
  let detailsPage: TablesDefinitionDetailsPage;
  let listPage: TablesDefinitionComponentsPage;
  let deleteDialog: TablesDefinitionDeleteDialog;
  let beforeRecordsCount = 0;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login(username, password);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load TablesDefinitions', async () => {
    await navBarPage.getEntityPage('tables-definition');
    listPage = new TablesDefinitionComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create TablesDefinition page', async () => {
      await listPage.createButton.click();
      updatePage = new TablesDefinitionUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a TablesDefinition/);
    });

    it('should create and save TablesDefinitions', async () => {
      await updatePage.tableNameInput.sendKeys('tableName');

      await updatePage.valueInput.sendKeys('5');

      await updatePage.symbolSizeInput.sendKeys('5');

      await updatePage.categoryInput.sendKeys('5');

      await updatePage.colCntInput.sendKeys('5');

      await updatePage.colCntNbrInput.sendKeys('5');

      await updatePage.colCntTBInput.sendKeys('5');

      await updatePage.colCntSTRInput.sendKeys('5');

      await updatePage.colCntBLInput.sendKeys('5');

      await updatePage.colCntPKInput.sendKeys('5');

      await updatePage.colCntFKInput.sendKeys('5');

      await updatePage.colCntIXInput.sendKeys('5');

      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      // await selectLastOption(updatePage.dsSchemaSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new TablesDefinitionDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.tablesDefinition.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details TablesDefinition page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new TablesDefinitionDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit TablesDefinition page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.tableNameInput.clear();
        await updatePage.tableNameInput.sendKeys('modified');

        await clear(updatePage.valueInput);
        await updatePage.valueInput.sendKeys('6');

        await clear(updatePage.symbolSizeInput);
        await updatePage.symbolSizeInput.sendKeys('6');

        await clear(updatePage.categoryInput);
        await updatePage.categoryInput.sendKeys('6');

        await clear(updatePage.colCntInput);
        await updatePage.colCntInput.sendKeys('6');

        await clear(updatePage.colCntNbrInput);
        await updatePage.colCntNbrInput.sendKeys('6');

        await clear(updatePage.colCntTBInput);
        await updatePage.colCntTBInput.sendKeys('6');

        await clear(updatePage.colCntSTRInput);
        await updatePage.colCntSTRInput.sendKeys('6');

        await clear(updatePage.colCntBLInput);
        await updatePage.colCntBLInput.sendKeys('6');

        await clear(updatePage.colCntPKInput);
        await updatePage.colCntPKInput.sendKeys('6');

        await clear(updatePage.colCntFKInput);
        await updatePage.colCntFKInput.sendKeys('6');

        await clear(updatePage.colCntIXInput);
        await updatePage.colCntIXInput.sendKeys('6');

        await updatePage.creationDateInput.clear();
        await updatePage.creationDateInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');

        await updatePage.lastUpdatedInput.clear();
        await updatePage.lastUpdatedInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
