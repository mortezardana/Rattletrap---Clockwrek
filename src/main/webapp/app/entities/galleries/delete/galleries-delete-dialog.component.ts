import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGalleries } from '../galleries.model';
import { GalleriesService } from '../service/galleries.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './galleries-delete-dialog.component.html',
})
export class GalleriesDeleteDialogComponent {
  galleries?: IGalleries;

  constructor(protected galleriesService: GalleriesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.galleriesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
