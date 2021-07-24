/* tslint:disable no-unused-expression */
import { browser, protractor } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import AnalyzerResultComponentsPage, { AnalyzerResultDeleteDialog } from './analyzer-result.page-object';
import AnalyzerResultUpdatePage from './analyzer-result-update.page-object';
import AnalyzerResultDetailsPage from './analyzer-result-details.page-object';

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

describe('AnalyzerResult e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AnalyzerResultUpdatePage;
  let detailsPage: AnalyzerResultDetailsPage;
  let listPage: AnalyzerResultComponentsPage;
  let deleteDialog: AnalyzerResultDeleteDialog;
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

  it('should load AnalyzerResults', async () => {
    await navBarPage.getEntityPage('analyzer-result');
    listPage = new AnalyzerResultComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create AnalyzerResult page', async () => {
      await listPage.createButton.click();
      updatePage = new AnalyzerResultUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getText()).to.match(/Create or edit a AnalyzerResult/);
    });

    it('should create and save AnalyzerResults', async () => {
      await updatePage.nameInput.sendKeys('name');

      await updatePage.detailInput.sendKeys('detail');

      await updatePage.objectIdInput.sendKeys('5');

      await selectLastOption(updatePage.objectTypeSelect);

      await updatePage.fieldIdInput.sendKeys('5');

      await updatePage.fieldTypeInput.sendKeys('fieldType');

      await updatePage.entityTypeInput.sendKeys('entityType');

      await updatePage.startInput.sendKeys('5');

      await updatePage.endInput.sendKeys('5');

      await updatePage.scoreInput.sendKeys('5');

      await updatePage.recognizerInput.sendKeys('recognizer');

      await updatePage.patternNameInput.sendKeys('patternName');

      await updatePage.patternExprInput.sendKeys('patternExpr');

      await updatePage.originalScoreInput.sendKeys('originalScore');

      await updatePage.textualExplanationInput.sendKeys('textualExplanation');

      await updatePage.supportiveContextWordInput.sendKeys('supportiveContextWord');

      await updatePage.validationResultInput.sendKeys('validationResult');

      await updatePage.creationDateInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      await updatePage.lastUpdatedInput.sendKeys('01/01/2001' + protractor.Key.TAB + '02:30AM');

      // await selectLastOption(updatePage.dataSourceSelect);
      // await selectLastOption(updatePage.fileSourceSelect);

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

        deleteDialog = new AnalyzerResultDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/dataelyAppV2App.analyzerResult.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details AnalyzerResult page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new AnalyzerResultDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit AnalyzerResult page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');

        await updatePage.detailInput.clear();
        await updatePage.detailInput.sendKeys('modified');

        await clear(updatePage.objectIdInput);
        await updatePage.objectIdInput.sendKeys('6');

        await clear(updatePage.fieldIdInput);
        await updatePage.fieldIdInput.sendKeys('6');

        await updatePage.fieldTypeInput.clear();
        await updatePage.fieldTypeInput.sendKeys('modified');

        await updatePage.entityTypeInput.clear();
        await updatePage.entityTypeInput.sendKeys('modified');

        await clear(updatePage.startInput);
        await updatePage.startInput.sendKeys('6');

        await clear(updatePage.endInput);
        await updatePage.endInput.sendKeys('6');

        await clear(updatePage.scoreInput);
        await updatePage.scoreInput.sendKeys('6');

        await updatePage.recognizerInput.clear();
        await updatePage.recognizerInput.sendKeys('modified');

        await updatePage.patternNameInput.clear();
        await updatePage.patternNameInput.sendKeys('modified');

        await updatePage.patternExprInput.clear();
        await updatePage.patternExprInput.sendKeys('modified');

        await updatePage.originalScoreInput.clear();
        await updatePage.originalScoreInput.sendKeys('modified');

        await updatePage.textualExplanationInput.clear();
        await updatePage.textualExplanationInput.sendKeys('modified');

        await updatePage.supportiveContextWordInput.clear();
        await updatePage.supportiveContextWordInput.sendKeys('modified');

        await updatePage.validationResultInput.clear();
        await updatePage.validationResultInput.sendKeys('modified');

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
