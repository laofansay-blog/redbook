import dayjs from 'dayjs';
import { IChannels } from 'app/shared/model/channels.model';
import { CstStatus } from 'app/shared/model/enumerations/cst-status.model';

export interface ICustomer {
  id?: string;
  name?: string;
  mobile?: string | null;
  email?: string | null;
  introduce?: string | null;
  balance?: number;
  times?: number;
  status?: keyof typeof CstStatus;
  createdDate?: dayjs.Dayjs | null;
  channels?: IChannels | null;
}

export const defaultValue: Readonly<ICustomer> = {};
