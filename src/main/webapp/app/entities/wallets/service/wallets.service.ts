import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWallets, NewWallets } from '../wallets.model';

export type PartialUpdateWallets = Partial<IWallets> & Pick<IWallets, 'id'>;

export type EntityResponseType = HttpResponse<IWallets>;
export type EntityArrayResponseType = HttpResponse<IWallets[]>;

@Injectable({ providedIn: 'root' })
export class WalletsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/wallets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wallets: NewWallets): Observable<EntityResponseType> {
    return this.http.post<IWallets>(this.resourceUrl, wallets, { observe: 'response' });
  }

  update(wallets: IWallets): Observable<EntityResponseType> {
    return this.http.put<IWallets>(`${this.resourceUrl}/${this.getWalletsIdentifier(wallets)}`, wallets, { observe: 'response' });
  }

  partialUpdate(wallets: PartialUpdateWallets): Observable<EntityResponseType> {
    return this.http.patch<IWallets>(`${this.resourceUrl}/${this.getWalletsIdentifier(wallets)}`, wallets, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWallets>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWallets[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWalletsIdentifier(wallets: Pick<IWallets, 'id'>): number {
    return wallets.id;
  }

  compareWallets(o1: Pick<IWallets, 'id'> | null, o2: Pick<IWallets, 'id'> | null): boolean {
    return o1 && o2 ? this.getWalletsIdentifier(o1) === this.getWalletsIdentifier(o2) : o1 === o2;
  }

  addWalletsToCollectionIfMissing<Type extends Pick<IWallets, 'id'>>(
    walletsCollection: Type[],
    ...walletsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const wallets: Type[] = walletsToCheck.filter(isPresent);
    if (wallets.length > 0) {
      const walletsCollectionIdentifiers = walletsCollection.map(walletsItem => this.getWalletsIdentifier(walletsItem)!);
      const walletsToAdd = wallets.filter(walletsItem => {
        const walletsIdentifier = this.getWalletsIdentifier(walletsItem);
        if (walletsCollectionIdentifiers.includes(walletsIdentifier)) {
          return false;
        }
        walletsCollectionIdentifiers.push(walletsIdentifier);
        return true;
      });
      return [...walletsToAdd, ...walletsCollection];
    }
    return walletsCollection;
  }
}
