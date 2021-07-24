/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import FileSourceComponentsPage, { FileSourceDeleteDialog } from './file-source.page-object';
import FileSourceUpdatePage from './file-source-update.page-object';
import FileSourceDetailsPage from './file-source-details.page-object';

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

describe('FileSource e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: FileSourceUpdatePage;
  let detailsPage: FileSourceDetailsPage;
  let listPage: FileSourceComponentsPage;
  let deleteDialog: FileSourceDeleteDialog;
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

  it('should load FileSources', async () => {
    await navBarPage.getEntityPage('file-source');
    listPage = new FileSourceComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create FileSource page', async () => {
      await listPage.createButton.click();
      updatePage = new FileSourceUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a FileSource/);
    });

    it('should create and save FileSources', async () => {
      await updatePage.nameInput.sendKeys('name');

      await updatePage.detailInput.sendKeys('detail');

      await updatePage.hostnameInput.sendKeys('hostname');

      await updatePage.portInput.sendKeys('5');

      await updatePage.usernameInput.sendKeys('username');

      await updatePage.passwordInput.sendKeys('password');

      await updatePage.iconInput.sendKeys('icon');

      await updatePage.connectionTypeInput.sendKeys('connectionType');

      await selectLastOption(updatePage.typeSelect);

      await updatePage.regionInput.sendKeys('region');

      await updatePage.ignoreDottedFilesInput.click();

      await updatePage.recurseInput.click();

      await updatePage.pathFilterRegexInput.sendKeys('pathFilterRegex');

      await updatePage.remotePathInput.sendKeys('remotePath');

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

        deleteDialog = new FileSourceDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.fileSource.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details FileSource page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new FileSourceDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit FileSource page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');

        await updatePage.detailInput.clear();
        await updatePage.detailInput.sendKeys('modified');

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

        await updatePage.connectionTypeInput.clear();
        await updatePage.connectionTypeInput.sendKeys('modified');

        await updatePage.regionInput.clear();
        await updatePage.regionInput.sendKeys('modified');

        await updatePage.ignoreDottedFilesInput.click();

        await updatePage.recurseInput.click();

        await updatePage.pathFilterRegexInput.clear();
        await updatePage.pathFilterRegexInput.sendKeys('modified');

        await updatePage.remotePathInput.clear();
        await updatePage.remotePathInput.sendKeys('modified');

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
