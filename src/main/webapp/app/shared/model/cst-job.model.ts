import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { ExecuteType } from 'app/shared/model/enumerations/execute-type.model';
import { JobStatus } from 'app/shared/model/enumerations/job-status.model';
import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';

export interface ICstJob {
  id?: string;
  name?: string;
  executeType?: keyof typeof ExecuteType;
  category?: string | null;
  status?: keyof typeof JobStatus;
  createdDate?: dayjs.Dayjs;
  jobProps?: string;
  channel?: keyof typeof ChannelCate;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<ICstJob> = {};
