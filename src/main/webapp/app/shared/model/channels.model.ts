import { ChannelCate } from 'app/shared/model/enumerations/channel-cate.model';

export interface IChannels {
  id?: string;
  name?: string;
  category?: keyof typeof ChannelCate;
  rate?: number;
  props?: string | null;
  open?: boolean;
}

export const defaultValue: Readonly<IChannels> = {
  open: false,
};
