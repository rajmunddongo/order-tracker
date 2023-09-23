import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from 'src/app/models/customer.type';
import { Product } from 'src/app/models/product.type';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { CustomerService } from 'src/app/services/customer.service';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { OrderService } from 'src/app/services/order.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.scss']
})
export class CheckoutPageComponent implements OnInit {
  constructor(private orderService:OrderService,private authService: AuthService,private fileManager:FileManagerService,private sanitizer:DomSanitizer, private router: Router, private route: ActivatedRoute, private _shoppingCartService: ShoppingCartService, private customerService : CustomerService) { }
  public cartproducts: Product[] = []
  public sum: number = 0;
  public total: number = 0;
  public fullamount: number = 0;
  private customerId: number = 0;
  private orderId: number = 0;
  user!: User;
  customer !: Customer;
  discountCode = "";

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.customerId = data.customer.id;
      this.user = data;
      this.customer = data.customer;
      this._shoppingCartService.getShoppingCartOrderId(this.customerId).subscribe(data => {
        this.orderId = data
      })
      this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
        this.cartproducts = data;
        this.cartproducts.forEach(product => {
          this.fileManager.downloadFile(product.imgSource).subscribe(data => {
            product.imgDataUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
          });});
        this.sum = 0;
        this.cartproducts.forEach(element => {
          this.sum += element.price;
        });
        this.total = this.sum;
      });
    });
  }
  goToOrderStatus() {
    this._shoppingCartService.getStripePaymentUrl(this.customerId).subscribe(data => {
      console.log('Received URL:', data.toString);
      window.location.href = data;
    })
  }
  submitDiscount() {
    this.orderService.postDiscount(this.discountCode, this.customerId).subscribe(data => this.sum =this.total - this.total*(data*0.01))
  }
}
