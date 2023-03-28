import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GalleriesDetailComponent } from './galleries-detail.component';

describe('Galleries Management Detail Component', () => {
  let comp: GalleriesDetailComponent;
  let fixture: ComponentFixture<GalleriesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GalleriesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ galleries: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GalleriesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GalleriesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load galleries on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.galleries).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
