import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../galleries.test-samples';

import { GalleriesFormService } from './galleries-form.service';

describe('Galleries Form Service', () => {
  let service: GalleriesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GalleriesFormService);
  });

  describe('Service methods', () => {
    describe('createGalleriesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGalleriesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creator: expect.any(Object),
            nfts: expect.any(Object),
            likes: expect.any(Object),
            comments: expect.any(Object),
            nfts: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });

      it('passing IGalleries should create a new form with FormGroup', () => {
        const formGroup = service.createGalleriesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            creator: expect.any(Object),
            nfts: expect.any(Object),
            likes: expect.any(Object),
            comments: expect.any(Object),
            nfts: expect.any(Object),
            userId: expect.any(Object),
          })
        );
      });
    });

    describe('getGalleries', () => {
      it('should return NewGalleries for default Galleries initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGalleriesFormGroup(sampleWithNewData);

        const galleries = service.getGalleries(formGroup) as any;

        expect(galleries).toMatchObject(sampleWithNewData);
      });

      it('should return NewGalleries for empty Galleries initial value', () => {
        const formGroup = service.createGalleriesFormGroup();

        const galleries = service.getGalleries(formGroup) as any;

        expect(galleries).toMatchObject({});
      });

      it('should return IGalleries', () => {
        const formGroup = service.createGalleriesFormGroup(sampleWithRequiredData);

        const galleries = service.getGalleries(formGroup) as any;

        expect(galleries).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGalleries should not enable id FormControl', () => {
        const formGroup = service.createGalleriesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGalleries should disable id FormControl', () => {
        const formGroup = service.createGalleriesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
