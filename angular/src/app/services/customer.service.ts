import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Address } from "../models/address.type";
import { Order } from "../models/order.type";
import { Customer } from "../models/customer.type";
import { Product } from "../models/product.type";

@Injectable()
export class CustomerService {
    constructor(private http: HttpClient) {}
    getCustomer(id: number): Observable<Customer>{
        return this.http.get<Customer>("http://localhost:8081/api/customer/"+ id);
    }
    getCustomerPreviousOrderNumber(id: number): Observable<number>{
        return this.http.get<number>("http://localhost:8081/api/customer/"+ id+ "/previousorders");
    }
}