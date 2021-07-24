/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import TableRelationshipComponentsPage, { TableRelationshipDeleteDialog } from './table-relationship.page-object';
import TableRelationshipUpdatePage from './table-relationship-update.page-object';
import TableRelationshipDetailsPage from './table-relationship-details.page-object';

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

describe('TableRelationship e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: TableRelationshipUpdatePage;
  let detailsPage: TableRelationshipDetailsPage;
  let listPage: TableRelationshipComponentsPage;
  let deleteDialog: TableRelationshipDeleteDialog;
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

  it('should load TableRelationships', async () => {
    await navBarPage.getEntityPage('table-relationship');
    listPage = new TableRelationshipComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create TableRelationship page', async () => {
      await listPage.createButton.click();
      updatePage = new TableRelationshipUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a TableRelationship/);
    });

    it('should create and save TableRelationships', async () => {
      await updatePage.sourceInput.sendKeys('source');

      await updatePage.targetInput.sendKeys('target');

      await updatePage.sourceKeyInput.sendKeys('sourceKey');

      await updatePage.targetKeyInput.sendKeys('targetKey');

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

        deleteDialog = new TableRelationshipDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.tableRelationship.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details TableRelationship page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new TableRelationshipDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit TableRelationship page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.sourceInput.clear();
        await updatePage.sourceInput.sendKeys('modified');

        await updatePage.targetInput.clear();
        await updatePage.targetInput.sendKeys('modified');

        await updatePage.sourceKeyInput.clear();
        await updatePage.sourceKeyInput.sendKeys('modified');

        await updatePage.targetKeyInput.clear();
        await updatePage.targetKeyInput.sendKeys('modified');

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
