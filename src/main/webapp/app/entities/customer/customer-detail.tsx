import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer.reducer';

export const CustomerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="redBookApp.customer.detail.title">Customer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="redBookApp.customer.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="redBookApp.customer.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.name}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="redBookApp.customer.mobile">Mobile</Translate>
            </span>
            <UncontrolledTooltip target="mobile">
              <Translate contentKey="redBookApp.customer.help.mobile" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.mobile}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="redBookApp.customer.email">Email</Translate>
            </span>
            <UncontrolledTooltip target="email">
              <Translate contentKey="redBookApp.customer.help.email" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="introduce">
              <Translate contentKey="redBookApp.customer.introduce">Introduce</Translate>
            </span>
            <UncontrolledTooltip target="introduce">
              <Translate contentKey="redBookApp.customer.help.introduce" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.introduce}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="redBookApp.customer.balance">Balance</Translate>
            </span>
            <UncontrolledTooltip target="balance">
              <Translate contentKey="redBookApp.customer.help.balance" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.balance}</dd>
          <dt>
            <span id="times">
              <Translate contentKey="redBookApp.customer.times">Times</Translate>
            </span>
            <UncontrolledTooltip target="times">
              <Translate contentKey="redBookApp.customer.help.times" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.times}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="redBookApp.customer.status">Status</Translate>
            </span>
            <UncontrolledTooltip target="status">
              <Translate contentKey="redBookApp.customer.help.status" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerEntity.status}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="redBookApp.customer.createdDate">Created Date</Translate>
            </span>
            <UncontrolledTooltip target="createdDate">
              <Translate contentKey="redBookApp.customer.help.createdDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {customerEntity.createdDate ? <TextFormat value={customerEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="redBookApp.customer.channels">Channels</Translate>
          </dt>
          <dd>{customerEntity.channels ? customerEntity.channels.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
