import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Product } from 'src/app/models/product.type';
import { MerchantService } from 'src/app/services/merchant.service';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';
import { ShoppingCartService } from 'src/app/services/shoppingcart.service';
import { Merchant } from "../../models/merchant.type";
import { Order } from "../../models/order.type";

@Component({
  selector: 'app-root',
  templateUrl: './merchantoverview.component.html',
  styleUrls: ['./merchantoverview.component.scss']
})
export class MerchantOverviewComponent implements OnInit {
    constructor(private _merchantService:MerchantService,private _orderService: OrderService) {}
    Title="angular"
    ngOnInit(): void {
      this._merchantService.getOrders(353).subscribe(data => {
        this.merchantOrders=data;
        this.merchantOrders.forEach(order => {
          this._orderService.getOrderCustomer(order.id).subscribe(data=> order.customer=data);
        });
      });
    }
    onStatusChange(id: number, status: string) {
      const order = this.merchantOrders.find(o => o.id === id);
      if (order) {
        order.delivery.status = status;
        this._orderService.patchStatus(id, status);
      }
    }
    
    
    options = [
      {value:'Ordered', label:'Ordered'}, 
      {value:'Shipped', label:'Shipped'}, 
      {value:'On the way', label:'On the way'},
      {value:'Delivered', label:'Delivered'}
    ];
    
    
  
    public merchantOrders: Order[] = [];

}