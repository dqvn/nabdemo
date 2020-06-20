import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProductOrder } from 'app/shared/model/cart/product-order.model';
import { getEntities as getProductOrders } from 'app/entities/cart/product-order/product-order.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-item.reducer';
import { IOrderItem } from 'app/shared/model/cart/order-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderItemUpdate = (props: IOrderItemUpdateProps) => {
  const [orderId, setOrderId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { orderItemEntity, productOrders, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/order-item' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProductOrders();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...orderItemEntity,
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
          <h2 id="gatewayApp.cartOrderItem.home.createOrEditLabel">
            <Translate contentKey="gatewayApp.cartOrderItem.home.createOrEditLabel">Create or edit a OrderItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : orderItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="order-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="order-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quantityLabel" for="order-item-quantity">
                  <Translate contentKey="gatewayApp.cartOrderItem.quantity">Quantity</Translate>
                </Label>
                <AvField
                  id="order-item-quantity"
                  type="string"
                  className="form-control"
                  name="quantity"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalPriceLabel" for="order-item-totalPrice">
                  <Translate contentKey="gatewayApp.cartOrderItem.totalPrice">Total Price</Translate>
                </Label>
                <AvField
                  id="order-item-totalPrice"
                  type="text"
                  name="totalPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="order-item-status">
                  <Translate contentKey="gatewayApp.cartOrderItem.status">Status</Translate>
                </Label>
                <AvInput
                  id="order-item-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && orderItemEntity.status) || 'AVAILABLE'}
                >
                  <option value="AVAILABLE">{translate('gatewayApp.OrderItemStatus.AVAILABLE')}</option>
                  <option value="OUT_OF_STOCK">{translate('gatewayApp.OrderItemStatus.OUT_OF_STOCK')}</option>
                  <option value="BACK_ORDER">{translate('gatewayApp.OrderItemStatus.BACK_ORDER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="productNameLabel" for="order-item-productName">
                  <Translate contentKey="gatewayApp.cartOrderItem.productName">Product Name</Translate>
                </Label>
                <AvField
                  id="order-item-productName"
                  type="text"
                  name="productName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="order-item-order">
                  <Translate contentKey="gatewayApp.cartOrderItem.order">Order</Translate>
                </Label>
                <AvInput
                  id="order-item-order"
                  type="select"
                  className="form-control"
                  name="order.id"
                  value={isNew ? productOrders[0] && productOrders[0].id : orderItemEntity.order?.id}
                  required
                >
                  {productOrders
                    ? productOrders.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.code}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/order-item" replace color="info">
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
  productOrders: storeState.productOrder.entities,
  orderItemEntity: storeState.orderItem.entity,
  loading: storeState.orderItem.loading,
  updating: storeState.orderItem.updating,
  updateSuccess: storeState.orderItem.updateSuccess,
});

const mapDispatchToProps = {
  getProductOrders,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderItemUpdate);
