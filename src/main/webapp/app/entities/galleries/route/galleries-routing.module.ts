import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GalleriesComponent } from '../list/galleries.component';
import { GalleriesDetailComponent } from '../detail/galleries-detail.component';
import { GalleriesUpdateComponent } from '../update/galleries-update.component';
import { GalleriesRoutingResolveService } from './galleries-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const galleriesRoute: Routes = [
  {
    path: '',
    component: GalleriesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GalleriesDetailComponent,
    resolve: {
      galleries: GalleriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GalleriesUpdateComponent,
    resolve: {
      galleries: GalleriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GalleriesUpdateComponent,
    resolve: {
      galleries: GalleriesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(galleriesRoute)],
  exports: [RouterModule],
})
export class GalleriesRoutingModule {}
