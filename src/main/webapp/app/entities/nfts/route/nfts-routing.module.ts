import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NftsComponent } from '../list/nfts.component';
import { NftsDetailComponent } from '../detail/nfts-detail.component';
import { NftsUpdateComponent } from '../update/nfts-update.component';
import { NftsRoutingResolveService } from './nfts-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nftsRoute: Routes = [
  {
    path: '',
    component: NftsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NftsDetailComponent,
    resolve: {
      nfts: NftsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NftsUpdateComponent,
    resolve: {
      nfts: NftsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NftsUpdateComponent,
    resolve: {
      nfts: NftsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nftsRoute)],
  exports: [RouterModule],
})
export class NftsRoutingModule {}
