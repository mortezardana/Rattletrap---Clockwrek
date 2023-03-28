import { IGalleries } from 'app/entities/galleries/galleries.model';

export interface IComments {
  id: number;
  text?: string | null;
  father?: number | null;
  id?: Pick<IGalleries, 'id'> | null;
}

export type NewComments = Omit<IComments, 'id'> & { id: null };
