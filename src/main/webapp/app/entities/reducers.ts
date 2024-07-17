import channels from 'app/entities/channels/channels.reducer';
import customer from 'app/entities/customer/customer.reducer';
import cstAccount from 'app/entities/cst-account/cst-account.reducer';
import cstJob from 'app/entities/cst-job/cst-job.reducer';
import jobResult from 'app/entities/job-result/job-result.reducer';
import jobOrder from 'app/entities/job-order/job-order.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  channels,
  customer,
  cstAccount,
  cstJob,
  jobResult,
  jobOrder,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
