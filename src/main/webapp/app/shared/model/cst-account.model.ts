import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { CstStatus } from 'app/shared/model/enumerations/cst-status.model';

export interface ICstAccount {
  id?: string;
  name?: string;
  provider?: string;
  rbAccount?: string;
  rbPwd?: string;
  rbToken?: string | null;
  status?: keyof typeof CstStatus;
  timesByDay?: number;
  createdDate?: dayjs.Dayjs | null;
  channel?: string;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<ICstAccount> = {};
