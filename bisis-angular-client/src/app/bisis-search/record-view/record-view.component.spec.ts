import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordViewComponent } from './record-view.component';

describe('RecordViewComponent', () => {
  let component: RecordViewComponent;
  let fixture: ComponentFixture<RecordViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
