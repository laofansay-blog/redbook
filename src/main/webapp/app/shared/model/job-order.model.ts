import dayjs from 'dayjs';
import { IJobResult } from 'app/shared/model/job-result.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IJobOrder {
  id?: string;
  settlementOrderNo?: string;
  amount?: number;
  paymentStatus?: keyof typeof PaymentStatus;
  settlementDate?: dayjs.Dayjs;
  paymentDate?: dayjs.Dayjs;
  customerId?: number;
  channel?: string;
  jobResults?: IJobResult[] | null;
}

export const defaultValue: Readonly<IJobOrder> = {};
