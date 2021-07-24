/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import SignInPage from '../../page-objects/signin-page';
import NavBarPage from '../../page-objects/navbar-page';
import RegisterPage from '../../page-objects/register-page';
import PasswordPage from '../../page-objects/password-page';
import SettingsPage from '../../page-objects/settings-page';
import { UserManagementDeletePage, UserManagementDetailsPage, UserManagementPage } from '../../page-objects/administration-page';
import { isVisible, waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Account', () => {
  let navBarPage: NavBarPage = new NavBarPage();
  let signInPage: SignInPage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';
  let passwordPage: PasswordPage;
  let settingsPage: SettingsPage;
  let registerPage: RegisterPage;
  let userManagementPage: UserManagementPage;

  const registerPageTitle = 'register-title';
  const passwordPageTitle = 'password-title';
  const settingsPageTitle = 'settings-title';
  const loginPageTitle = 'login-title';

  describe('Login Flow', () => {
    before(async () => {
      await browser.get('/');
      navBarPage = new NavBarPage();
    });

    after(async () => {
      await navBarPage.autoSignOut();
    });

    it('should fail to login with bad password', async () => {
      signInPage = await navBarPage.getSignInPage();
      expect(await signInPage.getTitle()).to.eq(loginPageTitle);
      expect(await isVisible(signInPage.dangerAlert)).to.be.false;

      await signInPage.login(username, 'foo');

      await waitUntilDisplayed(signInPage.dangerAlert);
      expect(await signInPage.dangerAlert.isDisplayed()).to.be.true;

      await signInPage.closeButton.click();
      await waitUntilHidden(signInPage.loginForm);
    });

    it('should login with admin account', async () => {
      signInPage = await navBarPage.getSignInPage();
      expect(await signInPage.getTitle()).to.eq(loginPageTitle);
      expect(await isVisible(signInPage.dangerAlert)).to.be.false;

      await signInPage.username.clear();
      await signInPage.username.sendKeys(username);
      await signInPage.password.clear();
      await signInPage.password.sendKeys(password);
      await signInPage.loginButton.click();

      await waitUntilHidden(signInPage.loginForm);
      await waitUntilDisplayed(navBarPage.entityMenu);

      expect(await navBarPage.entityMenu.isDisplayed()).to.be.true;
    });
  });

  describe('User Registration Flow', () => {
    before(async () => {
      await browser.get('/');
      navBarPage = new NavBarPage();
    });

    after(async () => {
      await navBarPage.autoSignOut();
    });

    it('should be able to sign up', async () => {
      registerPage = await navBarPage.getRegisterPage();

      expect(await registerPage.getTitle()).to.eq(registerPageTitle);

      await registerPage.autoSignUpUsing('user_test', 'admin@localhost.jh', 'user_test');

      await waitUntilDisplayed(registerPage.successAlert);

      expect(await registerPage.successAlert.isDisplayed()).to.be.true;
    });

    it('should load user management', async () => {
      signInPage = await navBarPage.getSignInPage();
      expect(await signInPage.getTitle()).to.eq(loginPageTitle);

      await signInPage.username.sendKeys(username);
      await signInPage.password.sendKeys(password);
      await signInPage.loginButton.click();

      await waitUntilHidden(signInPage.loginForm);
      await waitUntilDisplayed(navBarPage.adminMenu);

      userManagementPage = await navBarPage.getUserManagementPage();
      expect(await userManagementPage.title.isDisplayed()).to.be.true;
    });

    it('should activate the new registered user', async () => {
      userManagementPage = await navBarPage.getUserManagementPage();
      expect(await userManagementPage.title.isDisplayed()).to.be.true;

      await userManagementPage.activateUser('user_test');

      expect(await userManagementPage.activatedButton('user_test').isDisplayed()).to.be.true;
      await navBarPage.autoSignOut();
    });

    it('should not be able to sign up if login already taken', async () => {
      await browser.get('/');
      registerPage = await navBarPage.getRegisterPage();
      expect(await registerPage.getTitle()).to.eq(registerPageTitle);

      await registerPage.autoSignUpUsing('user_test', 'admin@localhost.jh', 'user_test');

      await waitUntilDisplayed(registerPage.dangerAlert);
      expect(await registerPage.dangerAlert.isDisplayed()).to.be.true;
    });

    it('should not be able to sign up if email already taken', async () => {
      registerPage = await navBarPage.getRegisterPage();
      expect(await registerPage.getTitle()).to.eq(registerPageTitle);

      await registerPage.username.sendKeys('_jhi');
      await registerPage.saveButton.click();

      await waitUntilDisplayed(registerPage.dangerAlert);
      expect(await registerPage.dangerAlert.isDisplayed()).to.be.true;
    });

    it('should be able to log in with new registered account', async () => {
      signInPage = await navBarPage.getSignInPage();
      expect(await signInPage.getTitle()).to.eq(loginPageTitle);

      await signInPage.username.sendKeys('user_test');
      await signInPage.password.sendKeys('user_test');
      await signInPage.loginButton.click();

      await waitUntilHidden(signInPage.loginForm);
      await waitUntilDisplayed(navBarPage.entityMenu);

      // Login page should close when login success
      expect(await isVisible(signInPage.loginForm)).to.be.false;
      await navBarPage.autoSignOut();
    });

    describe('Change Password Flow', () => {
      before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
      });

      after(async () => {
        await navBarPage.autoSignOut();
      });

      it('should login with admin account', async () => {
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys(username);
        await signInPage.password.sendKeys(password);
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.adminMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;
      });

      it('should fail to update password when using incorrect current password', async () => {
        passwordPage = await navBarPage.getPasswordPage();
        expect(await passwordPage.getTitle()).to.eq(passwordPageTitle);

        await passwordPage.autoChangePassword('bad_password', 'new_password', 'new_password');

        await waitUntilDisplayed(passwordPage.dangerAlert);
        expect(await passwordPage.dangerAlert.isDisplayed()).to.be.true;

        await navBarPage.autoSignOut();
      });

      it('should login with admin account', async () => {
        await browser.get('/');
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys(username);
        await signInPage.password.sendKeys(password);
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.adminMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;
      });

      it('should be able to update password', async () => {
        passwordPage = await navBarPage.getPasswordPage();
        expect(await passwordPage.getTitle()).to.eq(passwordPageTitle);

        await passwordPage.autoChangePassword(password, 'new_password', 'new_password');

        await waitUntilDisplayed(passwordPage.successAlert);
        expect(await passwordPage.successAlert.isDisplayed()).to.be.true;

        await navBarPage.autoSignOut();
      });

      it('should be able to log in with new password', async () => {
        await browser.get('/');
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys(username);
        await signInPage.password.sendKeys('new_password');
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.adminMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;

        // change back to default
        passwordPage = await navBarPage.getPasswordPage();
        expect(await passwordPage.getTitle()).to.eq(passwordPageTitle);
        await passwordPage.autoChangePassword('new_password', password, password);

        await waitUntilDisplayed(passwordPage.successAlert);
        expect(await passwordPage.successAlert.isDisplayed()).to.be.true;
      });
    });

    describe('User settings Flow', () => {
      before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
      });

      after(async () => {
        await navBarPage.autoSignOut();
      });

      it('should login with user_test account', async () => {
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys('user_test');
        await signInPage.password.sendKeys('user_test');
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.entityMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;
      });

      it('should be able to change user_test settings', async () => {
        settingsPage = await navBarPage.getSettingsPage();
        expect(await settingsPage.getTitle()).to.eq(settingsPageTitle);

        await settingsPage.firstName.sendKeys('jhipster');
        await settingsPage.lastName.sendKeys('retspihj');
        await settingsPage.saveButton.click();

        await waitUntilDisplayed(settingsPage.successAlert);
        expect(await settingsPage.successAlert.isDisplayed()).to.be.true;

        await navBarPage.autoSignOut();
      });

      it('should login with admin account', async () => {
        await browser.get('/');
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys(username);
        await signInPage.password.sendKeys(password);
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.adminMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;
      });

      it('should not be able to change admin settings if email already exists', async () => {
        settingsPage = await navBarPage.getSettingsPage();
        expect(await settingsPage.getTitle()).to.eq(settingsPageTitle);

        await settingsPage.email.sendKeys('.jh');
        await settingsPage.saveButton.click();

        await waitUntilDisplayed(settingsPage.dangerAlert);
        expect(await settingsPage.dangerAlert.isDisplayed()).to.be.true;
      });
    });

    describe('User management Flow', () => {
      before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
      });

      it('should login with admin account', async () => {
        signInPage = await navBarPage.getSignInPage();
        expect(await signInPage.getTitle()).to.eq(loginPageTitle);

        await signInPage.username.sendKeys(username);
        await signInPage.password.sendKeys(password);
        await signInPage.loginButton.click();

        await waitUntilHidden(signInPage.loginForm);
        await waitUntilDisplayed(navBarPage.adminMenu);

        expect(await isVisible(signInPage.loginForm)).to.be.false;
      });

      it('should preview details from previously created fake user', async () => {
        userManagementPage = await navBarPage.getUserManagementPage();
        const detailsPage: UserManagementDetailsPage = await userManagementPage.getDetailsPage('user_test');
        expect(await detailsPage.loginDetails.getText()).to.eq('user_test');
      });

      it('should edit details from previously created fake user', async () => {
        userManagementPage = await navBarPage.getUserManagementPage();

        const detailsPage: UserManagementDetailsPage = await userManagementPage.getEditPage('user_test');

        await detailsPage.email.sendKeys('ipster');
        await detailsPage.saveBtn.click();

        expect(await userManagementPage.getEmailColumnText('user_test')).to.eq('admin@localhost.jhipster');
      });

      it('should delete previously created fake user', async () => {
        userManagementPage = await navBarPage.getUserManagementPage();

        const deletePage: UserManagementDeletePage = await userManagementPage.getDeletePage('user_test');

        await deletePage.okButton.click();

        await waitUntilHidden(deletePage.modal);

        expect(await isVisible(deletePage.modal)).to.be.false;
      });
    });
  });
});
