import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INfts } from '../nfts.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nfts.test-samples';

import { NftsService } from './nfts.service';

const requireRestSample: INfts = {
  ...sampleWithRequiredData,
};

describe('Nfts Service', () => {
  let service: NftsService;
  let httpMock: HttpTestingController;
  let expectedResult: INfts | INfts[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NftsService);
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

    it('should create a Nfts', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const nfts = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(nfts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Nfts', () => {
      const nfts = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(nfts).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Nfts', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Nfts', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Nfts', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNftsToCollectionIfMissing', () => {
      it('should add a Nfts to an empty array', () => {
        const nfts: INfts = sampleWithRequiredData;
        expectedResult = service.addNftsToCollectionIfMissing([], nfts);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nfts);
      });

      it('should not add a Nfts to an array that contains it', () => {
        const nfts: INfts = sampleWithRequiredData;
        const nftsCollection: INfts[] = [
          {
            ...nfts,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNftsToCollectionIfMissing(nftsCollection, nfts);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Nfts to an array that doesn't contain it", () => {
        const nfts: INfts = sampleWithRequiredData;
        const nftsCollection: INfts[] = [sampleWithPartialData];
        expectedResult = service.addNftsToCollectionIfMissing(nftsCollection, nfts);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nfts);
      });

      it('should add only unique Nfts to an array', () => {
        const nftsArray: INfts[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const nftsCollection: INfts[] = [sampleWithRequiredData];
        expectedResult = service.addNftsToCollectionIfMissing(nftsCollection, ...nftsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const nfts: INfts = sampleWithRequiredData;
        const nfts2: INfts = sampleWithPartialData;
        expectedResult = service.addNftsToCollectionIfMissing([], nfts, nfts2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(nfts);
        expect(expectedResult).toContain(nfts2);
      });

      it('should accept null and undefined values', () => {
        const nfts: INfts = sampleWithRequiredData;
        expectedResult = service.addNftsToCollectionIfMissing([], null, nfts, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(nfts);
      });

      it('should return initial array if no Nfts is added', () => {
        const nftsCollection: INfts[] = [sampleWithRequiredData];
        expectedResult = service.addNftsToCollectionIfMissing(nftsCollection, undefined, null);
        expect(expectedResult).toEqual(nftsCollection);
      });
    });

    describe('compareNfts', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNfts(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNfts(entity1, entity2);
        const compareResult2 = service.compareNfts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNfts(entity1, entity2);
        const compareResult2 = service.compareNfts(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNfts(entity1, entity2);
        const compareResult2 = service.compareNfts(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
