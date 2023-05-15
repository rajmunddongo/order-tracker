import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { Product } from 'src/app/models/product.type';
import { MerchantService } from 'src/app/services/merchant.service';
import { ProductService } from 'src/app/services/product.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { AuthService } from 'src/app/services/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  // ...
  @ViewChild('cartProductsContainer', { static: false }) cartProductsContainer!: ElementRef;

  constructor(private authService: AuthService, private _orderService: OrderService, private router: Router, private route: ActivatedRoute,
     private _merchantService: MerchantService, private _productService: ProductService, private _shoppingCartService: ShoppingCartService,
      private _cdRef: ChangeDetectorRef, private http:HttpClient) { }
  ngAfterViewInit(): void {
    this.authService.refreshToken();
  }

  title = 'Angular';

  public merchant: any;
  public products: Product[] = [];
  public cartproducts: Product[] = [];
  public sum: number = 0;
  private merchantId: number = 0;
  private customerId: number = 0;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.merchantId = params['number'];
    });

    this.authService.whoami().subscribe(data => {
      this.customerId = data.customer.id;

      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(cartProducts => {
        this.cartproducts = cartProducts;
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
    });
  }


  postShoppingCartProduct(event: Event, product: Product) {
    var merchantvar = this.merchant;
    merchantvar.user = null
    const prod = {
      id: product.id,
      name: product.name,
      description: product.description,
      imgSource: product.imgSource,
      price: product.price,
      merchant: merchantvar,
      delivery: null
    };
    event.preventDefault();
    event.stopPropagation();
    this._shoppingCartService.postShoppingCartProduct(this.customerId, prod).subscribe(() => {
      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.sum = 0;
        this.cartproducts.forEach((product) => {
          this.sum += product.price;
        });
        this.sum += this.merchant.deliveryPrice;
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
    if (this.cartproducts.length == 1) valtozo = true;
    this._shoppingCartService.deleteShoppingCartProduct(this.customerId, product).subscribe(() => {
      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.sum = 0;
        this.cartproducts.forEach((product) => {
          this.sum += product.price;
        });
        this.sum += this.merchant.deliveryPrice;
        this._cdRef.detectChanges(); // Manually trigger change detection
      });
    }, error => {
      console.error('Error adding product to shopping cart:', error);
    });
    if (valtozo = true) {
      this.cartproducts = [];
      this.sum = 0;
      this._cdRef.detectChanges();
    }
  }
  goToCheckout() {
    this._orderService.postOrder(this.customerId, this.merchantId, this.products).subscribe(data => {
      this.router.navigate(['/checkout']);
    });
  }

  reloadCartProducts() {
    const cartProductsHtml = this.cartProductsContainer.nativeElement.innerHTML;
    this.cartProductsContainer.nativeElement.innerHTML = cartProductsHtml;
  }
  // Inside your component class
  rateRestaurant(rating: number) {
    // Handle the logic to store the rating in your backend or local storage
    const ratingData= {
      merchantId : this.merchantId,
      rate: rating
    }
    this.http.post("http://localhost:8081/api/merchant/rate", ratingData).subscribe(
      (response) => {
        // Handle the response from the server if needed
        console.log('Rating request successful:', response);
        this._merchantService.postRateMerchant(ratingData);
        console.log(`User rated the restaurant with ${rating} stars`);
      },
      (error) => {
        // Handle any errors that occur during the request
        console.error('Rating request error:', error);
      }
    );
  }
}
