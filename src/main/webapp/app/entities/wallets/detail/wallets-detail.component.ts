import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWallets } from '../wallets.model';

@Component({
  selector: 'jhi-wallets-detail',
  templateUrl: './wallets-detail.component.html',
})
export class WalletsDetailComponent implements OnInit {
  wallets: IWallets | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wallets }) => {
      this.wallets = wallets;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
