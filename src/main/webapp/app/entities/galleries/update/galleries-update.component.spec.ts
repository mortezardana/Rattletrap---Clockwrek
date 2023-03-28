import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GalleriesFormService } from './galleries-form.service';
import { GalleriesService } from '../service/galleries.service';
import { IGalleries } from '../galleries.model';
import { INfts } from 'app/entities/nfts/nfts.model';
import { NftsService } from 'app/entities/nfts/service/nfts.service';
import { IUsers } from 'app/entities/users/users.model';
import { UsersService } from 'app/entities/users/service/users.service';

import { GalleriesUpdateComponent } from './galleries-update.component';

describe('Galleries Management Update Component', () => {
  let comp: GalleriesUpdateComponent;
  let fixture: ComponentFixture<GalleriesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let galleriesFormService: GalleriesFormService;
  let galleriesService: GalleriesService;
  let nftsService: NftsService;
  let usersService: UsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GalleriesUpdateComponent],
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
      .overrideTemplate(GalleriesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GalleriesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    galleriesFormService = TestBed.inject(GalleriesFormService);
    galleriesService = TestBed.inject(GalleriesService);
    nftsService = TestBed.inject(NftsService);
    usersService = TestBed.inject(UsersService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Nfts query and add missing value', () => {
      const galleries: IGalleries = { id: 456 };
      const nfts: INfts[] = [{ id: 49150 }];
      galleries.nfts = nfts;

      const nftsCollection: INfts[] = [{ id: 77907 }];
      jest.spyOn(nftsService, 'query').mockReturnValue(of(new HttpResponse({ body: nftsCollection })));
      const additionalNfts = [...nfts];
      const expectedCollection: INfts[] = [...additionalNfts, ...nftsCollection];
      jest.spyOn(nftsService, 'addNftsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ galleries });
      comp.ngOnInit();

      expect(nftsService.query).toHaveBeenCalled();
      expect(nftsService.addNftsToCollectionIfMissing).toHaveBeenCalledWith(nftsCollection, ...additionalNfts.map(expect.objectContaining));
      expect(comp.nftsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Users query and add missing value', () => {
      const galleries: IGalleries = { id: 456 };
      const userId: IUsers = { id: 27744 };
      galleries.userId = userId;

      const usersCollection: IUsers[] = [{ id: 14276 }];
      jest.spyOn(usersService, 'query').mockReturnValue(of(new HttpResponse({ body: usersCollection })));
      const additionalUsers = [userId];
      const expectedCollection: IUsers[] = [...additionalUsers, ...usersCollection];
      jest.spyOn(usersService, 'addUsersToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ galleries });
      comp.ngOnInit();

      expect(usersService.query).toHaveBeenCalled();
      expect(usersService.addUsersToCollectionIfMissing).toHaveBeenCalledWith(
        usersCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const galleries: IGalleries = { id: 456 };
      const nfts: INfts = { id: 99312 };
      galleries.nfts = [nfts];
      const userId: IUsers = { id: 86351 };
      galleries.userId = userId;

      activatedRoute.data = of({ galleries });
      comp.ngOnInit();

      expect(comp.nftsSharedCollection).toContain(nfts);
      expect(comp.usersSharedCollection).toContain(userId);
      expect(comp.galleries).toEqual(galleries);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGalleries>>();
      const galleries = { id: 123 };
      jest.spyOn(galleriesFormService, 'getGalleries').mockReturnValue(galleries);
      jest.spyOn(galleriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galleries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: galleries }));
      saveSubject.complete();

      // THEN
      expect(galleriesFormService.getGalleries).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(galleriesService.update).toHaveBeenCalledWith(expect.objectContaining(galleries));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGalleries>>();
      const galleries = { id: 123 };
      jest.spyOn(galleriesFormService, 'getGalleries').mockReturnValue({ id: null });
      jest.spyOn(galleriesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galleries: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: galleries }));
      saveSubject.complete();

      // THEN
      expect(galleriesFormService.getGalleries).toHaveBeenCalled();
      expect(galleriesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGalleries>>();
      const galleries = { id: 123 };
      jest.spyOn(galleriesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ galleries });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(galleriesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNfts', () => {
      it('Should forward to nftsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nftsService, 'compareNfts');
        comp.compareNfts(entity, entity2);
        expect(nftsService.compareNfts).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
