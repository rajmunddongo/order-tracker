import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Customer } from "../models/customer.type";
import { Merchant } from "../models/merchant.type";
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
      
      
}