import { IEvents, NewEvents } from './events.model';

export const sampleWithRequiredData: IEvents = {
  id: 89576,
};

export const sampleWithPartialData: IEvents = {
  id: 18562,
  startDate: 'Parkways Helena',
};

export const sampleWithFullData: IEvents = {
  id: 91755,
  name: 'Cotton',
  startDate: 'Marks',
  enDate: 'indexing',
};

export const sampleWithNewData: NewEvents = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
