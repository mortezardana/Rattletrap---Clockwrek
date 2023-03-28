import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGalleries } from '../galleries.model';
import { GalleriesService } from '../service/galleries.service';

@Injectable({ providedIn: 'root' })
export class GalleriesRoutingResolveService implements Resolve<IGalleries | null> {
  constructor(protected service: GalleriesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGalleries | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((galleries: HttpResponse<IGalleries>) => {
          if (galleries.body) {
            return of(galleries.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
