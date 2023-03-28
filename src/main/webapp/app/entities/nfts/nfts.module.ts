import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NftsComponent } from './list/nfts.component';
import { NftsDetailComponent } from './detail/nfts-detail.component';
import { NftsUpdateComponent } from './update/nfts-update.component';
import { NftsDeleteDialogComponent } from './delete/nfts-delete-dialog.component';
import { NftsRoutingModule } from './route/nfts-routing.module';

@NgModule({
  imports: [SharedModule, NftsRoutingModule],
  declarations: [NftsComponent, NftsDetailComponent, NftsUpdateComponent, NftsDeleteDialogComponent],
})
export class NftsModule {}
