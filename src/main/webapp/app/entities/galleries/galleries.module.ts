import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GalleriesComponent } from './list/galleries.component';
import { GalleriesDetailComponent } from './detail/galleries-detail.component';
import { GalleriesUpdateComponent } from './update/galleries-update.component';
import { GalleriesDeleteDialogComponent } from './delete/galleries-delete-dialog.component';
import { GalleriesRoutingModule } from './route/galleries-routing.module';

@NgModule({
  imports: [SharedModule, GalleriesRoutingModule],
  declarations: [GalleriesComponent, GalleriesDetailComponent, GalleriesUpdateComponent, GalleriesDeleteDialogComponent],
})
export class GalleriesModule {}
