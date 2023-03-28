import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NftsFormService } from './nfts-form.service';
import { NftsService } from '../service/nfts.service';
import { INfts } from '../nfts.model';

import { NftsUpdateComponent } from './nfts-update.component';

describe('Nfts Management Update Component', () => {
  let comp: NftsUpdateComponent;
  let fixture: ComponentFixture<NftsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nftsFormService: NftsFormService;
  let nftsService: NftsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NftsUpdateComponent],
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
      .overrideTemplate(NftsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NftsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nftsFormService = TestBed.inject(NftsFormService);
    nftsService = TestBed.inject(NftsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nfts: INfts = { id: 456 };

      activatedRoute.data = of({ nfts });
      comp.ngOnInit();

      expect(comp.nfts).toEqual(nfts);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfts>>();
      const nfts = { id: 123 };
      jest.spyOn(nftsFormService, 'getNfts').mockReturnValue(nfts);
      jest.spyOn(nftsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nfts }));
      saveSubject.complete();

      // THEN
      expect(nftsFormService.getNfts).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nftsService.update).toHaveBeenCalledWith(expect.objectContaining(nfts));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfts>>();
      const nfts = { id: 123 };
      jest.spyOn(nftsFormService, 'getNfts').mockReturnValue({ id: null });
      jest.spyOn(nftsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfts: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nfts }));
      saveSubject.complete();

      // THEN
      expect(nftsFormService.getNfts).toHaveBeenCalled();
      expect(nftsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INfts>>();
      const nfts = { id: 123 };
      jest.spyOn(nftsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nfts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nftsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
