import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWallets } from '../wallets.model';
import { WalletsService } from '../service/wallets.service';

@Injectable({ providedIn: 'root' })
export class WalletsRoutingResolveService implements Resolve<IWallets | null> {
  constructor(protected service: WalletsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWallets | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((wallets: HttpResponse<IWallets>) => {
          if (wallets.body) {
            return of(wallets.body);
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
