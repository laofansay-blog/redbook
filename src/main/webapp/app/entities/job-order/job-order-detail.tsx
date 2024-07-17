import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-order.reducer';

export const JobOrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobOrderEntity = useAppSelector(state => state.jobOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobOrderDetailsHeading">
          <Translate contentKey="redBookApp.jobOrder.detail.title">JobOrder</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobOrderEntity.id}</dd>
          <dt>
            <span id="settlementOrderNo">
              <Translate contentKey="redBookApp.jobOrder.settlementOrderNo">Settlement Order No</Translate>
            </span>
            <UncontrolledTooltip target="settlementOrderNo">
              <Translate contentKey="redBookApp.jobOrder.help.settlementOrderNo" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobOrderEntity.settlementOrderNo}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="redBookApp.jobOrder.amount">Amount</Translate>
            </span>
            <UncontrolledTooltip target="amount">
              <Translate contentKey="redBookApp.jobOrder.help.amount" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobOrderEntity.amount}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="redBookApp.jobOrder.paymentStatus">Payment Status</Translate>
            </span>
            <UncontrolledTooltip target="paymentStatus">
              <Translate contentKey="redBookApp.jobOrder.help.paymentStatus" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobOrderEntity.paymentStatus}</dd>
          <dt>
            <span id="settlementDate">
              <Translate contentKey="redBookApp.jobOrder.settlementDate">Settlement Date</Translate>
            </span>
            <UncontrolledTooltip target="settlementDate">
              <Translate contentKey="redBookApp.jobOrder.help.settlementDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobOrderEntity.settlementDate ? (
              <TextFormat value={jobOrderEntity.settlementDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="paymentDate">
              <Translate contentKey="redBookApp.jobOrder.paymentDate">Payment Date</Translate>
            </span>
            <UncontrolledTooltip target="paymentDate">
              <Translate contentKey="redBookApp.jobOrder.help.paymentDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobOrderEntity.paymentDate ? <TextFormat value={jobOrderEntity.paymentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="redBookApp.jobOrder.customerId">Customer Id</Translate>
            </span>
            <UncontrolledTooltip target="customerId">
              <Translate contentKey="redBookApp.jobOrder.help.customerId" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobOrderEntity.customerId}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="redBookApp.jobOrder.channel">Channel</Translate>
            </span>
            <UncontrolledTooltip target="channel">
              <Translate contentKey="redBookApp.jobOrder.help.channel" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobOrderEntity.channel}</dd>
          <dt>
            <Translate contentKey="redBookApp.jobOrder.jobResult">Job Result</Translate>
          </dt>
          <dd>
            {jobOrderEntity.jobResults
              ? jobOrderEntity.jobResults.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {jobOrderEntity.jobResults && i === jobOrderEntity.jobResults.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/job-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-order/${jobOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobOrderDetail;
