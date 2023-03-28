import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UsersFormService, UsersFormGroup } from './users-form.service';
import { IUsers } from '../users.model';
import { UsersService } from '../service/users.service';
import { UserType } from 'app/entities/enumerations/user-type.model';

@Component({
  selector: 'jhi-users-update',
  templateUrl: './users-update.component.html',
})
export class UsersUpdateComponent implements OnInit {
  isSaving = false;
  users: IUsers | null = null;
  userTypeValues = Object.keys(UserType);

  editForm: UsersFormGroup = this.usersFormService.createUsersFormGroup();

  constructor(
    protected usersService: UsersService,
    protected usersFormService: UsersFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ users }) => {
      this.users = users;
      if (users) {
        this.updateForm(users);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const users = this.usersFormService.getUsers(this.editForm);
    if (users.id !== null) {
      this.subscribeToSaveResponse(this.usersService.update(users));
    } else {
      this.subscribeToSaveResponse(this.usersService.create(users));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsers>>): void {
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

  protected updateForm(users: IUsers): void {
    this.users = users;
    this.usersFormService.resetForm(this.editForm, users);
  }
}
