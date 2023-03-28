import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGalleries, NewGalleries } from '../galleries.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGalleries for edit and NewGalleriesFormGroupInput for create.
 */
type GalleriesFormGroupInput = IGalleries | PartialWithRequiredKeyOf<NewGalleries>;

type GalleriesFormDefaults = Pick<NewGalleries, 'id' | 'nfts'>;

type GalleriesFormGroupContent = {
  id: FormControl<IGalleries['id'] | NewGalleries['id']>;
  creator: FormControl<IGalleries['creator']>;
  likes: FormControl<IGalleries['likes']>;
  comments: FormControl<IGalleries['comments']>;
  nfts: FormControl<IGalleries['nfts']>;
  userId: FormControl<IGalleries['userId']>;
};

export type GalleriesFormGroup = FormGroup<GalleriesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GalleriesFormService {
  createGalleriesFormGroup(galleries: GalleriesFormGroupInput = { id: null }): GalleriesFormGroup {
    const galleriesRawValue = {
      ...this.getFormDefaults(),
      ...galleries,
    };
    return new FormGroup<GalleriesFormGroupContent>({
      id: new FormControl(
        { value: galleriesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      creator: new FormControl(galleriesRawValue.creator, {
        validators: [Validators.required],
      }),
      likes: new FormControl(galleriesRawValue.likes),
      comments: new FormControl(galleriesRawValue.comments),
      nfts: new FormControl(galleriesRawValue.nfts ?? []),
      userId: new FormControl(galleriesRawValue.userId),
    });
  }

  getGalleries(form: GalleriesFormGroup): IGalleries | NewGalleries {
    return form.getRawValue() as IGalleries | NewGalleries;
  }

  resetForm(form: GalleriesFormGroup, galleries: GalleriesFormGroupInput): void {
    const galleriesRawValue = { ...this.getFormDefaults(), ...galleries };
    form.reset(
      {
        ...galleriesRawValue,
        id: { value: galleriesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GalleriesFormDefaults {
    return {
      id: null,
      nfts: [],
    };
  }
}
