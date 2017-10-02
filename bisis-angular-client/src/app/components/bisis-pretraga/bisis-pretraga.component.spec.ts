import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BisisPretragaComponent } from './bisis-pretraga.component';

describe('BisisPretragaComponent', () => {
  let component: BisisPretragaComponent;
  let fixture: ComponentFixture<BisisPretragaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BisisPretragaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BisisPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
