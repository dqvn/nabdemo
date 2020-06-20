import { element, by, ElementFinder } from 'protractor';

export default class ProductUpdatePage {
  pageTitle: ElementFinder = element(by.id('gatewayApp.productProduct.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#product-name'));
  brandSelect: ElementFinder = element(by.css('select#product-brand'));
  priceInput: ElementFinder = element(by.css('input#product-price'));
  productColorSelect: ElementFinder = element(by.css('select#product-productColor'));
  productSizeSelect: ElementFinder = element(by.css('select#product-productSize'));
  statusSelect: ElementFinder = element(by.css('select#product-status'));
  productCategorySelect: ElementFinder = element(by.css('select#product-productCategory'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setBrandSelect(brand) {
    await this.brandSelect.sendKeys(brand);
  }

  async getBrandSelect() {
    return this.brandSelect.element(by.css('option:checked')).getText();
  }

  async brandSelectLastOption() {
    await this.brandSelect.all(by.tagName('option')).last().click();
  }
  async setPriceInput(price) {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput() {
    return this.priceInput.getAttribute('value');
  }

  async setProductColorSelect(productColor) {
    await this.productColorSelect.sendKeys(productColor);
  }

  async getProductColorSelect() {
    return this.productColorSelect.element(by.css('option:checked')).getText();
  }

  async productColorSelectLastOption() {
    await this.productColorSelect.all(by.tagName('option')).last().click();
  }
  async setProductSizeSelect(productSize) {
    await this.productSizeSelect.sendKeys(productSize);
  }

  async getProductSizeSelect() {
    return this.productSizeSelect.element(by.css('option:checked')).getText();
  }

  async productSizeSelectLastOption() {
    await this.productSizeSelect.all(by.tagName('option')).last().click();
  }
  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }
  async productCategorySelectLastOption() {
    await this.productCategorySelect.all(by.tagName('option')).last().click();
  }

  async productCategorySelectOption(option) {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect() {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption() {
    return this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
