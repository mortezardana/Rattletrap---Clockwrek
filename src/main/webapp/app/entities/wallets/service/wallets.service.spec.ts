import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWallets } from '../wallets.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../wallets.test-samples';

import { WalletsService } from './wallets.service';

const requireRestSample: IWallets = {
  ...sampleWithRequiredData,
};

describe('Wallets Service', () => {
  let service: WalletsService;
  let httpMock: HttpTestingController;
  let expectedResult: IWallets | IWallets[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WalletsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Wallets', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const wallets = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(wallets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Wallets', () => {
      const wallets = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(wallets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Wallets', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Wallets', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Wallets', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWalletsToCollectionIfMissing', () => {
      it('should add a Wallets to an empty array', () => {
        const wallets: IWallets = sampleWithRequiredData;
        expectedResult = service.addWalletsToCollectionIfMissing([], wallets);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wallets);
      });

      it('should not add a Wallets to an array that contains it', () => {
        const wallets: IWallets = sampleWithRequiredData;
        const walletsCollection: IWallets[] = [
          {
            ...wallets,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWalletsToCollectionIfMissing(walletsCollection, wallets);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Wallets to an array that doesn't contain it", () => {
        const wallets: IWallets = sampleWithRequiredData;
        const walletsCollection: IWallets[] = [sampleWithPartialData];
        expectedResult = service.addWalletsToCollectionIfMissing(walletsCollection, wallets);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wallets);
      });

      it('should add only unique Wallets to an array', () => {
        const walletsArray: IWallets[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const walletsCollection: IWallets[] = [sampleWithRequiredData];
        expectedResult = service.addWalletsToCollectionIfMissing(walletsCollection, ...walletsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const wallets: IWallets = sampleWithRequiredData;
        const wallets2: IWallets = sampleWithPartialData;
        expectedResult = service.addWalletsToCollectionIfMissing([], wallets, wallets2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wallets);
        expect(expectedResult).toContain(wallets2);
      });

      it('should accept null and undefined values', () => {
        const wallets: IWallets = sampleWithRequiredData;
        expectedResult = service.addWalletsToCollectionIfMissing([], null, wallets, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wallets);
      });

      it('should return initial array if no Wallets is added', () => {
        const walletsCollection: IWallets[] = [sampleWithRequiredData];
        expectedResult = service.addWalletsToCollectionIfMissing(walletsCollection, undefined, null);
        expect(expectedResult).toEqual(walletsCollection);
      });
    });

    describe('compareWallets', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWallets(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWallets(entity1, entity2);
        const compareResult2 = service.compareWallets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWallets(entity1, entity2);
        const compareResult2 = service.compareWallets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWallets(entity1, entity2);
        const compareResult2 = service.compareWallets(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
