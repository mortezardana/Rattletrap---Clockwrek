import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../wallets.test-samples';

import { WalletsFormService } from './wallets-form.service';

describe('Wallets Form Service', () => {
  let service: WalletsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WalletsFormService);
  });

  describe('Service methods', () => {
    describe('createWalletsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWalletsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            walletAddress: expect.any(Object),
            walletType: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });

      it('passing IWallets should create a new form with FormGroup', () => {
        const formGroup = service.createWalletsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            walletAddress: expect.any(Object),
            walletType: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });
    });

    describe('getWallets', () => {
      it('should return NewWallets for default Wallets initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createWalletsFormGroup(sampleWithNewData);

        const wallets = service.getWallets(formGroup) as any;

        expect(wallets).toMatchObject(sampleWithNewData);
      });

      it('should return NewWallets for empty Wallets initial value', () => {
        const formGroup = service.createWalletsFormGroup();

        const wallets = service.getWallets(formGroup) as any;

        expect(wallets).toMatchObject({});
      });

      it('should return IWallets', () => {
        const formGroup = service.createWalletsFormGroup(sampleWithRequiredData);

        const wallets = service.getWallets(formGroup) as any;

        expect(wallets).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWallets should not enable id FormControl', () => {
        const formGroup = service.createWalletsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWallets should disable id FormControl', () => {
        const formGroup = service.createWalletsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
