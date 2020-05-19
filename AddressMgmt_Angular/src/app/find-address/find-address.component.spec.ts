import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FindAddressComponent } from './find-address.component';

describe('FindAddressComponent', () => {
  let component: FindAddressComponent;
  let fixture: ComponentFixture<FindAddressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FindAddressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FindAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
