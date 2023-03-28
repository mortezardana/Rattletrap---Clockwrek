import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INfts, NewNfts } from '../nfts.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INfts for edit and NewNftsFormGroupInput for create.
 */
type NftsFormGroupInput = INfts | PartialWithRequiredKeyOf<NewNfts>;

type NftsFormDefaults = Pick<NewNfts, 'id' | 'ids'>;

type NftsFormGroupContent = {
  id: FormControl<INfts['id'] | NewNfts['id']>;
  creatorAddress: FormControl<INfts['creatorAddress']>;
  ownerAddress: FormControl<INfts['ownerAddress']>;
  contractAddress: FormControl<INfts['contractAddress']>;
  fileAddress: FormControl<INfts['fileAddress']>;
  actualFile: FormControl<INfts['actualFile']>;
  metadataAddress: FormControl<INfts['metadataAddress']>;
  metadata: FormControl<INfts['metadata']>;
  tile: FormControl<INfts['tile']>;
  format: FormControl<INfts['format']>;
  traits: FormControl<INfts['traits']>;
  ids: FormControl<INfts['ids']>;
};

export type NftsFormGroup = FormGroup<NftsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NftsFormService {
  createNftsFormGroup(nfts: NftsFormGroupInput = { id: null }): NftsFormGroup {
    const nftsRawValue = {
      ...this.getFormDefaults(),
      ...nfts,
    };
    return new FormGroup<NftsFormGroupContent>({
      id: new FormControl(
        { value: nftsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      creatorAddress: new FormControl(nftsRawValue.creatorAddress),
      ownerAddress: new FormControl(nftsRawValue.ownerAddress),
      contractAddress: new FormControl(nftsRawValue.contractAddress),
      fileAddress: new FormControl(nftsRawValue.fileAddress),
      actualFile: new FormControl(nftsRawValue.actualFile),
      metadataAddress: new FormControl(nftsRawValue.metadataAddress),
      metadata: new FormControl(nftsRawValue.metadata),
      tile: new FormControl(nftsRawValue.tile),
      format: new FormControl(nftsRawValue.format),
      traits: new FormControl(nftsRawValue.traits),
      ids: new FormControl(nftsRawValue.ids ?? []),
    });
  }

  getNfts(form: NftsFormGroup): INfts | NewNfts {
    return form.getRawValue() as INfts | NewNfts;
  }

  resetForm(form: NftsFormGroup, nfts: NftsFormGroupInput): void {
    const nftsRawValue = { ...this.getFormDefaults(), ...nfts };
    form.reset(
      {
        ...nftsRawValue,
        id: { value: nftsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NftsFormDefaults {
    return {
      id: null,
      ids: [],
    };
  }
}
