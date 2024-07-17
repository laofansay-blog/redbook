import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobResult } from 'app/shared/model/job-result.model';
import { getEntities as getJobResults } from 'app/entities/job-result/job-result.reducer';
import { IJobOrder } from 'app/shared/model/job-order.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { getEntity, updateEntity, createEntity, reset } from './job-order.reducer';

export const JobOrderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobResults = useAppSelector(state => state.jobResult.entities);
  const jobOrderEntity = useAppSelector(state => state.jobOrder.entity);
  const loading = useAppSelector(state => state.jobOrder.loading);
  const updating = useAppSelector(state => state.jobOrder.updating);
  const updateSuccess = useAppSelector(state => state.jobOrder.updateSuccess);
  const paymentStatusValues = Object.keys(PaymentStatus);

  const handleClose = () => {
    navigate('/job-order' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobResults({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    values.settlementDate = convertDateTimeToServer(values.settlementDate);
    values.paymentDate = convertDateTimeToServer(values.paymentDate);
    if (values.customerId !== undefined && typeof values.customerId !== 'number') {
      values.customerId = Number(values.customerId);
    }

    const entity = {
      ...jobOrderEntity,
      ...values,
      jobResults: mapIdList(values.jobResults),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          settlementDate: displayDefaultDateTime(),
          paymentDate: displayDefaultDateTime(),
        }
      : {
          paymentStatus: 'WAIT',
          ...jobOrderEntity,
          settlementDate: convertDateTimeFromServer(jobOrderEntity.settlementDate),
          paymentDate: convertDateTimeFromServer(jobOrderEntity.paymentDate),
          jobResults: jobOrderEntity?.jobResults?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.jobOrder.home.createOrEditLabel" data-cy="JobOrderCreateUpdateHeading">
            <Translate contentKey="redBookApp.jobOrder.home.createOrEditLabel">Create or edit a JobOrder</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="job-order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.jobOrder.settlementOrderNo')}
                id="job-order-settlementOrderNo"
                name="settlementOrderNo"
                data-cy="settlementOrderNo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="settlementOrderNoLabel">
                <Translate contentKey="redBookApp.jobOrder.help.settlementOrderNo" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.amount')}
                id="job-order-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="amountLabel">
                <Translate contentKey="redBookApp.jobOrder.help.amount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.paymentStatus')}
                id="job-order-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate('redBookApp.PaymentStatus.' + paymentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="paymentStatusLabel">
                <Translate contentKey="redBookApp.jobOrder.help.paymentStatus" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.settlementDate')}
                id="job-order-settlementDate"
                name="settlementDate"
                data-cy="settlementDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="settlementDateLabel">
                <Translate contentKey="redBookApp.jobOrder.help.settlementDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.paymentDate')}
                id="job-order-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="paymentDateLabel">
                <Translate contentKey="redBookApp.jobOrder.help.paymentDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.customerId')}
                id="job-order-customerId"
                name="customerId"
                data-cy="customerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="customerIdLabel">
                <Translate contentKey="redBookApp.jobOrder.help.customerId" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.channel')}
                id="job-order-channel"
                name="channel"
                data-cy="channel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="channelLabel">
                <Translate contentKey="redBookApp.jobOrder.help.channel" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobOrder.jobResult')}
                id="job-order-jobResult"
                data-cy="jobResult"
                type="select"
                multiple
                name="jobResults"
              >
                <option value="" key="0" />
                {jobResults
                  ? jobResults.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-order" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JobOrderUpdate;
