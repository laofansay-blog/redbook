import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChannels } from 'app/shared/model/channels.model';
import { getEntities as getChannels } from 'app/entities/channels/channels.reducer';
import { ICustomer } from 'app/shared/model/customer.model';
import { CstStatus } from 'app/shared/model/enumerations/cst-status.model';
import { getEntity, updateEntity, createEntity, reset } from './customer.reducer';

export const CustomerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const channels = useAppSelector(state => state.channels.entities);
  const customerEntity = useAppSelector(state => state.customer.entity);
  const loading = useAppSelector(state => state.customer.loading);
  const updating = useAppSelector(state => state.customer.updating);
  const updateSuccess = useAppSelector(state => state.customer.updateSuccess);
  const cstStatusValues = Object.keys(CstStatus);

  const handleClose = () => {
    navigate('/customer' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChannels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.balance !== undefined && typeof values.balance !== 'number') {
      values.balance = Number(values.balance);
    }
    if (values.times !== undefined && typeof values.times !== 'number') {
      values.times = Number(values.times);
    }
    values.createdDate = convertDateTimeToServer(values.createdDate);

    const entity = {
      ...customerEntity,
      ...values,
      channels: channels.find(it => it.id.toString() === values.channels?.toString()),
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
          status: 'ENABLED',
          ...customerEntity,
          createdDate: convertDateTimeFromServer(customerEntity.createdDate),
          channels: customerEntity?.channels?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.customer.home.createOrEditLabel" data-cy="CustomerCreateUpdateHeading">
            <Translate contentKey="redBookApp.customer.home.createOrEditLabel">Create or edit a Customer</Translate>
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
                  id="customer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.customer.name')}
                id="customer-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="redBookApp.customer.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.mobile')}
                id="customer-mobile"
                name="mobile"
                data-cy="mobile"
                type="text"
              />
              <UncontrolledTooltip target="mobileLabel">
                <Translate contentKey="redBookApp.customer.help.mobile" />
              </UncontrolledTooltip>
              <ValidatedField label={translate('redBookApp.customer.email')} id="customer-email" name="email" data-cy="email" type="text" />
              <UncontrolledTooltip target="emailLabel">
                <Translate contentKey="redBookApp.customer.help.email" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.introduce')}
                id="customer-introduce"
                name="introduce"
                data-cy="introduce"
                type="text"
              />
              <UncontrolledTooltip target="introduceLabel">
                <Translate contentKey="redBookApp.customer.help.introduce" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.balance')}
                id="customer-balance"
                name="balance"
                data-cy="balance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="balanceLabel">
                <Translate contentKey="redBookApp.customer.help.balance" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.times')}
                id="customer-times"
                name="times"
                data-cy="times"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="timesLabel">
                <Translate contentKey="redBookApp.customer.help.times" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.status')}
                id="customer-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {cstStatusValues.map(cstStatus => (
                  <option value={cstStatus} key={cstStatus}>
                    {translate('redBookApp.CstStatus.' + cstStatus)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="statusLabel">
                <Translate contentKey="redBookApp.customer.help.status" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.customer.createdDate')}
                id="customer-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="createdDateLabel">
                <Translate contentKey="redBookApp.customer.help.createdDate" />
              </UncontrolledTooltip>
              <ValidatedField
                id="customer-channels"
                name="channels"
                data-cy="channels"
                label={translate('redBookApp.customer.channels')}
                type="select"
              >
                <option value="" key="0" />
                {channels
                  ? channels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer" replace color="info">
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

export default CustomerUpdate;
