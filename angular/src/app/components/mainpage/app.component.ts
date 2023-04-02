import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Product } from 'src/app/models/product.type';
import { MerchantService } from 'src/app/services/merchant.service';
import { ProductService } from 'src/app/services/product.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  // ...
  @ViewChild('cartProductsContainer', { static: false }) cartProductsContainer!: ElementRef;

  constructor(private _orderService : OrderService,private router: Router,private route: ActivatedRoute,private _merchantService : MerchantService, private _productService : ProductService, private _shoppingCartService : ShoppingCartService,private _cdRef: ChangeDetectorRef) {}

  title = 'Angular';
  
  public merchant : any ;
  public products : Product[] =  [];
  public cartproducts : Product[] =  [];
  public sum : number =0;
  private merchantId : number = 0;
  private customerId: number = 52;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.merchantId = params['number'];
    });
    this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
      this.cartproducts = data;
      this.sum = this.cartproducts.reduce((total, product) => total + product.price, 0);
      if (this.merchant) {
        this.sum += this.merchant.deliveryPrice;
      }
    });
    this._merchantService.getMerchant(this.merchantId).subscribe(data => {
      this.merchant = data;
      if (this.cartproducts && this.cartproducts.length > 0) {
        this.sum += this.merchant.deliveryPrice;
      }
    });
    this._merchantService.getMerchantProducts(this.merchantId).subscribe(data => {
      this.products = data;
    });
  }
  
  
  postShoppingCartProduct(event:Event,product: any) {
    event.preventDefault();
    event.stopPropagation();
    this._shoppingCartService.postShoppingCartProduct(this.customerId,product).subscribe(() => {
      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.sum=0;
        this.cartproducts.forEach((product) => {
          this.sum+=product.price;
        });
        this.sum+=this.merchant.deliveryPrice;
        setTimeout(() => {
          this._cdRef.detectChanges();
        }, 100);
      });
    }, error => {
      console.error('Error adding product to shopping cart:', error);
    });
  }
  deleteShoppingCartProduct(product: any) {
    var valtozo = false;
    if(this.cartproducts.length==1) valtozo=true;
    this._shoppingCartService.deleteShoppingCartProduct(this.customerId,product).subscribe(() => {
      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.sum=0;
        this.cartproducts.forEach((product) => {
          this.sum+=product.price;
        });
        this.sum+=this.merchant.deliveryPrice;
        this._cdRef.detectChanges(); // Manually trigger change detection
      });
    }, error => {
      console.error('Error adding product to shopping cart:', error);
    });
    if(valtozo=true){
      this.cartproducts= [];
      this.sum=0;
      this._cdRef.detectChanges();
    }
  }
  //goToCheckout() {
  //  this._orderService.postOrder(this.customerId,this.merchantId,this.products);
  //  this.router.navigate(['/checkout'], { queryParams: { number: this.customerId } });
  //}

  reloadCartProducts() {
    const cartProductsHtml = this.cartProductsContainer.nativeElement.innerHTML;
    this.cartProductsContainer.nativeElement.innerHTML = cartProductsHtml;
  }
}