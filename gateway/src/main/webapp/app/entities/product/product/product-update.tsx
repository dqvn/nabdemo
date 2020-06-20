import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductCategory } from 'app/shared/model/product/product-category.model';
import { getEntities as getProductCategories } from 'app/entities/product/product-category/product-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';
import { IProduct } from 'app/shared/model/product/product.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductUpdate = (props: IProductUpdateProps) => {
  const [productCategoryId, setProductCategoryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { productEntity, productCategories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/product' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gatewayApp.productProduct.home.createOrEditLabel">
            <Translate contentKey="gatewayApp.productProduct.home.createOrEditLabel">Create or edit a Product</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="product-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="product-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="product-name">
                  <Translate contentKey="gatewayApp.productProduct.name">Name</Translate>
                </Label>
                <AvField
                  id="product-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="brandLabel" for="product-brand">
                  <Translate contentKey="gatewayApp.productProduct.brand">Brand</Translate>
                </Label>
                <AvInput
                  id="product-brand"
                  type="select"
                  className="form-control"
                  name="brand"
                  value={(!isNew && productEntity.brand) || 'JUNO'}
                >
                  <option value="JUNO">{translate('gatewayApp.ProductBrand.JUNO')}</option>
                  <option value="LV">{translate('gatewayApp.ProductBrand.LV')}</option>
                  <option value="KAFPA">{translate('gatewayApp.ProductBrand.KAFPA')}</option>
                  <option value="CROSS">{translate('gatewayApp.ProductBrand.CROSS')}</option>
                  <option value="NOUS">{translate('gatewayApp.ProductBrand.NOUS')}</option>
                  <option value="JK">{translate('gatewayApp.ProductBrand.JK')}</option>
                  <option value="ADV">{translate('gatewayApp.ProductBrand.ADV')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="product-price">
                  <Translate contentKey="gatewayApp.productProduct.price">Price</Translate>
                </Label>
                <AvField
                  id="product-price"
                  type="text"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="productColorLabel" for="product-productColor">
                  <Translate contentKey="gatewayApp.productProduct.productColor">Product Color</Translate>
                </Label>
                <AvInput
                  id="product-productColor"
                  type="select"
                  className="form-control"
                  name="productColor"
                  value={(!isNew && productEntity.productColor) || 'RED'}
                >
                  <option value="RED">{translate('gatewayApp.ProductColor.RED')}</option>
                  <option value="GREEN">{translate('gatewayApp.ProductColor.GREEN')}</option>
                  <option value="YELLOW">{translate('gatewayApp.ProductColor.YELLOW')}</option>
                  <option value="GREY">{translate('gatewayApp.ProductColor.GREY')}</option>
                  <option value="WHITE">{translate('gatewayApp.ProductColor.WHITE')}</option>
                  <option value="ORANGE">{translate('gatewayApp.ProductColor.ORANGE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="productSizeLabel" for="product-productSize">
                  <Translate contentKey="gatewayApp.productProduct.productSize">Product Size</Translate>
                </Label>
                <AvInput
                  id="product-productSize"
                  type="select"
                  className="form-control"
                  name="productSize"
                  value={(!isNew && productEntity.productSize) || 'S'}
                >
                  <option value="S">{translate('gatewayApp.Size.S')}</option>
                  <option value="M">{translate('gatewayApp.Size.M')}</option>
                  <option value="L">{translate('gatewayApp.Size.L')}</option>
                  <option value="XL">{translate('gatewayApp.Size.XL')}</option>
                  <option value="XXL">{translate('gatewayApp.Size.XXL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="product-status">
                  <Translate contentKey="gatewayApp.productProduct.status">Status</Translate>
                </Label>
                <AvInput
                  id="product-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && productEntity.status) || 'CURRENT'}
                >
                  <option value="CURRENT">{translate('gatewayApp.ProductStatus.CURRENT')}</option>
                  <option value="OUTDATE">{translate('gatewayApp.ProductStatus.OUTDATE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="product-productCategory">
                  <Translate contentKey="gatewayApp.productProduct.productCategory">Product Category</Translate>
                </Label>
                <AvInput id="product-productCategory" type="select" className="form-control" name="productCategory.id">
                  <option value="" key="0" />
                  {productCategories
                    ? productCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/product" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  productCategories: storeState.productCategory.entities,
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess,
});

const mapDispatchToProps = {
  getProductCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductUpdate);
