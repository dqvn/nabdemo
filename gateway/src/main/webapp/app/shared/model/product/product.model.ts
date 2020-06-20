import { IProductCategory } from 'app/shared/model/product/product-category.model';
import { ProductBrand } from 'app/shared/model/enumerations/product-brand.model';
import { ProductColor } from 'app/shared/model/enumerations/product-color.model';
import { Size } from 'app/shared/model/enumerations/size.model';
import { ProductStatus } from 'app/shared/model/enumerations/product-status.model';

export interface IProduct {
  id?: number;
  name?: string;
  brand?: ProductBrand;
  price?: number;
  productColor?: ProductColor;
  productSize?: Size;
  status?: ProductStatus;
  productCategory?: IProductCategory;
}

export const defaultValue: Readonly<IProduct> = {};
