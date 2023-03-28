import { UserType } from 'app/entities/enumerations/user-type.model';

export interface IUsers {
  id: number;
  userName?: string | null;
  wallets?: string | null;
  galleries?: string | null;
  password?: string | null;
  type?: UserType | null;
}

export type NewUsers = Omit<IUsers, 'id'> & { id: null };
