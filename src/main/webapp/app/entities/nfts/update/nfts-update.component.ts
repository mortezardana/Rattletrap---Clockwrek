import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NftsFormService, NftsFormGroup } from './nfts-form.service';
import { INfts } from '../nfts.model';
import { NftsService } from '../service/nfts.service';
import { Formats } from 'app/entities/enumerations/formats.model';

@Component({
  selector: 'jhi-nfts-update',
  templateUrl: './nfts-update.component.html',
})
export class NftsUpdateComponent implements OnInit {
  isSaving = false;
  nfts: INfts | null = null;
  formatsValues = Object.keys(Formats);

  editForm: NftsFormGroup = this.nftsFormService.createNftsFormGroup();

  constructor(protected nftsService: NftsService, protected nftsFormService: NftsFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nfts }) => {
      this.nfts = nfts;
      if (nfts) {
        this.updateForm(nfts);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nfts = this.nftsFormService.getNfts(this.editForm);
    if (nfts.id !== null) {
      this.subscribeToSaveResponse(this.nftsService.update(nfts));
    } else {
      this.subscribeToSaveResponse(this.nftsService.create(nfts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INfts>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(nfts: INfts): void {
    this.nfts = nfts;
    this.nftsFormService.resetForm(this.editForm, nfts);
  }
}
