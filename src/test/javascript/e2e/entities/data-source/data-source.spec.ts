/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import DataSourceComponentsPage, { DataSourceDeleteDialog } from './data-source.page-object';
import DataSourceUpdatePage from './data-source-update.page-object';
import DataSourceDetailsPage from './data-source-details.page-object';

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

describe('DataSource e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: DataSourceUpdatePage;
  let detailsPage: DataSourceDetailsPage;
  let listPage: DataSourceComponentsPage;
  let deleteDialog: DataSourceDeleteDialog;
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

  it('should load DataSources', async () => {
    await navBarPage.getEntityPage('data-source');
    listPage = new DataSourceComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create DataSource page', async () => {
      await listPage.createButton.click();
      updatePage = new DataSourceUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a DataSource/);
    });

    it('should create and save DataSources', async () => {
      await updatePage.nameInput.sendKeys('name');

      await updatePage.detailInput.sendKeys('detail');

      await updatePage.databaseNameInput.sendKeys('databaseName');

      await updatePage.instanceNameInput.sendKeys('instanceName');

      await updatePage.schemaNameInput.sendKeys('schemaName');

      await updatePage.hostnameInput.sendKeys('hostname');

      await updatePage.portInput.sendKeys('5');

      await updatePage.usernameInput.sendKeys('username');

      await updatePage.passwordInput.sendKeys('password');

      await updatePage.iconInput.sendKeys('icon');

      await selectLastOption(updatePage.dbTypeSelect);

      await updatePage.dbVersionInput.sendKeys('dbVersion');

      await updatePage.schemaCountInput.sendKeys('5');

      await selectLastOption(updatePage.dsTypeSelect);

      await updatePage.driverClassNameInput.sendKeys('driverClassName');

      await updatePage.jdbcUrlInput.sendKeys('jdbcUrl');

      await updatePage.sidInput.sendKeys('sid');

      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      // await selectLastOption(updatePage.environmentSelect);

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

        deleteDialog = new DataSourceDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.dataSource.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details DataSource page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new DataSourceDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit DataSource page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');

        await updatePage.detailInput.clear();
        await updatePage.detailInput.sendKeys('modified');

        await updatePage.databaseNameInput.clear();
        await updatePage.databaseNameInput.sendKeys('modified');

        await updatePage.instanceNameInput.clear();
        await updatePage.instanceNameInput.sendKeys('modified');

        await updatePage.schemaNameInput.clear();
        await updatePage.schemaNameInput.sendKeys('modified');

        await updatePage.hostnameInput.clear();
        await updatePage.hostnameInput.sendKeys('modified');

        await clear(updatePage.portInput);
        await updatePage.portInput.sendKeys('6');

        await updatePage.usernameInput.clear();
        await updatePage.usernameInput.sendKeys('modified');

        await updatePage.passwordInput.clear();
        await updatePage.passwordInput.sendKeys('modified');

        await updatePage.iconInput.clear();
        await updatePage.iconInput.sendKeys('modified');

        await updatePage.dbVersionInput.clear();
        await updatePage.dbVersionInput.sendKeys('modified');

        await clear(updatePage.schemaCountInput);
        await updatePage.schemaCountInput.sendKeys('6');

        await updatePage.driverClassNameInput.clear();
        await updatePage.driverClassNameInput.sendKeys('modified');

        await updatePage.jdbcUrlInput.clear();
        await updatePage.jdbcUrlInput.sendKeys('modified');

        await updatePage.sidInput.clear();
        await updatePage.sidInput.sendKeys('modified');

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
