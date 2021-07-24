/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import AnalyzerAdHocRecognizersComponentsPage, { AnalyzerAdHocRecognizersDeleteDialog } from './analyzer-ad-hoc-recognizers.page-object';
import AnalyzerAdHocRecognizersUpdatePage from './analyzer-ad-hoc-recognizers-update.page-object';
import AnalyzerAdHocRecognizersDetailsPage from './analyzer-ad-hoc-recognizers-details.page-object';

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

describe('AnalyzerAdHocRecognizers e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AnalyzerAdHocRecognizersUpdatePage;
  let detailsPage: AnalyzerAdHocRecognizersDetailsPage;
  let listPage: AnalyzerAdHocRecognizersComponentsPage;
  let deleteDialog: AnalyzerAdHocRecognizersDeleteDialog;
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

  it('should load AnalyzerAdHocRecognizers', async () => {
    await navBarPage.getEntityPage('analyzer-ad-hoc-recognizers');
    listPage = new AnalyzerAdHocRecognizersComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create AnalyzerAdHocRecognizers page', async () => {
      await listPage.createButton.click();
      updatePage = new AnalyzerAdHocRecognizersUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a AnalyzerAdHocRecognizers/);
    });

    it('should create and save AnalyzerAdHocRecognizers', async () => {
      await updatePage.nameInput.sendKeys('name');

      await updatePage.detailInput.sendKeys('detail');

      await updatePage.recognizerNameInput.sendKeys('recognizerName');

      await updatePage.supportedLangInput.sendKeys('supportedLang');

      await updatePage.patternNameInput.sendKeys('patternName');

      await updatePage.regexInput.sendKeys('regex');

      await updatePage.scoreInput.sendKeys('5');

      await updatePage.contextInput.sendKeys('context');

      await updatePage.denyListInput.sendKeys('denyList');

      await updatePage.supportedEntityInput.sendKeys('supportedEntity');

      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

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

        deleteDialog = new AnalyzerAdHocRecognizersDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.analyzerAdHocRecognizers.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details AnalyzerAdHocRecognizers page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new AnalyzerAdHocRecognizersDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit AnalyzerAdHocRecognizers page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');

        await updatePage.detailInput.clear();
        await updatePage.detailInput.sendKeys('modified');

        await updatePage.recognizerNameInput.clear();
        await updatePage.recognizerNameInput.sendKeys('modified');

        await updatePage.supportedLangInput.clear();
        await updatePage.supportedLangInput.sendKeys('modified');

        await updatePage.patternNameInput.clear();
        await updatePage.patternNameInput.sendKeys('modified');

        await updatePage.regexInput.clear();
        await updatePage.regexInput.sendKeys('modified');

        await clear(updatePage.scoreInput);
        await updatePage.scoreInput.sendKeys('6');

        await updatePage.contextInput.clear();
        await updatePage.contextInput.sendKeys('modified');

        await updatePage.denyListInput.clear();
        await updatePage.denyListInput.sendKeys('modified');

        await updatePage.supportedEntityInput.clear();
        await updatePage.supportedEntityInput.sendKeys('modified');

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
