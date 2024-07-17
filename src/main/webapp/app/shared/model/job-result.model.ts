import dayjs from 'dayjs';
import { ICstJob } from 'app/shared/model/cst-job.model';
import { JobStatus } from 'app/shared/model/enumerations/job-status.model';
import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';

export interface IJobResult {
  id?: string;
  name?: string;
  jobUrl?: string;
  authorName?: string;
  accountId?: string;
  customerId?: string;
  status?: keyof typeof JobStatus;
  jobDate?: dayjs.Dayjs;
  jobNo?: string;
  builderDate?: dayjs.Dayjs;
  replay?: string;
  replayTheme?: string;
  replayImageContentType?: string | null;
  replayImage?: string | null;
  replayDate?: dayjs.Dayjs | null;
  effReplay?: boolean | null;
  settlement?: boolean | null;
  settlementOrder?: boolean | null;
  settlementDate?: dayjs.Dayjs | null;
  errorMsg?: string | null;
  errorImageContentType?: string | null;
  errorImage?: string | null;
  channel?: keyof typeof ChannelCate;
  cstJob?: ICstJob | null;
}

export const defaultValue: Readonly<IJobResult> = {
  effReplay: false,
  settlement: false,
  settlementOrder: false,
};
