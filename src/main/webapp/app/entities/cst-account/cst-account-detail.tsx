import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cst-account.reducer';

export const CstAccountDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cstAccountEntity = useAppSelector(state => state.cstAccount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cstAccountDetailsHeading">
          <Translate contentKey="redBookApp.cstAccount.detail.title">CstAccount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cstAccountEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="redBookApp.cstAccount.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="redBookApp.cstAccount.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.name}</dd>
          <dt>
            <span id="provider">
              <Translate contentKey="redBookApp.cstAccount.provider">Provider</Translate>
            </span>
            <UncontrolledTooltip target="provider">
              <Translate contentKey="redBookApp.cstAccount.help.provider" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.provider}</dd>
          <dt>
            <span id="rbAccount">
              <Translate contentKey="redBookApp.cstAccount.rbAccount">Rb Account</Translate>
            </span>
            <UncontrolledTooltip target="rbAccount">
              <Translate contentKey="redBookApp.cstAccount.help.rbAccount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.rbAccount}</dd>
          <dt>
            <span id="rbPwd">
              <Translate contentKey="redBookApp.cstAccount.rbPwd">Rb Pwd</Translate>
            </span>
            <UncontrolledTooltip target="rbPwd">
              <Translate contentKey="redBookApp.cstAccount.help.rbPwd" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.rbPwd}</dd>
          <dt>
            <span id="rbToken">
              <Translate contentKey="redBookApp.cstAccount.rbToken">Rb Token</Translate>
            </span>
            <UncontrolledTooltip target="rbToken">
              <Translate contentKey="redBookApp.cstAccount.help.rbToken" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.rbToken}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="redBookApp.cstAccount.status">Status</Translate>
            </span>
            <UncontrolledTooltip target="status">
              <Translate contentKey="redBookApp.cstAccount.help.status" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.status}</dd>
          <dt>
            <span id="timesByDay">
              <Translate contentKey="redBookApp.cstAccount.timesByDay">Times By Day</Translate>
            </span>
            <UncontrolledTooltip target="timesByDay">
              <Translate contentKey="redBookApp.cstAccount.help.timesByDay" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.timesByDay}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="redBookApp.cstAccount.createdDate">Created Date</Translate>
            </span>
            <UncontrolledTooltip target="createdDate">
              <Translate contentKey="redBookApp.cstAccount.help.createdDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {cstAccountEntity.createdDate ? <TextFormat value={cstAccountEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="channel">
              <Translate contentKey="redBookApp.cstAccount.channel">Channel</Translate>
            </span>
            <UncontrolledTooltip target="channel">
              <Translate contentKey="redBookApp.cstAccount.help.channel" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstAccountEntity.channel}</dd>
          <dt>
            <Translate contentKey="redBookApp.cstAccount.customer">Customer</Translate>
          </dt>
          <dd>{cstAccountEntity.customer ? cstAccountEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cst-account" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cst-account/${cstAccountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CstAccountDetail;
