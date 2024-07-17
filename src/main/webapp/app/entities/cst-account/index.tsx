import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CstAccount from './cst-account';
import CstAccountDetail from './cst-account-detail';
import CstAccountUpdate from './cst-account-update';
import CstAccountDeleteDialog from './cst-account-delete-dialog';

const CstAccountRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CstAccount />} />
    <Route path="new" element={<CstAccountUpdate />} />
    <Route path=":id">
      <Route index element={<CstAccountDetail />} />
      <Route path="edit" element={<CstAccountUpdate />} />
      <Route path="delete" element={<CstAccountDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CstAccountRoutes;
