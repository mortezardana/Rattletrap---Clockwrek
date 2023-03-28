import { IGalleries } from 'app/entities/galleries/galleries.model';
import { Formats } from 'app/entities/enumerations/formats.model';

export interface INfts {
  id: number;
  creatorAddress?: string | null;
  ownerAddress?: string | null;
  contractAddress?: string | null;
  fileAddress?: string | null;
  actualFile?: string | null;
  metadataAddress?: string | null;
  metadata?: string | null;
  tile?: string | null;
  format?: Formats | null;
  traits?: string | null;
  ids?: Pick<IGalleries, 'id'>[] | null;
}

export type NewNfts = Omit<INfts, 'id'> & { id: null };
