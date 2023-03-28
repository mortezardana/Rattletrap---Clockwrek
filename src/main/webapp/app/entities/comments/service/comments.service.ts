import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComments, NewComments } from '../comments.model';

export type PartialUpdateComments = Partial<IComments> & Pick<IComments, 'id'>;

export type EntityResponseType = HttpResponse<IComments>;
export type EntityArrayResponseType = HttpResponse<IComments[]>;

@Injectable({ providedIn: 'root' })
export class CommentsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(comments: NewComments): Observable<EntityResponseType> {
    return this.http.post<IComments>(this.resourceUrl, comments, { observe: 'response' });
  }

  update(comments: IComments): Observable<EntityResponseType> {
    return this.http.put<IComments>(`${this.resourceUrl}/${this.getCommentsIdentifier(comments)}`, comments, { observe: 'response' });
  }

  partialUpdate(comments: PartialUpdateComments): Observable<EntityResponseType> {
    return this.http.patch<IComments>(`${this.resourceUrl}/${this.getCommentsIdentifier(comments)}`, comments, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComments>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComments[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommentsIdentifier(comments: Pick<IComments, 'id'>): number {
    return comments.id;
  }

  compareComments(o1: Pick<IComments, 'id'> | null, o2: Pick<IComments, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommentsIdentifier(o1) === this.getCommentsIdentifier(o2) : o1 === o2;
  }

  addCommentsToCollectionIfMissing<Type extends Pick<IComments, 'id'>>(
    commentsCollection: Type[],
    ...commentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comments: Type[] = commentsToCheck.filter(isPresent);
    if (comments.length > 0) {
      const commentsCollectionIdentifiers = commentsCollection.map(commentsItem => this.getCommentsIdentifier(commentsItem)!);
      const commentsToAdd = comments.filter(commentsItem => {
        const commentsIdentifier = this.getCommentsIdentifier(commentsItem);
        if (commentsCollectionIdentifiers.includes(commentsIdentifier)) {
          return false;
        }
        commentsCollectionIdentifiers.push(commentsIdentifier);
        return true;
      });
      return [...commentsToAdd, ...commentsCollection];
    }
    return commentsCollection;
  }
}
