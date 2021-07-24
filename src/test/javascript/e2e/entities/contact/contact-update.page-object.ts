import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ContactUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('dataelyAppV2App.contact.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  firstNameInput: ElementFinder = element(by.css('input#contact-firstName'));

  lastNameInput: ElementFinder = element(by.css('input#contact-lastName'));

  nameInput: ElementFinder = element(by.css('input#contact-name'));

  roleSelect = element(by.css('select#contact-role'));

  emailInput: ElementFinder = element(by.css('input#contact-email'));

  phoneInput: ElementFinder = element(by.css('input#contact-phone'));

  extensionInput: ElementFinder = element(by.css('input#contact-extension'));

  mobileInput: ElementFinder = element(by.css('input#contact-mobile'));

  locationInput: ElementFinder = element(by.css('input#contact-location'));

  addressLine1Input: ElementFinder = element(by.css('input#contact-addressLine1'));

  addressLine2Input: ElementFinder = element(by.css('input#contact-addressLine2'));

  cityInput: ElementFinder = element(by.css('input#contact-city'));

  countryInput: ElementFinder = element(by.css('input#contact-country'));

  imageInput: ElementFinder = element(by.css('input#file_image'));
}
