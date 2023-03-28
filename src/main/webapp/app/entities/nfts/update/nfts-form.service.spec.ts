import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nfts.test-samples';

import { NftsFormService } from './nfts-form.service';

describe('Nfts Form Service', () => {
  let service: NftsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NftsFormService);
  });

  describe('Service methods', () => {
    describe('createNftsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNftsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creatorAddress: expect.any(Object),
            ownerAddress: expect.any(Object),
            contractAddress: expect.any(Object),
            fileAddress: expect.any(Object),
            actualFile: expect.any(Object),
            metadataAddress: expect.any(Object),
            metadata: expect.any(Object),
            tile: expect.any(Object),
            format: expect.any(Object),
            traits: expect.any(Object),
            ids: expect.any(Object),
          })
        );
      });

      it('passing INfts should create a new form with FormGroup', () => {
        const formGroup = service.createNftsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creatorAddress: expect.any(Object),
            ownerAddress: expect.any(Object),
            contractAddress: expect.any(Object),
            fileAddress: expect.any(Object),
            actualFile: expect.any(Object),
            metadataAddress: expect.any(Object),
            metadata: expect.any(Object),
            tile: expect.any(Object),
            format: expect.any(Object),
            traits: expect.any(Object),
            ids: expect.any(Object),
          })
        );
      });
    });

    describe('getNfts', () => {
      it('should return NewNfts for default Nfts initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNftsFormGroup(sampleWithNewData);

        const nfts = service.getNfts(formGroup) as any;

        expect(nfts).toMatchObject(sampleWithNewData);
      });

      it('should return NewNfts for empty Nfts initial value', () => {
        const formGroup = service.createNftsFormGroup();

        const nfts = service.getNfts(formGroup) as any;

        expect(nfts).toMatchObject({});
      });

      it('should return INfts', () => {
        const formGroup = service.createNftsFormGroup(sampleWithRequiredData);

        const nfts = service.getNfts(formGroup) as any;

        expect(nfts).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INfts should not enable id FormControl', () => {
        const formGroup = service.createNftsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNfts should disable id FormControl', () => {
        const formGroup = service.createNftsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
