import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyBookshelfComponent } from './my-bookshelf.component';

describe('MyBookshelfComponent', () => {
  let component: MyBookshelfComponent;
  let fixture: ComponentFixture<MyBookshelfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyBookshelfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyBookshelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
