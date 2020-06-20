import { element, by, ElementFinder } from 'protractor';

export default class OrderItemUpdatePage {
  pageTitle: ElementFinder = element(by.id('gatewayApp.cartOrderItem.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  quantityInput: ElementFinder = element(by.css('input#order-item-quantity'));
  totalPriceInput: ElementFinder = element(by.css('input#order-item-totalPrice'));
  statusSelect: ElementFinder = element(by.css('select#order-item-status'));
  productNameInput: ElementFinder = element(by.css('input#order-item-productName'));
  orderSelect: ElementFinder = element(by.css('select#order-item-order'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setQuantityInput(quantity) {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput() {
    return this.quantityInput.getAttribute('value');
  }

  async setTotalPriceInput(totalPrice) {
    await this.totalPriceInput.sendKeys(totalPrice);
  }

  async getTotalPriceInput() {
    return this.totalPriceInput.getAttribute('value');
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
  async setProductNameInput(productName) {
    await this.productNameInput.sendKeys(productName);
  }

  async getProductNameInput() {
    return this.productNameInput.getAttribute('value');
  }

  async orderSelectLastOption() {
    await this.orderSelect.all(by.tagName('option')).last().click();
  }

  async orderSelectOption(option) {
    await this.orderSelect.sendKeys(option);
  }

  getOrderSelect() {
    return this.orderSelect;
  }

  async getOrderSelectedOption() {
    return this.orderSelect.element(by.css('option:checked')).getText();
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
