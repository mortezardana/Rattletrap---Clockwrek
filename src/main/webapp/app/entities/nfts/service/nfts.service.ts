import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INfts, NewNfts } from '../nfts.model';

export type PartialUpdateNfts = Partial<INfts> & Pick<INfts, 'id'>;

export type EntityResponseType = HttpResponse<INfts>;
export type EntityArrayResponseType = HttpResponse<INfts[]>;

@Injectable({ providedIn: 'root' })
export class NftsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nfts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nfts: NewNfts): Observable<EntityResponseType> {
    return this.http.post<INfts>(this.resourceUrl, nfts, { observe: 'response' });
  }

  update(nfts: INfts): Observable<EntityResponseType> {
    return this.http.put<INfts>(`${this.resourceUrl}/${this.getNftsIdentifier(nfts)}`, nfts, { observe: 'response' });
  }

  partialUpdate(nfts: PartialUpdateNfts): Observable<EntityResponseType> {
    return this.http.patch<INfts>(`${this.resourceUrl}/${this.getNftsIdentifier(nfts)}`, nfts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INfts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INfts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNftsIdentifier(nfts: Pick<INfts, 'id'>): number {
    return nfts.id;
  }

  compareNfts(o1: Pick<INfts, 'id'> | null, o2: Pick<INfts, 'id'> | null): boolean {
    return o1 && o2 ? this.getNftsIdentifier(o1) === this.getNftsIdentifier(o2) : o1 === o2;
  }

  addNftsToCollectionIfMissing<Type extends Pick<INfts, 'id'>>(
    nftsCollection: Type[],
    ...nftsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nfts: Type[] = nftsToCheck.filter(isPresent);
    if (nfts.length > 0) {
      const nftsCollectionIdentifiers = nftsCollection.map(nftsItem => this.getNftsIdentifier(nftsItem)!);
      const nftsToAdd = nfts.filter(nftsItem => {
        const nftsIdentifier = this.getNftsIdentifier(nftsItem);
        if (nftsCollectionIdentifiers.includes(nftsIdentifier)) {
          return false;
        }
        nftsCollectionIdentifiers.push(nftsIdentifier);
        return true;
      });
      return [...nftsToAdd, ...nftsCollection];
    }
    return nftsCollection;
  }
}
