package com.nab.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.nab.product.domain.enumeration.ProductBrand;

import com.nab.product.domain.enumeration.ProductColor;

import com.nab.product.domain.enumeration.Size;

import com.nab.product.domain.enumeration.ProductStatus;

/**
 * Product sold by the Online store
 */
@ApiModel(description = "Product sold by the Online store")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private ProductBrand brand;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_color", nullable = false)
    private ProductColor productColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_size")
    private Size productSize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductBrand getBrand() {
        return brand;
    }

    public Product brand(ProductBrand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(ProductBrand brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public Product productColor(ProductColor productColor) {
        this.productColor = productColor;
        return this;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public Size getProductSize() {
        return productSize;
    }

    public Product productSize(Size productSize) {
        this.productSize = productSize;
        return this;
    }

    public void setProductSize(Size productSize) {
        this.productSize = productSize;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public Product status(ProductStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Product productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", price=" + getPrice() +
            ", productColor='" + getProductColor() + "'" +
            ", productSize='" + getProductSize() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
