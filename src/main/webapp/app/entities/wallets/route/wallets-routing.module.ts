import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WalletsComponent } from '../list/wallets.component';
import { WalletsDetailComponent } from '../detail/wallets-detail.component';
import { WalletsUpdateComponent } from '../update/wallets-update.component';
import { WalletsRoutingResolveService } from './wallets-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const walletsRoute: Routes = [
  {
    path: '',
    component: WalletsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WalletsDetailComponent,
    resolve: {
      wallets: WalletsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WalletsUpdateComponent,
    resolve: {
      wallets: WalletsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WalletsUpdateComponent,
    resolve: {
      wallets: WalletsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(walletsRoute)],
  exports: [RouterModule],
})
export class WalletsRoutingModule {}
