import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'wallets',
        data: { pageTitle: 'rattletrapApp.wallets.home.title' },
        loadChildren: () => import('./wallets/wallets.module').then(m => m.WalletsModule),
      },
      {
        path: 'users',
        data: { pageTitle: 'rattletrapApp.users.home.title' },
        loadChildren: () => import('./users/users.module').then(m => m.UsersModule),
      },
      {
        path: 'galleries',
        data: { pageTitle: 'rattletrapApp.galleries.home.title' },
        loadChildren: () => import('./galleries/galleries.module').then(m => m.GalleriesModule),
      },
      {
        path: 'comments',
        data: { pageTitle: 'rattletrapApp.comments.home.title' },
        loadChildren: () => import('./comments/comments.module').then(m => m.CommentsModule),
      },
      {
        path: 'nfts',
        data: { pageTitle: 'rattletrapApp.nfts.home.title' },
        loadChildren: () => import('./nfts/nfts.module').then(m => m.NftsModule),
      },
      {
        path: 'events',
        data: { pageTitle: 'rattletrapApp.events.home.title' },
        loadChildren: () => import('./events/events.module').then(m => m.EventsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
