import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WalletsDetailComponent } from './wallets-detail.component';

describe('Wallets Management Detail Component', () => {
  let comp: WalletsDetailComponent;
  let fixture: ComponentFixture<WalletsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WalletsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ wallets: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(WalletsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WalletsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load wallets on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.wallets).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
