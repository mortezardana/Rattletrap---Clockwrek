import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommentsFormService, CommentsFormGroup } from './comments-form.service';
import { IComments } from '../comments.model';
import { CommentsService } from '../service/comments.service';
import { IGalleries } from 'app/entities/galleries/galleries.model';
import { GalleriesService } from 'app/entities/galleries/service/galleries.service';

@Component({
  selector: 'jhi-comments-update',
  templateUrl: './comments-update.component.html',
})
export class CommentsUpdateComponent implements OnInit {
  isSaving = false;
  comments: IComments | null = null;

  galleriesSharedCollection: IGalleries[] = [];

  editForm: CommentsFormGroup = this.commentsFormService.createCommentsFormGroup();

  constructor(
    protected commentsService: CommentsService,
    protected commentsFormService: CommentsFormService,
    protected galleriesService: GalleriesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareGalleries = (o1: IGalleries | null, o2: IGalleries | null): boolean => this.galleriesService.compareGalleries(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comments }) => {
      this.comments = comments;
      if (comments) {
        this.updateForm(comments);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comments = this.commentsFormService.getComments(this.editForm);
    if (comments.id !== null) {
      this.subscribeToSaveResponse(this.commentsService.update(comments));
    } else {
      this.subscribeToSaveResponse(this.commentsService.create(comments));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComments>>): void {
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

  protected updateForm(comments: IComments): void {
    this.comments = comments;
    this.commentsFormService.resetForm(this.editForm, comments);

    this.galleriesSharedCollection = this.galleriesService.addGalleriesToCollectionIfMissing<IGalleries>(
      this.galleriesSharedCollection,
      comments.id
    );
  }

  protected loadRelationshipsOptions(): void {
    this.galleriesService
      .query()
      .pipe(map((res: HttpResponse<IGalleries[]>) => res.body ?? []))
      .pipe(
        map((galleries: IGalleries[]) => this.galleriesService.addGalleriesToCollectionIfMissing<IGalleries>(galleries, this.comments?.id))
      )
      .subscribe((galleries: IGalleries[]) => (this.galleriesSharedCollection = galleries));
  }
}
