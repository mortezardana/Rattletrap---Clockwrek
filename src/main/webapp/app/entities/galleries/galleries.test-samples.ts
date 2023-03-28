import { IGalleries, NewGalleries } from './galleries.model';

export const sampleWithRequiredData: IGalleries = {
  id: 26398,
  creator: 75832,
};

export const sampleWithPartialData: IGalleries = {
  id: 20769,
  creator: 80617,
  nfts: 'Unit Rubber',
  likes: 'Ohio',
  comments: 'Dakota e-enable Books',
};

export const sampleWithFullData: IGalleries = {
  id: 48378,
  creator: 74752,
  nfts: 'holistic proactive',
  likes: 'capacitor Cambridgeshire Virginia',
  comments: 'Pre-emptive Configuration USB',
};

export const sampleWithNewData: NewGalleries = {
  creator: 27687,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
