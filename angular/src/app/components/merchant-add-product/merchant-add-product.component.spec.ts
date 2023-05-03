import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MerchantAddProductComponent } from './merchant-add-product.component';

describe('MerchantAddProductComponent', () => {
  let component: MerchantAddProductComponent;
  let fixture: ComponentFixture<MerchantAddProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MerchantAddProductComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MerchantAddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
