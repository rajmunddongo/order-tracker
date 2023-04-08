import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product.type';
import { AuthService } from 'src/app/services/auth.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.scss']
})
export class CheckoutPageComponent implements OnInit {
  constructor(private authService:AuthService,private router: Router, private route: ActivatedRoute,private _shoppingCartService : ShoppingCartService) {}
  public cartproducts: Product[] = []
  public sum : number = 0;
  public tax : number = 0;
  public fullamount : number = 0;
  private customerId : number = 0;

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.customerId = data.customer.id;

      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.sum = 0;
        this.cartproducts.forEach(element => {
          this.sum += element.price;
        });
        this.tax = this.sum * 0.15;
        this.fullamount = this.tax + this.sum;
      });
    });
  }
  goToOrderStatus() {
    this.router.navigate(['/order/status'], { queryParams: { number: this.customerId } });
  }
  
  

}
