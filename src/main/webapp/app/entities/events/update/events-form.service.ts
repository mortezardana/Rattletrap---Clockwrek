import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvents, NewEvents } from '../events.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvents for edit and NewEventsFormGroupInput for create.
 */
type EventsFormGroupInput = IEvents | PartialWithRequiredKeyOf<NewEvents>;

type EventsFormDefaults = Pick<NewEvents, 'id'>;

type EventsFormGroupContent = {
  id: FormControl<IEvents['id'] | NewEvents['id']>;
  name: FormControl<IEvents['name']>;
  startDate: FormControl<IEvents['startDate']>;
  enDate: FormControl<IEvents['enDate']>;
};

export type EventsFormGroup = FormGroup<EventsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventsFormService {
  createEventsFormGroup(events: EventsFormGroupInput = { id: null }): EventsFormGroup {
    const eventsRawValue = {
      ...this.getFormDefaults(),
      ...events,
    };
    return new FormGroup<EventsFormGroupContent>({
      id: new FormControl(
        { value: eventsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(eventsRawValue.name),
      startDate: new FormControl(eventsRawValue.startDate),
      enDate: new FormControl(eventsRawValue.enDate),
    });
  }

  getEvents(form: EventsFormGroup): IEvents | NewEvents {
    return form.getRawValue() as IEvents | NewEvents;
  }

  resetForm(form: EventsFormGroup, events: EventsFormGroupInput): void {
    const eventsRawValue = { ...this.getFormDefaults(), ...events };
    form.reset(
      {
        ...eventsRawValue,
        id: { value: eventsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventsFormDefaults {
    return {
      id: null,
    };
  }
}
