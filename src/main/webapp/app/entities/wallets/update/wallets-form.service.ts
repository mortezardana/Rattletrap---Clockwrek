import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWallets, NewWallets } from '../wallets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWallets for edit and NewWalletsFormGroupInput for create.
 */
type WalletsFormGroupInput = IWallets | PartialWithRequiredKeyOf<NewWallets>;

type WalletsFormDefaults = Pick<NewWallets, 'id'>;

type WalletsFormGroupContent = {
  id: FormControl<IWallets['id'] | NewWallets['id']>;
  userId: FormControl<IWallets['userId']>;
  walletAddress: FormControl<IWallets['walletAddress']>;
  walletType: FormControl<IWallets['walletType']>;
  userId: FormControl<IWallets['userId']>;
};

export type WalletsFormGroup = FormGroup<WalletsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WalletsFormService {
  createWalletsFormGroup(wallets: WalletsFormGroupInput = { id: null }): WalletsFormGroup {
    const walletsRawValue = {
      ...this.getFormDefaults(),
      ...wallets,
    };
    return new FormGroup<WalletsFormGroupContent>({
      id: new FormControl(
        { value: walletsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      userId: new FormControl(walletsRawValue.userId, {
        validators: [Validators.required],
      }),
      walletAddress: new FormControl(walletsRawValue.walletAddress),
      walletType: new FormControl(walletsRawValue.walletType),
      userId: new FormControl(walletsRawValue.userId),
    });
  }

  getWallets(form: WalletsFormGroup): IWallets | NewWallets {
    return form.getRawValue() as IWallets | NewWallets;
  }

  resetForm(form: WalletsFormGroup, wallets: WalletsFormGroupInput): void {
    const walletsRawValue = { ...this.getFormDefaults(), ...wallets };
    form.reset(
      {
        ...walletsRawValue,
        id: { value: walletsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WalletsFormDefaults {
    return {
      id: null,
    };
  }
}
