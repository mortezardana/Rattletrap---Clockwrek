import { IUsers } from 'app/entities/users/users.model';
import { WalletType } from 'app/entities/enumerations/wallet-type.model';

export interface IWallets {
  id: number;
  userId?: number | null;
  walletAddress?: string | null;
  walletType?: WalletType | null;
  userId?: Pick<IUsers, 'id'> | null;
}

export type NewWallets = Omit<IWallets, 'id'> & { id: null };
