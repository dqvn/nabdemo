import { IProductOrder } from 'app/shared/model/cart/product-order.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';

export interface IOrderItem {
  id?: number;
  quantity?: number;
  totalPrice?: number;
  status?: OrderItemStatus;
  productName?: string;
  order?: IProductOrder;
}

export const defaultValue: Readonly<IOrderItem> = {};
