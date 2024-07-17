import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-result.reducer';

export const JobResultDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobResultEntity = useAppSelector(state => state.jobResult.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobResultDetailsHeading">
          <Translate contentKey="redBookApp.jobResult.detail.title">JobResult</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobResultEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="redBookApp.jobResult.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="redBookApp.jobResult.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.name}</dd>
          <dt>
            <span id="jobUrl">
              <Translate contentKey="redBookApp.jobResult.jobUrl">Job Url</Translate>
            </span>
            <UncontrolledTooltip target="jobUrl">
              <Translate contentKey="redBookApp.jobResult.help.jobUrl" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.jobUrl}</dd>
          <dt>
            <span id="authorName">
              <Translate contentKey="redBookApp.jobResult.authorName">Author Name</Translate>
            </span>
            <UncontrolledTooltip target="authorName">
              <Translate contentKey="redBookApp.jobResult.help.authorName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.authorName}</dd>
          <dt>
            <span id="accountId">
              <Translate contentKey="redBookApp.jobResult.accountId">Account Id</Translate>
            </span>
            <UncontrolledTooltip target="accountId">
              <Translate contentKey="redBookApp.jobResult.help.accountId" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.accountId}</dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="redBookApp.jobResult.customerId">Customer Id</Translate>
            </span>
            <UncontrolledTooltip target="customerId">
              <Translate contentKey="redBookApp.jobResult.help.customerId" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.customerId}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="redBookApp.jobResult.status">Status</Translate>
            </span>
            <UncontrolledTooltip target="status">
              <Translate contentKey="redBookApp.jobResult.help.status" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.status}</dd>
          <dt>
            <span id="jobDate">
              <Translate contentKey="redBookApp.jobResult.jobDate">Job Date</Translate>
            </span>
            <UncontrolledTooltip target="jobDate">
              <Translate contentKey="redBookApp.jobResult.help.jobDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.jobDate ? <TextFormat value={jobResultEntity.jobDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="jobNo">
              <Translate contentKey="redBookApp.jobResult.jobNo">Job No</Translate>
            </span>
            <UncontrolledTooltip target="jobNo">
              <Translate contentKey="redBookApp.jobResult.help.jobNo" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.jobNo}</dd>
          <dt>
            <span id="builderDate">
              <Translate contentKey="redBookApp.jobResult.builderDate">Builder Date</Translate>
            </span>
            <UncontrolledTooltip target="builderDate">
              <Translate contentKey="redBookApp.jobResult.help.builderDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.builderDate ? <TextFormat value={jobResultEntity.builderDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="replay">
              <Translate contentKey="redBookApp.jobResult.replay">Replay</Translate>
            </span>
            <UncontrolledTooltip target="replay">
              <Translate contentKey="redBookApp.jobResult.help.replay" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.replay}</dd>
          <dt>
            <span id="replayTheme">
              <Translate contentKey="redBookApp.jobResult.replayTheme">Replay Theme</Translate>
            </span>
            <UncontrolledTooltip target="replayTheme">
              <Translate contentKey="redBookApp.jobResult.help.replayTheme" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.replayTheme}</dd>
          <dt>
            <span id="replayImage">
              <Translate contentKey="redBookApp.jobResult.replayImage">Replay Image</Translate>
            </span>
            <UncontrolledTooltip target="replayImage">
              <Translate contentKey="redBookApp.jobResult.help.replayImage" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.replayImage ? (
              <div>
                {jobResultEntity.replayImageContentType ? (
                  <a onClick={openFile(jobResultEntity.replayImageContentType, jobResultEntity.replayImage)}>
                    <img
                      src={`data:${jobResultEntity.replayImageContentType};base64,${jobResultEntity.replayImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {jobResultEntity.replayImageContentType}, {byteSize(jobResultEntity.replayImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="replayDate">
              <Translate contentKey="redBookApp.jobResult.replayDate">Replay Date</Translate>
            </span>
            <UncontrolledTooltip target="replayDate">
              <Translate contentKey="redBookApp.jobResult.help.replayDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.replayDate ? <TextFormat value={jobResultEntity.replayDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="effReplay">
              <Translate contentKey="redBookApp.jobResult.effReplay">Eff Replay</Translate>
            </span>
            <UncontrolledTooltip target="effReplay">
              <Translate contentKey="redBookApp.jobResult.help.effReplay" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.effReplay ? 'true' : 'false'}</dd>
          <dt>
            <span id="settlement">
              <Translate contentKey="redBookApp.jobResult.settlement">Settlement</Translate>
            </span>
            <UncontrolledTooltip target="settlement">
              <Translate contentKey="redBookApp.jobResult.help.settlement" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.settlement ? 'true' : 'false'}</dd>
          <dt>
            <span id="settlementOrder">
              <Translate contentKey="redBookApp.jobResult.settlementOrder">Settlement Order</Translate>
            </span>
            <UncontrolledTooltip target="settlementOrder">
              <Translate contentKey="redBookApp.jobResult.help.settlementOrder" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.settlementOrder ? 'true' : 'false'}</dd>
          <dt>
            <span id="settlementDate">
              <Translate contentKey="redBookApp.jobResult.settlementDate">Settlement Date</Translate>
            </span>
            <UncontrolledTooltip target="settlementDate">
              <Translate contentKey="redBookApp.jobResult.help.settlementDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.settlementDate ? (
              <TextFormat value={jobResultEntity.settlementDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="errorMsg">
              <Translate contentKey="redBookApp.jobResult.errorMsg">Error Msg</Translate>
            </span>
            <UncontrolledTooltip target="errorMsg">
              <Translate contentKey="redBookApp.jobResult.help.errorMsg" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.errorMsg}</dd>
          <dt>
            <span id="errorImage">
              <Translate contentKey="redBookApp.jobResult.errorImage">Error Image</Translate>
            </span>
            <UncontrolledTooltip target="errorImage">
              <Translate contentKey="redBookApp.jobResult.help.errorImage" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {jobResultEntity.errorImage ? (
              <div>
                {jobResultEntity.errorImageContentType ? (
                  <a onClick={openFile(jobResultEntity.errorImageContentType, jobResultEntity.errorImage)}>
                    <img
                      src={`data:${jobResultEntity.errorImageContentType};base64,${jobResultEntity.errorImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {jobResultEntity.errorImageContentType}, {byteSize(jobResultEntity.errorImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="channel">
              <Translate contentKey="redBookApp.jobResult.channel">Channel</Translate>
            </span>
            <UncontrolledTooltip target="channel">
              <Translate contentKey="redBookApp.jobResult.help.channel" />
            </UncontrolledTooltip>
          </dt>
          <dd>{jobResultEntity.channel}</dd>
          <dt>
            <Translate contentKey="redBookApp.jobResult.cstJob">Cst Job</Translate>
          </dt>
          <dd>{jobResultEntity.cstJob ? jobResultEntity.cstJob.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-result" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-result/${jobResultEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobResultDetail;
