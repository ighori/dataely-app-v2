/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import AnalyzerJobComponentsPage, { AnalyzerJobDeleteDialog } from './analyzer-job.page-object';
import AnalyzerJobUpdatePage from './analyzer-job-update.page-object';
import AnalyzerJobDetailsPage from './analyzer-job-details.page-object';

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

describe('AnalyzerJob e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AnalyzerJobUpdatePage;
  let detailsPage: AnalyzerJobDetailsPage;
  let listPage: AnalyzerJobComponentsPage;
  /*let deleteDialog: AnalyzerJobDeleteDialog;*/
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

  it('should load AnalyzerJobs', async () => {
    await navBarPage.getEntityPage('analyzer-job');
    listPage = new AnalyzerJobComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create AnalyzerJob page', async () => {
      await listPage.createButton.click();
      updatePage = new AnalyzerJobUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a AnalyzerJob/);
    });

    /* it('should create and save AnalyzerJobs', async () => {

      await updatePage.nameInput.sendKeys('name');


      await updatePage.detailInput.sendKeys('detail');


      await updatePage.endTimeInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');


      await updatePage.startTimeInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');


      await selectLastOption(updatePage.statusSelect);


      await updatePage.previousRunTimeInput.sendKeys('previousRunTime');


      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');


      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      // await selectLastOption(updatePage.environmentSelect);
      // await selectLastOption(updatePage.dataSourceSelect);
      // await selectLastOption(updatePage.fileSourceSelect);
      // await selectLastOption(updatePage.userSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });*/

    /*
    describe('Details, Update, Delete flow', () => {

      after(async () => {

        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new AnalyzerJobDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.analyzerJob.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details AnalyzerJob page and fetch data', async () => {

        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new AnalyzerJobDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit AnalyzerJob page, fetch data and update', async () => {

        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

          await updatePage.nameInput.clear();
          await updatePage.nameInput.sendKeys('modified');

          await updatePage.detailInput.clear();
          await updatePage.detailInput.sendKeys('modified');

          await updatePage.endTimeInput.clear();
          await updatePage.endTimeInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');

          await updatePage.startTimeInput.clear();
          await updatePage.startTimeInput.sendKeys('01/01/2019' + protractor.Key.TAB + '02:30AM');

          await updatePage.previousRunTimeInput.clear();
          await updatePage.previousRunTimeInput.sendKeys('modified');

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
    */
  });
});
