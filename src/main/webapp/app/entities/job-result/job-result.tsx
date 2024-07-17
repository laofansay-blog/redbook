import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './job-result.reducer';

export const JobResult = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const jobResultList = useAppSelector(state => state.jobResult.entities);
  const loading = useAppSelector(state => state.jobResult.loading);
  const totalItems = useAppSelector(state => state.jobResult.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="job-result-heading" data-cy="JobResultHeading">
        <Translate contentKey="redBookApp.jobResult.home.title">Job Results</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="redBookApp.jobResult.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/job-result/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="redBookApp.jobResult.home.createLabel">Create new Job Result</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jobResultList && jobResultList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="redBookApp.jobResult.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="redBookApp.jobResult.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('jobUrl')}>
                  <Translate contentKey="redBookApp.jobResult.jobUrl">Job Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobUrl')} />
                </th>
                <th className="hand" onClick={sort('authorName')}>
                  <Translate contentKey="redBookApp.jobResult.authorName">Author Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('authorName')} />
                </th>
                <th className="hand" onClick={sort('accountId')}>
                  <Translate contentKey="redBookApp.jobResult.accountId">Account Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('accountId')} />
                </th>
                <th className="hand" onClick={sort('customerId')}>
                  <Translate contentKey="redBookApp.jobResult.customerId">Customer Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('customerId')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="redBookApp.jobResult.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('jobDate')}>
                  <Translate contentKey="redBookApp.jobResult.jobDate">Job Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobDate')} />
                </th>
                <th className="hand" onClick={sort('jobNo')}>
                  <Translate contentKey="redBookApp.jobResult.jobNo">Job No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('jobNo')} />
                </th>
                <th className="hand" onClick={sort('builderDate')}>
                  <Translate contentKey="redBookApp.jobResult.builderDate">Builder Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('builderDate')} />
                </th>
                <th className="hand" onClick={sort('replay')}>
                  <Translate contentKey="redBookApp.jobResult.replay">Replay</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('replay')} />
                </th>
                <th className="hand" onClick={sort('replayTheme')}>
                  <Translate contentKey="redBookApp.jobResult.replayTheme">Replay Theme</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('replayTheme')} />
                </th>
                <th className="hand" onClick={sort('replayImage')}>
                  <Translate contentKey="redBookApp.jobResult.replayImage">Replay Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('replayImage')} />
                </th>
                <th className="hand" onClick={sort('replayDate')}>
                  <Translate contentKey="redBookApp.jobResult.replayDate">Replay Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('replayDate')} />
                </th>
                <th className="hand" onClick={sort('effReplay')}>
                  <Translate contentKey="redBookApp.jobResult.effReplay">Eff Replay</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('effReplay')} />
                </th>
                <th className="hand" onClick={sort('settlement')}>
                  <Translate contentKey="redBookApp.jobResult.settlement">Settlement</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('settlement')} />
                </th>
                <th className="hand" onClick={sort('settlementOrder')}>
                  <Translate contentKey="redBookApp.jobResult.settlementOrder">Settlement Order</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('settlementOrder')} />
                </th>
                <th className="hand" onClick={sort('settlementDate')}>
                  <Translate contentKey="redBookApp.jobResult.settlementDate">Settlement Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('settlementDate')} />
                </th>
                <th className="hand" onClick={sort('errorMsg')}>
                  <Translate contentKey="redBookApp.jobResult.errorMsg">Error Msg</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('errorMsg')} />
                </th>
                <th className="hand" onClick={sort('errorImage')}>
                  <Translate contentKey="redBookApp.jobResult.errorImage">Error Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('errorImage')} />
                </th>
                <th className="hand" onClick={sort('channel')}>
                  <Translate contentKey="redBookApp.jobResult.channel">Channel</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('channel')} />
                </th>
                <th>
                  <Translate contentKey="redBookApp.jobResult.cstJob">Cst Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jobResultList.map((jobResult, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/job-result/${jobResult.id}`} color="link" size="sm">
                      {jobResult.id}
                    </Button>
                  </td>
                  <td>{jobResult.name}</td>
                  <td>{jobResult.jobUrl}</td>
                  <td>{jobResult.authorName}</td>
                  <td>{jobResult.accountId}</td>
                  <td>{jobResult.customerId}</td>
                  <td>
                    <Translate contentKey={`redBookApp.JobStatus.${jobResult.status}`} />
                  </td>
                  <td>{jobResult.jobDate ? <TextFormat type="date" value={jobResult.jobDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{jobResult.jobNo}</td>
                  <td>
                    {jobResult.builderDate ? <TextFormat type="date" value={jobResult.builderDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{jobResult.replay}</td>
                  <td>{jobResult.replayTheme}</td>
                  <td>
                    {jobResult.replayImage ? (
                      <div>
                        {jobResult.replayImageContentType ? (
                          <a onClick={openFile(jobResult.replayImageContentType, jobResult.replayImage)}>
                            <img
                              src={`data:${jobResult.replayImageContentType};base64,${jobResult.replayImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {jobResult.replayImageContentType}, {byteSize(jobResult.replayImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{jobResult.replayDate ? <TextFormat type="date" value={jobResult.replayDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{jobResult.effReplay ? 'true' : 'false'}</td>
                  <td>{jobResult.settlement ? 'true' : 'false'}</td>
                  <td>{jobResult.settlementOrder ? 'true' : 'false'}</td>
                  <td>
                    {jobResult.settlementDate ? <TextFormat type="date" value={jobResult.settlementDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{jobResult.errorMsg}</td>
                  <td>
                    {jobResult.errorImage ? (
                      <div>
                        {jobResult.errorImageContentType ? (
                          <a onClick={openFile(jobResult.errorImageContentType, jobResult.errorImage)}>
                            <img
                              src={`data:${jobResult.errorImageContentType};base64,${jobResult.errorImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {jobResult.errorImageContentType}, {byteSize(jobResult.errorImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`redBookApp.ChannelCate.${jobResult.channel}`} />
                  </td>
                  <td>{jobResult.cstJob ? <Link to={`/cst-job/${jobResult.cstJob.id}`}>{jobResult.cstJob.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/job-result/${jobResult.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/job-result/${jobResult.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/job-result/${jobResult.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="redBookApp.jobResult.home.notFound">No Job Results found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={jobResultList && jobResultList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default JobResult;
