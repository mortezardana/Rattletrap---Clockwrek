import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NftsDetailComponent } from './nfts-detail.component';

describe('Nfts Management Detail Component', () => {
  let comp: NftsDetailComponent;
  let fixture: ComponentFixture<NftsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NftsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nfts: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NftsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NftsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nfts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nfts).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
