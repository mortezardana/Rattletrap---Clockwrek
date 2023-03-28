import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommentsFormService } from './comments-form.service';
import { CommentsService } from '../service/comments.service';
import { IComments } from '../comments.model';
import { IGalleries } from 'app/entities/galleries/galleries.model';
import { GalleriesService } from 'app/entities/galleries/service/galleries.service';

import { CommentsUpdateComponent } from './comments-update.component';

describe('Comments Management Update Component', () => {
  let comp: CommentsUpdateComponent;
  let fixture: ComponentFixture<CommentsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commentsFormService: CommentsFormService;
  let commentsService: CommentsService;
  let galleriesService: GalleriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommentsUpdateComponent],
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
      .overrideTemplate(CommentsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommentsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commentsFormService = TestBed.inject(CommentsFormService);
    commentsService = TestBed.inject(CommentsService);
    galleriesService = TestBed.inject(GalleriesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Galleries query and add missing value', () => {
      const comments: IComments = { id: 456 };
      const id: IGalleries = { id: 73392 };
      comments.id = id;

      const galleriesCollection: IGalleries[] = [{ id: 90911 }];
      jest.spyOn(galleriesService, 'query').mockReturnValue(of(new HttpResponse({ body: galleriesCollection })));
      const additionalGalleries = [id];
      const expectedCollection: IGalleries[] = [...additionalGalleries, ...galleriesCollection];
      jest.spyOn(galleriesService, 'addGalleriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ comments });
      comp.ngOnInit();

      expect(galleriesService.query).toHaveBeenCalled();
      expect(galleriesService.addGalleriesToCollectionIfMissing).toHaveBeenCalledWith(
        galleriesCollection,
        ...additionalGalleries.map(expect.objectContaining)
      );
      expect(comp.galleriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const comments: IComments = { id: 456 };
      const id: IGalleries = { id: 7622 };
      comments.id = id;

      activatedRoute.data = of({ comments });
      comp.ngOnInit();

      expect(comp.galleriesSharedCollection).toContain(id);
      expect(comp.comments).toEqual(comments);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComments>>();
      const comments = { id: 123 };
      jest.spyOn(commentsFormService, 'getComments').mockReturnValue(comments);
      jest.spyOn(commentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comments });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comments }));
      saveSubject.complete();

      // THEN
      expect(commentsFormService.getComments).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commentsService.update).toHaveBeenCalledWith(expect.objectContaining(comments));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComments>>();
      const comments = { id: 123 };
      jest.spyOn(commentsFormService, 'getComments').mockReturnValue({ id: null });
      jest.spyOn(commentsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comments: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comments }));
      saveSubject.complete();

      // THEN
      expect(commentsFormService.getComments).toHaveBeenCalled();
      expect(commentsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComments>>();
      const comments = { id: 123 };
      jest.spyOn(commentsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comments });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commentsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGalleries', () => {
      it('Should forward to galleriesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(galleriesService, 'compareGalleries');
        comp.compareGalleries(entity, entity2);
        expect(galleriesService.compareGalleries).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
