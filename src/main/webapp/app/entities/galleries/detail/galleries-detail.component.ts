import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGalleries } from '../galleries.model';

@Component({
  selector: 'jhi-galleries-detail',
  templateUrl: './galleries-detail.component.html',
})
export class GalleriesDetailComponent implements OnInit {
  galleries: IGalleries | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ galleries }) => {
      this.galleries = galleries;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
