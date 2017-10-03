import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrollerViewComponent } from './scroller-view.component';

describe('ScrollerViewComponent', () => {
  let component: ScrollerViewComponent;
  let fixture: ComponentFixture<ScrollerViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScrollerViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScrollerViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
