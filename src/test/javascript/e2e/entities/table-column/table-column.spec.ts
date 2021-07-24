/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import TableColumnComponentsPage, { TableColumnDeleteDialog } from './table-column.page-object';
import TableColumnUpdatePage from './table-column-update.page-object';
import TableColumnDetailsPage from './table-column-details.page-object';

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

describe('TableColumn e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: TableColumnUpdatePage;
  let detailsPage: TableColumnDetailsPage;
  let listPage: TableColumnComponentsPage;
  let deleteDialog: TableColumnDeleteDialog;
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

  it('should load TableColumns', async () => {
    await navBarPage.getEntityPage('table-column');
    listPage = new TableColumnComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create TableColumn page', async () => {
      await listPage.createButton.click();
      updatePage = new TableColumnUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a TableColumn/);
    });

    it('should create and save TableColumns', async () => {
      await updatePage.columnNameInput.sendKeys('columnName');

      await updatePage.columnTypeInput.sendKeys('columnType');

      await updatePage.columnSizeInput.sendKeys('5');

      await updatePage.isNullableInput.click();

      await updatePage.isPrimaryKeyInput.click();

      await updatePage.isForeignKeyInput.click();

      await updatePage.isIndexedInput.click();

      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      // await selectLastOption(updatePage.tablesDefinitionSelect);

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

        deleteDialog = new TableColumnDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.tableColumn.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details TableColumn page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new TableColumnDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit TableColumn page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.columnNameInput.clear();
        await updatePage.columnNameInput.sendKeys('modified');

        await updatePage.columnTypeInput.clear();
        await updatePage.columnTypeInput.sendKeys('modified');

        await clear(updatePage.columnSizeInput);
        await updatePage.columnSizeInput.sendKeys('6');

        await updatePage.isNullableInput.click();

        await updatePage.isPrimaryKeyInput.click();

        await updatePage.isForeignKeyInput.click();

        await updatePage.isIndexedInput.click();

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
