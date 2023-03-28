import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INfts } from '../nfts.model';
import { NftsService } from '../service/nfts.service';

@Injectable({ providedIn: 'root' })
export class NftsRoutingResolveService implements Resolve<INfts | null> {
  constructor(protected service: NftsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INfts | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nfts: HttpResponse<INfts>) => {
          if (nfts.body) {
            return of(nfts.body);
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
