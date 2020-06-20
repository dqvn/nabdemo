package com.nab.product.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.nab.product.domain.enumeration.ProductBrand;
import com.nab.product.domain.enumeration.ProductColor;
import com.nab.product.domain.enumeration.Size;
import com.nab.product.domain.enumeration.ProductStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.nab.product.domain.Product} entity. This class is used
 * in {@link com.nab.product.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProductBrand
     */
    public static class ProductBrandFilter extends Filter<ProductBrand> {

        public ProductBrandFilter() {
        }

        public ProductBrandFilter(ProductBrandFilter filter) {
            super(filter);
        }

        @Override
        public ProductBrandFilter copy() {
            return new ProductBrandFilter(this);
        }

    }
    /**
     * Class for filtering ProductColor
     */
    public static class ProductColorFilter extends Filter<ProductColor> {

        public ProductColorFilter() {
        }

        public ProductColorFilter(ProductColorFilter filter) {
            super(filter);
        }

        @Override
        public ProductColorFilter copy() {
            return new ProductColorFilter(this);
        }

    }
    /**
     * Class for filtering Size
     */
    public static class SizeFilter extends Filter<Size> {

        public SizeFilter() {
        }

        public SizeFilter(SizeFilter filter) {
            super(filter);
        }

        @Override
        public SizeFilter copy() {
            return new SizeFilter(this);
        }

    }
    /**
     * Class for filtering ProductStatus
     */
    public static class ProductStatusFilter extends Filter<ProductStatus> {

        public ProductStatusFilter() {
        }

        public ProductStatusFilter(ProductStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProductStatusFilter copy() {
            return new ProductStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ProductBrandFilter brand;

    private BigDecimalFilter price;

    private ProductColorFilter productColor;

    private SizeFilter productSize;

    private ProductStatusFilter status;

    private LongFilter productCategoryId;

    public ProductCriteria() {
    }

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.productColor = other.productColor == null ? null : other.productColor.copy();
        this.productSize = other.productSize == null ? null : other.productSize.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ProductBrandFilter getBrand() {
        return brand;
    }

    public void setBrand(ProductBrandFilter brand) {
        this.brand = brand;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public ProductColorFilter getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColorFilter productColor) {
        this.productColor = productColor;
    }

    public SizeFilter getProductSize() {
        return productSize;
    }

    public void setProductSize(SizeFilter productSize) {
        this.productSize = productSize;
    }

    public ProductStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProductStatusFilter status) {
        this.status = status;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(price, that.price) &&
            Objects.equals(productColor, that.productColor) &&
            Objects.equals(productSize, that.productSize) &&
            Objects.equals(status, that.status) &&
            Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        brand,
        price,
        productColor,
        productSize,
        status,
        productCategoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (brand != null ? "brand=" + brand + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (productColor != null ? "productColor=" + productColor + ", " : "") +
                (productSize != null ? "productSize=" + productSize + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            "}";
    }

}
