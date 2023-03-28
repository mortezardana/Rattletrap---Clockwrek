import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWallets } from '../wallets.model';
import { WalletsService } from '../service/wallets.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './wallets-delete-dialog.component.html',
})
export class WalletsDeleteDialogComponent {
  wallets?: IWallets;

  constructor(protected walletsService: WalletsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.walletsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
