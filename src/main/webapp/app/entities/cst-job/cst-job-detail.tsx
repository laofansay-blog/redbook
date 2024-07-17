import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cst-job.reducer';

export const CstJobDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cstJobEntity = useAppSelector(state => state.cstJob.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cstJobDetailsHeading">
          <Translate contentKey="redBookApp.cstJob.detail.title">CstJob</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cstJobEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="redBookApp.cstJob.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="redBookApp.cstJob.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.name}</dd>
          <dt>
            <span id="executeType">
              <Translate contentKey="redBookApp.cstJob.executeType">Execute Type</Translate>
            </span>
            <UncontrolledTooltip target="executeType">
              <Translate contentKey="redBookApp.cstJob.help.executeType" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.executeType}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="redBookApp.cstJob.category">Category</Translate>
            </span>
            <UncontrolledTooltip target="category">
              <Translate contentKey="redBookApp.cstJob.help.category" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.category}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="redBookApp.cstJob.status">Status</Translate>
            </span>
            <UncontrolledTooltip target="status">
              <Translate contentKey="redBookApp.cstJob.help.status" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.status}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="redBookApp.cstJob.createdDate">Created Date</Translate>
            </span>
            <UncontrolledTooltip target="createdDate">
              <Translate contentKey="redBookApp.cstJob.help.createdDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.createdDate ? <TextFormat value={cstJobEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="jobProps">
              <Translate contentKey="redBookApp.cstJob.jobProps">Job Props</Translate>
            </span>
            <UncontrolledTooltip target="jobProps">
              <Translate contentKey="redBookApp.cstJob.help.jobProps" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.jobProps}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="redBookApp.cstJob.channel">Channel</Translate>
            </span>
            <UncontrolledTooltip target="channel">
              <Translate contentKey="redBookApp.cstJob.help.channel" />
            </UncontrolledTooltip>
          </dt>
          <dd>{cstJobEntity.channel}</dd>
          <dt>
            <Translate contentKey="redBookApp.cstJob.customer">Customer</Translate>
          </dt>
          <dd>{cstJobEntity.customer ? cstJobEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cst-job" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cst-job/${cstJobEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CstJobDetail;
