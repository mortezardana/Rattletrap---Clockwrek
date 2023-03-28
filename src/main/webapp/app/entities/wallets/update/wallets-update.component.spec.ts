import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WalletsFormService } from './wallets-form.service';
import { WalletsService } from '../service/wallets.service';
import { IWallets } from '../wallets.model';
import { IUsers } from 'app/entities/users/users.model';
import { UsersService } from 'app/entities/users/service/users.service';

import { WalletsUpdateComponent } from './wallets-update.component';

describe('Wallets Management Update Component', () => {
  let comp: WalletsUpdateComponent;
  let fixture: ComponentFixture<WalletsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let walletsFormService: WalletsFormService;
  let walletsService: WalletsService;
  let usersService: UsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WalletsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WalletsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WalletsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    walletsFormService = TestBed.inject(WalletsFormService);
    walletsService = TestBed.inject(WalletsService);
    usersService = TestBed.inject(UsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Users query and add missing value', () => {
      const wallets: IWallets = { id: 456 };
      const userId: IUsers = { id: 45842 };
      wallets.userId = userId;

      const usersCollection: IUsers[] = [{ id: 73178 }];
      jest.spyOn(usersService, 'query').mockReturnValue(of(new HttpResponse({ body: usersCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUsers[] = [...additionalUsers, ...usersCollection];
      jest.spyOn(usersService, 'addUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ wallets });
      comp.ngOnInit();

      expect(usersService.query).toHaveBeenCalled();
      expect(usersService.addUsersToCollectionIfMissing).toHaveBeenCalledWith(
        usersCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const wallets: IWallets = { id: 456 };
      const userId: IUsers = { id: 88356 };
      wallets.userId = userId;

      activatedRoute.data = of({ wallets });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(userId);
      expect(comp.wallets).toEqual(wallets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWallets>>();
      const wallets = { id: 123 };
      jest.spyOn(walletsFormService, 'getWallets').mockReturnValue(wallets);
      jest.spyOn(walletsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wallets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wallets }));
      saveSubject.complete();

      // THEN
      expect(walletsFormService.getWallets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(walletsService.update).toHaveBeenCalledWith(expect.objectContaining(wallets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWallets>>();
      const wallets = { id: 123 };
      jest.spyOn(walletsFormService, 'getWallets').mockReturnValue({ id: null });
      jest.spyOn(walletsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wallets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wallets }));
      saveSubject.complete();

      // THEN
      expect(walletsFormService.getWallets).toHaveBeenCalled();
      expect(walletsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWallets>>();
      const wallets = { id: 123 };
      jest.spyOn(walletsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wallets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(walletsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUsers', () => {
      it('Should forward to usersService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usersService, 'compareUsers');
        comp.compareUsers(entity, entity2);
        expect(usersService.compareUsers).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
