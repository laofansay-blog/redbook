import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './channels.reducer';

export const ChannelsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const channelsEntity = useAppSelector(state => state.channels.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="channelsDetailsHeading">
          <Translate contentKey="redBookApp.channels.detail.title">Channels</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{channelsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="redBookApp.channels.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="redBookApp.channels.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{channelsEntity.name}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="redBookApp.channels.category">Category</Translate>
            </span>
            <UncontrolledTooltip target="category">
              <Translate contentKey="redBookApp.channels.help.category" />
            </UncontrolledTooltip>
          </dt>
          <dd>{channelsEntity.category}</dd>
          <dt>
            <span id="rate">
              <Translate contentKey="redBookApp.channels.rate">Rate</Translate>
            </span>
            <UncontrolledTooltip target="rate">
              <Translate contentKey="redBookApp.channels.help.rate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{channelsEntity.rate}</dd>
          <dt>
            <span id="props">
              <Translate contentKey="redBookApp.channels.props">Props</Translate>
            </span>
          </dt>
          <dd>{channelsEntity.props}</dd>
          <dt>
            <span id="open">
              <Translate contentKey="redBookApp.channels.open">Open</Translate>
            </span>
            <UncontrolledTooltip target="open">
              <Translate contentKey="redBookApp.channels.help.open" />
            </UncontrolledTooltip>
          </dt>
          <dd>{channelsEntity.open ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/channels" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/channels/${channelsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChannelsDetail;
