import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IComments, NewComments } from '../comments.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComments for edit and NewCommentsFormGroupInput for create.
 */
type CommentsFormGroupInput = IComments | PartialWithRequiredKeyOf<NewComments>;

type CommentsFormDefaults = Pick<NewComments, 'id'>;

type CommentsFormGroupContent = {
  id: FormControl<IComments['id'] | NewComments['id']>;
  text: FormControl<IComments['text']>;
  father: FormControl<IComments['father']>;
  id: FormControl<IComments['id']>;
};

export type CommentsFormGroup = FormGroup<CommentsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommentsFormService {
  createCommentsFormGroup(comments: CommentsFormGroupInput = { id: null }): CommentsFormGroup {
    const commentsRawValue = {
      ...this.getFormDefaults(),
      ...comments,
    };
    return new FormGroup<CommentsFormGroupContent>({
      id: new FormControl(
        { value: commentsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      text: new FormControl(commentsRawValue.text, {
        validators: [Validators.required],
      }),
      father: new FormControl(commentsRawValue.father),
      id: new FormControl(commentsRawValue.id),
    });
  }

  getComments(form: CommentsFormGroup): IComments | NewComments {
    return form.getRawValue() as IComments | NewComments;
  }

  resetForm(form: CommentsFormGroup, comments: CommentsFormGroupInput): void {
    const commentsRawValue = { ...this.getFormDefaults(), ...comments };
    form.reset(
      {
        ...commentsRawValue,
        id: { value: commentsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommentsFormDefaults {
    return {
      id: null,
    };
  }
}
