import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { GalleriesFormService, GalleriesFormGroup } from './galleries-form.service';
import { IGalleries } from '../galleries.model';
import { GalleriesService } from '../service/galleries.service';
import { INfts } from 'app/entities/nfts/nfts.model';
import { NftsService } from 'app/entities/nfts/service/nfts.service';
import { IUsers } from 'app/entities/users/users.model';
import { UsersService } from 'app/entities/users/service/users.service';

@Component({
  selector: 'jhi-galleries-update',
  templateUrl: './galleries-update.component.html',
})
export class GalleriesUpdateComponent implements OnInit {
  isSaving = false;
  galleries: IGalleries | null = null;

  nftsSharedCollection: INfts[] = [];
  usersSharedCollection: IUsers[] = [];

  editForm: GalleriesFormGroup = this.galleriesFormService.createGalleriesFormGroup();

  constructor(
    protected galleriesService: GalleriesService,
    protected galleriesFormService: GalleriesFormService,
    protected nftsService: NftsService,
    protected usersService: UsersService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNfts = (o1: INfts | null, o2: INfts | null): boolean => this.nftsService.compareNfts(o1, o2);

  compareUsers = (o1: IUsers | null, o2: IUsers | null): boolean => this.usersService.compareUsers(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galleries }) => {
      this.galleries = galleries;
      if (galleries) {
        this.updateForm(galleries);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const galleries = this.galleriesFormService.getGalleries(this.editForm);
    if (galleries.id !== null) {
      this.subscribeToSaveResponse(this.galleriesService.update(galleries));
    } else {
      this.subscribeToSaveResponse(this.galleriesService.create(galleries));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGalleries>>): void {
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

  protected updateForm(galleries: IGalleries): void {
    this.galleries = galleries;
    this.galleriesFormService.resetForm(this.editForm, galleries);

    this.nftsSharedCollection = this.nftsService.addNftsToCollectionIfMissing<INfts>(this.nftsSharedCollection, ...(galleries.nfts ?? []));
    this.usersSharedCollection = this.usersService.addUsersToCollectionIfMissing<IUsers>(this.usersSharedCollection, galleries.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.nftsService
      .query()
      .pipe(map((res: HttpResponse<INfts[]>) => res.body ?? []))
      .pipe(map((nfts: INfts[]) => this.nftsService.addNftsToCollectionIfMissing<INfts>(nfts, ...(this.galleries?.nfts ?? []))))
      .subscribe((nfts: INfts[]) => (this.nftsSharedCollection = nfts));

    this.usersService
      .query()
      .pipe(map((res: HttpResponse<IUsers[]>) => res.body ?? []))
      .pipe(map((users: IUsers[]) => this.usersService.addUsersToCollectionIfMissing<IUsers>(users, this.galleries?.userId)))
      .subscribe((users: IUsers[]) => (this.usersSharedCollection = users));
  }
}
