import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Product } from "../models/product.type";

@Injectable()
export class ShoppingCartService {
    constructor(private http: HttpClient) {}

    getShoppingCartProducts() : Observable<Product[]> {
        return this.http.get<Product[]>("http://localhost:8081/api/customer/1152/shoppingcart/products");
    }
    postShoppingCartProduct(product: any): Observable<any> {
        const url = 'http://localhost:8081/api/customer/1152/shoppingcart/product';
        return this.http.post(url, product);
      }
    deleteShoppingCartProduct(product : Product): Observable<any>{
        const url = 'http://localhost:8081/api/customer/1152/shoppingcart/product/'+product.id;
        return this.http.delete(url);
    }
      
      
}