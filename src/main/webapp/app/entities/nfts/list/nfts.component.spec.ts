import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NftsService } from '../service/nfts.service';

import { NftsComponent } from './nfts.component';

describe('Nfts Management Component', () => {
  let comp: NftsComponent;
  let fixture: ComponentFixture<NftsComponent>;
  let service: NftsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'nfts', component: NftsComponent }]), HttpClientTestingModule],
      declarations: [NftsComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(NftsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NftsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NftsService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.nfts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to nftsService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getNftsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getNftsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
