import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChannels } from 'app/shared/model/channels.model';
import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';
import { getEntity, updateEntity, createEntity, reset } from './channels.reducer';

export const ChannelsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const channelsEntity = useAppSelector(state => state.channels.entity);
  const loading = useAppSelector(state => state.channels.loading);
  const updating = useAppSelector(state => state.channels.updating);
  const updateSuccess = useAppSelector(state => state.channels.updateSuccess);
  const channelCateValues = Object.keys(ChannelCate);

  const handleClose = () => {
    navigate('/channels' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.rate !== undefined && typeof values.rate !== 'number') {
      values.rate = Number(values.rate);
    }

    const entity = {
      ...channelsEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          category: 'SmallRedBook',
          ...channelsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="redBookApp.channels.home.createOrEditLabel" data-cy="ChannelsCreateUpdateHeading">
            <Translate contentKey="redBookApp.channels.home.createOrEditLabel">Create or edit a Channels</Translate>
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
                  id="channels-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('redBookApp.channels.name')}
                id="channels-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="redBookApp.channels.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.channels.category')}
                id="channels-category"
                name="category"
                data-cy="category"
                type="select"
              >
                {channelCateValues.map(channelCate => (
                  <option value={channelCate} key={channelCate}>
                    {translate('redBookApp.ChannelCate.' + channelCate)}
                  </option>
                ))}
              </ValidatedField>
              <UncontrolledTooltip target="categoryLabel">
                <Translate contentKey="redBookApp.channels.help.category" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('redBookApp.channels.rate')}
                id="channels-rate"
                name="rate"
                data-cy="rate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="rateLabel">
                <Translate contentKey="redBookApp.channels.help.rate" />
              </UncontrolledTooltip>
              <ValidatedField label={translate('redBookApp.channels.props')} id="channels-props" name="props" data-cy="props" type="text" />
              <ValidatedField
                label={translate('redBookApp.channels.open')}
                id="channels-open"
                name="open"
                data-cy="open"
                check
                type="checkbox"
              />
              <UncontrolledTooltip target="openLabel">
                <Translate contentKey="redBookApp.channels.help.open" />
              </UncontrolledTooltip>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/channels" replace color="info">
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

export default ChannelsUpdate;
