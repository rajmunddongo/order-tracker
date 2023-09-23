import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Customer } from "../models/customer.type";
import { Delivery } from "../models/delivery.type";
import { Product } from "../models/product.type";
import { Order } from "../models/order.type";

@Injectable()
export class OrderService {
    constructor(private http: HttpClient) {}
    
    getOrderCustomer(id:number) : Observable<Customer> {
        return this.http.get<Customer>("http://localhost:8081/api/order/"+id+"/customer");
    }
    patchStatus(id: number, status: String) {
        this.http.patch(`http://localhost:8081/api/order/${id}/status`, status).subscribe(response => {
          console.log(response);
        });
      }
    getDelivery(id: number) : Observable<Delivery>{
      return this.http.get<Delivery>("http://localhost:8081/api/order/"+ id +"/delivery");
    }
    postOrder(customerId : number, merchantId : number,products: Product[]) {
      return this.http.post<Order>("http://localhost:8081/api/order/customer/"+customerId+"/merchant/"+merchantId,products);
    }
    getOrder(orderId : number) {
      return this.http.get<Order>("http://localhost:8081/api/order/" + orderId);
    }
    postDiscount(code : String, id: number) {
      return this.http.post<number>("http://localhost:8081/api/customer/"+id+"/shoppingcart/coupon",code);
    }
    getDiscount(id: number) {
      return this.http.get<number>("http://localhost:8081/api/customer/"+id+"/shoppingcart/precentage");
    }
      
      
}