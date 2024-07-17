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
import { ICstAccount } from 'app/shared/model/cst-account.model';
import { CstStatus } from 'app/shared/model/enumerations/cst-status.model';
import { getEntity, updateEntity, createEntity, reset } from './cst-account.reducer';

export const CstAccountUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const cstAccountEntity = useAppSelector(state => state.cstAccount.entity);
  const loading = useAppSelector(state => state.cstAccount.loading);
  const updating = useAppSelector(state => state.cstAccount.updating);
  const updateSuccess = useAppSelector(state => state.cstAccount.updateSuccess);
  const cstStatusValues = Object.keys(CstStatus);

  const handleClose = () => {
    navigate('/cst-account' + location.search);
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
    if (values.timesByDay !== undefined && typeof values.timesByDay !== 'number') {
      values.timesByDay = Number(values.timesByDay);
    }
    values.createdDate = convertDateTimeToServer(values.createdDate);

    const entity = {
      ...cstAccountEntity,
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
          status: 'ENABLED',
          ...cstAccountEntity,
          createdDate: convertDateTimeFromServer(cstAccountEntity.createdDate),
          customer: cstAccountEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.cstAccount.home.createOrEditLabel" data-cy="CstAccountCreateUpdateHeading">
            <Translate contentKey="redBookApp.cstAccount.home.createOrEditLabel">Create or edit a CstAccount</Translate>
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
                  id="cst-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.cstAccount.name')}
                id="cst-account-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="redBookApp.cstAccount.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.provider')}
                id="cst-account-provider"
                name="provider"
                data-cy="provider"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="providerLabel">
                <Translate contentKey="redBookApp.cstAccount.help.provider" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.rbAccount')}
                id="cst-account-rbAccount"
                name="rbAccount"
                data-cy="rbAccount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="rbAccountLabel">
                <Translate contentKey="redBookApp.cstAccount.help.rbAccount" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.rbPwd')}
                id="cst-account-rbPwd"
                name="rbPwd"
                data-cy="rbPwd"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="rbPwdLabel">
                <Translate contentKey="redBookApp.cstAccount.help.rbPwd" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.rbToken')}
                id="cst-account-rbToken"
                name="rbToken"
                data-cy="rbToken"
                type="text"
              />
              <UncontrolledTooltip target="rbTokenLabel">
                <Translate contentKey="redBookApp.cstAccount.help.rbToken" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.status')}
                id="cst-account-status"
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
                <Translate contentKey="redBookApp.cstAccount.help.status" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.timesByDay')}
                id="cst-account-timesByDay"
                name="timesByDay"
                data-cy="timesByDay"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="timesByDayLabel">
                <Translate contentKey="redBookApp.cstAccount.help.timesByDay" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.createdDate')}
                id="cst-account-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="createdDateLabel">
                <Translate contentKey="redBookApp.cstAccount.help.createdDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.cstAccount.channel')}
                id="cst-account-channel"
                name="channel"
                data-cy="channel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="channelLabel">
                <Translate contentKey="redBookApp.cstAccount.help.channel" />
              </UncontrolledTooltip>
              <ValidatedField
                id="cst-account-customer"
                name="customer"
                data-cy="customer"
                label={translate('redBookApp.cstAccount.customer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cst-account" replace color="info">
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

export default CstAccountUpdate;
