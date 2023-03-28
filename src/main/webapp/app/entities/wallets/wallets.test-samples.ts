import { WalletType } from 'app/entities/enumerations/wallet-type.model';

import { IWallets, NewWallets } from './wallets.model';

export const sampleWithRequiredData: IWallets = {
  id: 24774,
  userId: 61947,
};

export const sampleWithPartialData: IWallets = {
  id: 62276,
  userId: 11544,
};

export const sampleWithFullData: IWallets = {
  id: 19162,
  userId: 84224,
  walletAddress: 'fuchsia Implemented Optimized',
  walletType: WalletType['TEZOS'],
};

export const sampleWithNewData: NewWallets = {
  userId: 44658,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
