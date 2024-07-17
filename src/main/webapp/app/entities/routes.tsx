import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Channels from './channels';
import Customer from './customer';
import CstAccount from './cst-account';
import CstJob from './cst-job';
import JobResult from './job-result';
import JobOrder from './job-order';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="channels/*" element={<Channels />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="cst-account/*" element={<CstAccount />} />
        <Route path="cst-job/*" element={<CstJob />} />
        <Route path="job-result/*" element={<JobResult />} />
        <Route path="job-order/*" element={<JobOrder />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
