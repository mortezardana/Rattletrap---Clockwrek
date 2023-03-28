import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { WalletsFormService, WalletsFormGroup } from './wallets-form.service';
import { IWallets } from '../wallets.model';
import { WalletsService } from '../service/wallets.service';
import { IUsers } from 'app/entities/users/users.model';
import { UsersService } from 'app/entities/users/service/users.service';
import { WalletType } from 'app/entities/enumerations/wallet-type.model';

@Component({
  selector: 'jhi-wallets-update',
  templateUrl: './wallets-update.component.html',
})
export class WalletsUpdateComponent implements OnInit {
  isSaving = false;
  wallets: IWallets | null = null;
  walletTypeValues = Object.keys(WalletType);

  usersSharedCollection: IUsers[] = [];

  editForm: WalletsFormGroup = this.walletsFormService.createWalletsFormGroup();

  constructor(
    protected walletsService: WalletsService,
    protected walletsFormService: WalletsFormService,
    protected usersService: UsersService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUsers = (o1: IUsers | null, o2: IUsers | null): boolean => this.usersService.compareUsers(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wallets }) => {
      this.wallets = wallets;
      if (wallets) {
        this.updateForm(wallets);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wallets = this.walletsFormService.getWallets(this.editForm);
    if (wallets.id !== null) {
      this.subscribeToSaveResponse(this.walletsService.update(wallets));
    } else {
      this.subscribeToSaveResponse(this.walletsService.create(wallets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWallets>>): void {
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

  protected updateForm(wallets: IWallets): void {
    this.wallets = wallets;
    this.walletsFormService.resetForm(this.editForm, wallets);

    this.usersSharedCollection = this.usersService.addUsersToCollectionIfMissing<IUsers>(this.usersSharedCollection, wallets.userId);
  }

  protected loadRelationshipsOptions(): void {
    this.usersService
      .query()
      .pipe(map((res: HttpResponse<IUsers[]>) => res.body ?? []))
      .pipe(map((users: IUsers[]) => this.usersService.addUsersToCollectionIfMissing<IUsers>(users, this.wallets?.userId)))
      .subscribe((users: IUsers[]) => (this.usersSharedCollection = users));
  }
}
