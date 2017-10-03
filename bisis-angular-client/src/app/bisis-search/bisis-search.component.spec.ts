import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BisisSearchComponent } from './bisis-search.component';

describe('BisisSearchComponent', () => {
  let component: BisisSearchComponent;
  let fixture: ComponentFixture<BisisSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BisisSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BisisSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
