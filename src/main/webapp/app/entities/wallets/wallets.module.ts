import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WalletsComponent } from './list/wallets.component';
import { WalletsDetailComponent } from './detail/wallets-detail.component';
import { WalletsUpdateComponent } from './update/wallets-update.component';
import { WalletsDeleteDialogComponent } from './delete/wallets-delete-dialog.component';
import { WalletsRoutingModule } from './route/wallets-routing.module';

@NgModule({
  imports: [SharedModule, WalletsRoutingModule],
  declarations: [WalletsComponent, WalletsDetailComponent, WalletsUpdateComponent, WalletsDeleteDialogComponent],
})
export class WalletsModule {}
