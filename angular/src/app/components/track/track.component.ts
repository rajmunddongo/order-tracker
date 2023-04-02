import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from 'src/app/models/customer.type';
import { Delivery } from 'src/app/models/delivery.type';
import { Merchant } from 'src/app/models/merchant.type';
import { Product } from 'src/app/models/product.type';
import { CustomerService } from 'src/app/services/customer.service';
import { MerchantService } from 'src/app/services/merchant.service';
import { OrderService } from 'src/app/services/order.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.scss']
})
export class TrackComponent implements OnInit {
  public customer! : Customer;
  public merchant!: Merchant;
  public products : Product[] = [];
  public sum : number = 0;
  public tax : number = 0;
  public orderId : number = 352;
  public fullamount : number = 0;
  public merchantId : number = 354;
  public customerId : number = 52;
  public delivery! : Delivery;
  public statusprec : string = "";

  constructor(private _orderService : OrderService,private _customerService : CustomerService ,private _shoppingCartService : ShoppingCartService,private _merchantService : MerchantService) {}

  ngOnInit(): void {
    this._customerService.getCustomer(this.customerId).subscribe(data => {
      this.customer = data;
    });
    this._shoppingCartService.getCustomerShoppingCartProducts(this.customerId).subscribe(data => {
      this.products = data;
      this.sum = 0;
      this.products.forEach(element => {
        this.sum += element.price;
      });
      this.tax = this.sum * 0.15;
      this.fullamount = this.tax + this.sum;
    });
    this._merchantService.getMerchant(this.merchantId).subscribe(data => {
      this.merchant = data;
    });
    this._orderService.getDelivery(this.orderId).subscribe(data => {
      this.delivery = data;
    });

  }
  getProgressClass() : string{
  let widthClass = 'w-full';
  this.statusprec= "100%";
  if (this.delivery.status=='Ordered') {
      widthClass = 'w-1/4';
      this.statusprec= "25%";
  }
  if (this.delivery.status=='Shipped') {
    widthClass = 'w-2/4';
    this.statusprec= "50%";
  }
  if (this.delivery.status=='On the way') {
    widthClass = 'w-3/4';
    this.statusprec= "75%";
  }
    return widthClass;
  }

}
