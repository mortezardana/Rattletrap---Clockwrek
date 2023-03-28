import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGalleries, NewGalleries } from '../galleries.model';

export type PartialUpdateGalleries = Partial<IGalleries> & Pick<IGalleries, 'id'>;

export type EntityResponseType = HttpResponse<IGalleries>;
export type EntityArrayResponseType = HttpResponse<IGalleries[]>;

@Injectable({ providedIn: 'root' })
export class GalleriesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/galleries');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(galleries: NewGalleries): Observable<EntityResponseType> {
    return this.http.post<IGalleries>(this.resourceUrl, galleries, { observe: 'response' });
  }

  update(galleries: IGalleries): Observable<EntityResponseType> {
    return this.http.put<IGalleries>(`${this.resourceUrl}/${this.getGalleriesIdentifier(galleries)}`, galleries, { observe: 'response' });
  }

  partialUpdate(galleries: PartialUpdateGalleries): Observable<EntityResponseType> {
    return this.http.patch<IGalleries>(`${this.resourceUrl}/${this.getGalleriesIdentifier(galleries)}`, galleries, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGalleries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGalleries[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGalleriesIdentifier(galleries: Pick<IGalleries, 'id'>): number {
    return galleries.id;
  }

  compareGalleries(o1: Pick<IGalleries, 'id'> | null, o2: Pick<IGalleries, 'id'> | null): boolean {
    return o1 && o2 ? this.getGalleriesIdentifier(o1) === this.getGalleriesIdentifier(o2) : o1 === o2;
  }

  addGalleriesToCollectionIfMissing<Type extends Pick<IGalleries, 'id'>>(
    galleriesCollection: Type[],
    ...galleriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const galleries: Type[] = galleriesToCheck.filter(isPresent);
    if (galleries.length > 0) {
      const galleriesCollectionIdentifiers = galleriesCollection.map(galleriesItem => this.getGalleriesIdentifier(galleriesItem)!);
      const galleriesToAdd = galleries.filter(galleriesItem => {
        const galleriesIdentifier = this.getGalleriesIdentifier(galleriesItem);
        if (galleriesCollectionIdentifiers.includes(galleriesIdentifier)) {
          return false;
        }
        galleriesCollectionIdentifiers.push(galleriesIdentifier);
        return true;
      });
      return [...galleriesToAdd, ...galleriesCollection];
    }
    return galleriesCollection;
  }
}
