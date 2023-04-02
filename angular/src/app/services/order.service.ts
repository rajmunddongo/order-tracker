import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Customer } from "../models/customer.type";
import { Delivery } from "../models/delivery.type";
import { Product } from "../models/product.type";

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
      this.http.post("http://localhost:8081/order/customer/"+customerId+"/merchant/"+merchantId,products);
    }
      
      
}