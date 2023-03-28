import { INfts } from 'app/entities/nfts/nfts.model';
import { IUsers } from 'app/entities/users/users.model';

export interface IGalleries {
  id: number;
  creator?: number | null;
  nfts?: string | null;
  likes?: string | null;
  comments?: string | null;
  nfts?: Pick<INfts, 'id'>[] | null;
  userId?: Pick<IUsers, 'id'> | null;
}

export type NewGalleries = Omit<IGalleries, 'id'> & { id: null };
