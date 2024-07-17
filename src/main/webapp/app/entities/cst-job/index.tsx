import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CstJob from './cst-job';
import CstJobDetail from './cst-job-detail';
import CstJobUpdate from './cst-job-update';
import CstJobDeleteDialog from './cst-job-delete-dialog';

const CstJobRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CstJob />} />
    <Route path="new" element={<CstJobUpdate />} />
    <Route path=":id">
      <Route index element={<CstJobDetail />} />
      <Route path="edit" element={<CstJobUpdate />} />
      <Route path="delete" element={<CstJobDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CstJobRoutes;
