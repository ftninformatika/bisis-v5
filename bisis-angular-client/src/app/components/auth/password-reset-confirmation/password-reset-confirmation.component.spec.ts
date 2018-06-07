import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordResetConfirmationComponent } from './password-reset-confirmation.component';

describe('PasswordResetConfirmationComponent', () => {
  let component: PasswordResetConfirmationComponent;
  let fixture: ComponentFixture<PasswordResetConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordResetConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordResetConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
