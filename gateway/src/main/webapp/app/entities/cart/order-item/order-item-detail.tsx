import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-item.reducer';
import { IOrderItem } from 'app/shared/model/cart/order-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderItemDetail = (props: IOrderItemDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderItemEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gatewayApp.cartOrderItem.detail.title">OrderItem</Translate> [<b>{orderItemEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="quantity">
              <Translate contentKey="gatewayApp.cartOrderItem.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.quantity}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="gatewayApp.cartOrderItem.totalPrice">Total Price</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.totalPrice}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="gatewayApp.cartOrderItem.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.status}</dd>
          <dt>
            <span id="productName">
              <Translate contentKey="gatewayApp.cartOrderItem.productName">Product Name</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.productName}</dd>
          <dt>
            <Translate contentKey="gatewayApp.cartOrderItem.order">Order</Translate>
          </dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orderItem }: IRootState) => ({
  orderItemEntity: orderItem.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderItemDetail);
