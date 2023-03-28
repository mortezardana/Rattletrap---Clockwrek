import { IComments, NewComments } from './comments.model';

export const sampleWithRequiredData: IComments = {
  id: 51677,
  text: 'Alaska Chief',
};

export const sampleWithPartialData: IComments = {
  id: 4112,
  text: 'Small benchmark',
};

export const sampleWithFullData: IComments = {
  id: 32856,
  text: 'Rest',
  father: 57849,
};

export const sampleWithNewData: NewComments = {
  text: 'Bike array Group',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
