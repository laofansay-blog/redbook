import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICstJob } from 'app/shared/model/cst-job.model';
import { getEntities as getCstJobs } from 'app/entities/cst-job/cst-job.reducer';
import { IJobResult } from 'app/shared/model/job-result.model';
import { JobStatus } from 'app/shared/model/enumerations/job-status.model';
import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';
import { getEntity, updateEntity, createEntity, reset } from './job-result.reducer';

export const JobResultUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cstJobs = useAppSelector(state => state.cstJob.entities);
  const jobResultEntity = useAppSelector(state => state.jobResult.entity);
  const loading = useAppSelector(state => state.jobResult.loading);
  const updating = useAppSelector(state => state.jobResult.updating);
  const updateSuccess = useAppSelector(state => state.jobResult.updateSuccess);
  const jobStatusValues = Object.keys(JobStatus);
  const channelCateValues = Object.keys(ChannelCate);

  const handleClose = () => {
    navigate('/job-result' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCstJobs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    values.builderDate = convertDateTimeToServer(values.builderDate);
    values.replayDate = convertDateTimeToServer(values.replayDate);
    values.settlementDate = convertDateTimeToServer(values.settlementDate);

    const entity = {
      ...jobResultEntity,
      ...values,
      cstJob: cstJobs.find(it => it.id.toString() === values.cstJob?.toString()),
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
          builderDate: displayDefaultDateTime(),
          replayDate: displayDefaultDateTime(),
          settlementDate: displayDefaultDateTime(),
        }
      : {
          status: 'OVER',
          channel: 'SmallRedBook',
          ...jobResultEntity,
          builderDate: convertDateTimeFromServer(jobResultEntity.builderDate),
          replayDate: convertDateTimeFromServer(jobResultEntity.replayDate),
          settlementDate: convertDateTimeFromServer(jobResultEntity.settlementDate),
          cstJob: jobResultEntity?.cstJob?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.jobResult.home.createOrEditLabel" data-cy="JobResultCreateUpdateHeading">
            <Translate contentKey="redBookApp.jobResult.home.createOrEditLabel">Create or edit a JobResult</Translate>
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
                  id="job-result-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.jobResult.name')}
                id="job-result-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="redBookApp.jobResult.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.jobUrl')}
                id="job-result-jobUrl"
                name="jobUrl"
                data-cy="jobUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="jobUrlLabel">
                <Translate contentKey="redBookApp.jobResult.help.jobUrl" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.authorName')}
                id="job-result-authorName"
                name="authorName"
                data-cy="authorName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="authorNameLabel">
                <Translate contentKey="redBookApp.jobResult.help.authorName" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.accountId')}
                id="job-result-accountId"
                name="accountId"
                data-cy="accountId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="accountIdLabel">
                <Translate contentKey="redBookApp.jobResult.help.accountId" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.customerId')}
                id="job-result-customerId"
                name="customerId"
                data-cy="customerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="customerIdLabel">
                <Translate contentKey="redBookApp.jobResult.help.customerId" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.status')}
                id="job-result-status"
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
                <Translate contentKey="redBookApp.jobResult.help.status" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.jobDate')}
                id="job-result-jobDate"
                name="jobDate"
                data-cy="jobDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="jobDateLabel">
                <Translate contentKey="redBookApp.jobResult.help.jobDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.jobNo')}
                id="job-result-jobNo"
                name="jobNo"
                data-cy="jobNo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="jobNoLabel">
                <Translate contentKey="redBookApp.jobResult.help.jobNo" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.builderDate')}
                id="job-result-builderDate"
                name="builderDate"
                data-cy="builderDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="builderDateLabel">
                <Translate contentKey="redBookApp.jobResult.help.builderDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.replay')}
                id="job-result-replay"
                name="replay"
                data-cy="replay"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="replayLabel">
                <Translate contentKey="redBookApp.jobResult.help.replay" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.replayTheme')}
                id="job-result-replayTheme"
                name="replayTheme"
                data-cy="replayTheme"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="replayThemeLabel">
                <Translate contentKey="redBookApp.jobResult.help.replayTheme" />
              </UncontrolledTooltip>
              <ValidatedBlobField
                label={translate('redBookApp.jobResult.replayImage')}
                id="job-result-replayImage"
                name="replayImage"
                data-cy="replayImage"
                isImage
                accept="image/*"
              />
              <UncontrolledTooltip target="replayImageLabel">
                <Translate contentKey="redBookApp.jobResult.help.replayImage" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.replayDate')}
                id="job-result-replayDate"
                name="replayDate"
                data-cy="replayDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="replayDateLabel">
                <Translate contentKey="redBookApp.jobResult.help.replayDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.effReplay')}
                id="job-result-effReplay"
                name="effReplay"
                data-cy="effReplay"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="effReplayLabel">
                <Translate contentKey="redBookApp.jobResult.help.effReplay" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.settlement')}
                id="job-result-settlement"
                name="settlement"
                data-cy="settlement"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="settlementLabel">
                <Translate contentKey="redBookApp.jobResult.help.settlement" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.settlementOrder')}
                id="job-result-settlementOrder"
                name="settlementOrder"
                data-cy="settlementOrder"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="settlementOrderLabel">
                <Translate contentKey="redBookApp.jobResult.help.settlementOrder" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.settlementDate')}
                id="job-result-settlementDate"
                name="settlementDate"
                data-cy="settlementDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="settlementDateLabel">
                <Translate contentKey="redBookApp.jobResult.help.settlementDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.errorMsg')}
                id="job-result-errorMsg"
                name="errorMsg"
                data-cy="errorMsg"
                type="text"
              />
              <UncontrolledTooltip target="errorMsgLabel">
                <Translate contentKey="redBookApp.jobResult.help.errorMsg" />
              </UncontrolledTooltip>
              <ValidatedBlobField
                label={translate('redBookApp.jobResult.errorImage')}
                id="job-result-errorImage"
                name="errorImage"
                data-cy="errorImage"
                isImage
                accept="image/*"
              />
              <UncontrolledTooltip target="errorImageLabel">
                <Translate contentKey="redBookApp.jobResult.help.errorImage" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.jobResult.channel')}
                id="job-result-channel"
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
                <Translate contentKey="redBookApp.jobResult.help.channel" />
              </UncontrolledTooltip>
              <ValidatedField
                id="job-result-cstJob"
                name="cstJob"
                data-cy="cstJob"
                label={translate('redBookApp.jobResult.cstJob')}
                type="select"
              >
                <option value="" key="0" />
                {cstJobs
                  ? cstJobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-result" replace color="info">
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

export default JobResultUpdate;
