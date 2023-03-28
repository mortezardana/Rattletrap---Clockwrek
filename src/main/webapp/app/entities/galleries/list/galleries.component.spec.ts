import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GalleriesService } from '../service/galleries.service';

import { GalleriesComponent } from './galleries.component';

describe('Galleries Management Component', () => {
  let comp: GalleriesComponent;
  let fixture: ComponentFixture<GalleriesComponent>;
  let service: GalleriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'galleries', component: GalleriesComponent }]), HttpClientTestingModule],
      declarations: [GalleriesComponent],
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
      .overrideTemplate(GalleriesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GalleriesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GalleriesService);

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
    expect(comp.galleries?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to galleriesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getGalleriesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getGalleriesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
