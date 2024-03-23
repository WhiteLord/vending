import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendingKeyboardComponent } from './vending-keyboard.component';

describe('VendingKeyboardComponent', () => {
  let component: VendingKeyboardComponent;
  let fixture: ComponentFixture<VendingKeyboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendingKeyboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendingKeyboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
