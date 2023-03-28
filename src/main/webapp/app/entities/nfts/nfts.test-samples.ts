import { Formats } from 'app/entities/enumerations/formats.model';

import { INfts, NewNfts } from './nfts.model';

export const sampleWithRequiredData: INfts = {
  id: 6197,
};

export const sampleWithPartialData: INfts = {
  id: 32713,
  metadataAddress: 'Agent primary plum',
};

export const sampleWithFullData: INfts = {
  id: 8346,
  creatorAddress: 'reboot Buckinghamshire red',
  ownerAddress: 'Tactics',
  contractAddress: 'Sleek SMTP',
  fileAddress: 'Curve',
  actualFile: 'Table override',
  metadataAddress: 'Arizona service-desk',
  metadata: 'Chips',
  tile: 'Dollar firewall',
  format: Formats['JPG'],
  traits: 'programming',
};

export const sampleWithNewData: NewNfts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
