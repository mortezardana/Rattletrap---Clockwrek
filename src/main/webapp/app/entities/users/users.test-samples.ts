import { UserType } from 'app/entities/enumerations/user-type.model';

import { IUsers, NewUsers } from './users.model';

export const sampleWithRequiredData: IUsers = {
  id: 3635,
  userName: 'Xtj4',
  password: 'invoice Wooden Profit-focused',
};

export const sampleWithPartialData: IUsers = {
  id: 36674,
  userName: 'Lfpkm3',
  password: 'Saint',
};

export const sampleWithFullData: IUsers = {
  id: 68947,
  userName: 'Ikcbhsl4',
  wallets: 'Azerbaijan SDD Program',
  galleries: 'programming Rustic',
  password: 'monitor RSS',
  type: UserType['GALLERYOWNER'],
};

export const sampleWithNewData: NewUsers = {
  userName: 'Snptkns2',
  password: 'Checking methodical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
