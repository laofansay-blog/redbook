import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { ICstJob } from 'app/shared/model/cst-job.model';
import { ExecuteType } from 'app/shared/model/enumerations/execute-type.model';
import { JobStatus } from 'app/shared/model/enumerations/job-status.model';
import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';
import { getEntity, updateEntity, createEntity, reset } from './cst-job.reducer';

export const CstJobUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const cstJobEntity = useAppSelector(state => state.cstJob.entity);
  const loading = useAppSelector(state => state.cstJob.loading);
  const updating = useAppSelector(state => state.cstJob.updating);
  const updateSuccess = useAppSelector(state => state.cstJob.updateSuccess);
  const executeTypeValues = Object.keys(ExecuteType);
  const jobStatusValues = Object.keys(JobStatus);
  const channelCateValues = Object.keys(ChannelCate);

  const handleClose = () => {
    navigate('/cst-job' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    values.createdDate = convertDateTimeToServer(values.createdDate);

    const entity = {
      ...cstJobEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
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
          createdDate: displayDefaultDateTime(),
        }
      : {
          executeType: 'ONCE',
          status: 'OVER',
          channel: 'SmallRedBook',
          ...cstJobEntity,
          createdDate: convertDateTimeFromServer(cstJobEntity.createdDate),
          customer: cstJobEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.cstJob.home.createOrEditLabel" data-cy="CstJobCreateUpdateHeading">
            <Translate contentKey="redBookApp.cstJob.home.createOrEditLabel">Create or edit a CstJob</Translate>
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
                  id="cst-job-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.cstJob.name')}
                id="cst-job-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="redBookApp.cstJob.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.executeType')}
                id="cst-job-executeType"
                name="executeType"
                data-cy="executeType"
                type="select"
              >
                {executeTypeValues.map(executeType => (
                  <option value={executeType} key={executeType}>
                    {translate('redBookApp.ExecuteType.' + executeType)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="executeTypeLabel">
                <Translate contentKey="redBookApp.cstJob.help.executeType" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.category')}
                id="cst-job-category"
                name="category"
                data-cy="category"
                type="text"
              />
              <UncontrolledTooltip target="categoryLabel">
                <Translate contentKey="redBookApp.cstJob.help.category" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.status')}
                id="cst-job-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {jobStatusValues.map(jobStatus => (
                  <option value={jobStatus} key={jobStatus}>
                    {translate('redBookApp.JobStatus.' + jobStatus)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="statusLabel">
                <Translate contentKey="redBookApp.cstJob.help.status" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.createdDate')}
                id="cst-job-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="createdDateLabel">
                <Translate contentKey="redBookApp.cstJob.help.createdDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.jobProps')}
                id="cst-job-jobProps"
                name="jobProps"
                data-cy="jobProps"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="jobPropsLabel">
                <Translate contentKey="redBookApp.cstJob.help.jobProps" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstJob.channel')}
                id="cst-job-channel"
                name="channel"
                data-cy="channel"
                type="select"
              >
                {channelCateValues.map(channelCate => (
                  <option value={channelCate} key={channelCate}>
                    {translate('redBookApp.ChannelCate.' + channelCate)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="channelLabel">
                <Translate contentKey="redBookApp.cstJob.help.channel" />
              </UncontrolledTooltip>
              <ValidatedField
                id="cst-job-customer"
                name="customer"
                data-cy="customer"
                label={translate('redBookApp.cstJob.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cst-job" replace color="info">
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

export default CstJobUpdate;
