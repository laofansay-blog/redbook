import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobResult from './job-result';
import JobResultDetail from './job-result-detail';
import JobResultUpdate from './job-result-update';
import JobResultDeleteDialog from './job-result-delete-dialog';

const JobResultRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobResult />} />
    <Route path="new" element={<JobResultUpdate />} />
    <Route path=":id">
      <Route index element={<JobResultDetail />} />
      <Route path="edit" element={<JobResultUpdate />} />
      <Route path="delete" element={<JobResultDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobResultRoutes;
