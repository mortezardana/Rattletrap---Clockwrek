import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INfts } from '../nfts.model';

@Component({
  selector: 'jhi-nfts-detail',
  templateUrl: './nfts-detail.component.html',
})
export class NftsDetailComponent implements OnInit {
  nfts: INfts | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nfts }) => {
      this.nfts = nfts;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
