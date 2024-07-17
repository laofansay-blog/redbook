import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobOrder from './job-order';
import JobOrderDetail from './job-order-detail';
import JobOrderUpdate from './job-order-update';
import JobOrderDeleteDialog from './job-order-delete-dialog';

const JobOrderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobOrder />} />
    <Route path="new" element={<JobOrderUpdate />} />
    <Route path=":id">
      <Route index element={<JobOrderDetail />} />
      <Route path="edit" element={<JobOrderUpdate />} />
      <Route path="delete" element={<JobOrderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobOrderRoutes;
