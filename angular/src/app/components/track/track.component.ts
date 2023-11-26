import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { Customer } from 'src/app/models/customer.type';
import { Delivery } from 'src/app/models/delivery.type';
import { Merchant } from 'src/app/models/merchant.type';
import { Product } from 'src/app/models/product.type';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { CustomerService } from 'src/app/services/customer.service';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { MerchantService } from 'src/app/services/merchant.service';
import { OrderService } from 'src/app/services/order.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.scss']
})
export class TrackComponent implements OnInit {
  public customer!: Customer;
  public merchant!: Merchant;
  public products: Product[] = [];
  public sum: number = 0;
  public tax: number = 0;
  public orderId: number = 352;
  public fullamount: number = 0;
  public merchantId: number = 354;
  public customerId: number = 52;
  public delivery!: Delivery;
  public statusprec: string = "";
  public user !: User;
  public total :number =0;
  public minusDiscount : number = 0;
  public previousOrderNumber :number = 0;

  constructor(private router: Router,private orderSerivce: OrderService,private fileManager : FileManagerService,private sanitizer:DomSanitizer,private route: ActivatedRoute, private authService: AuthService, private _orderService: OrderService, private _customerService: CustomerService, private _shoppingCartService: ShoppingCartService, private _merchantService: MerchantService) { }

  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.customerId = data.customer.id;
      var id = this.customerId;
      this._shoppingCartService.getShoppingCartOrderId(this.customerId).pipe(
        catchError(error => {
          console.error('Error occurred while fetching ShoppingCartOrderId:', error);
          this.navigateBack();
          return throwError(error);
        })
      ).subscribe(data => {
        if(data==null) {
          this.navigateBack();
        }
        this.orderId = data
        this.authService.whoami().subscribe(data => {
          this.user = data; this.customer = this.user.customer;
          this._customerService.getCustomerPreviousOrderNumber(this.customerId).subscribe(data => this.previousOrderNumber=data)
          this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
            this.products = data;
              this.fileManager.downloadFile(this.user.profilePicture).subscribe(data => {
                 this.user.imgDataUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
              });
            this.products.forEach(product => {
              this.fileManager.downloadFile(product.imgSource).subscribe(data => {
                product.imgDataUrl = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
              });});
            this.sum = 0;
            this.products.forEach(element => {
              this.sum += element.price;
            });
            this.sum+=this.merchant.deliveryPrice;
            this.total = this.sum;
            this.orderSerivce.getDiscount(id).subscribe(data => {this.sum =this.total - this.total*(data*0.01); this.minusDiscount =this.total-this.sum;});
            this._orderService.getOrder(this.orderId).subscribe(data => {
              this.merchant = data.merchant;
            })
            this._orderService.getDelivery(this.orderId).subscribe(data => {
              this.delivery = data;
            });
          })
        })
      })
    });

  }
  navigateBack() {
    this.router.navigate(['/']);
  }
  getProgressClass(): string {
    let widthClass = 'w-full';
    this.statusprec = "100%";
    if (this.delivery.status == 'Ordered') {
      widthClass = 'w-1/4';
      this.statusprec = "25%";
    }
    if (this.delivery.status == 'Shipped') {
      widthClass = 'w-2/4';
      this.statusprec = "50%";
    }
    if (this.delivery.status == 'On the way') {
      widthClass = 'w-3/4';
      this.statusprec = "75%";
    }
    return widthClass;
  }

}
