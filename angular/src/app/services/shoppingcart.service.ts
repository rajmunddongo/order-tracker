import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Product } from "../models/product.type";

@Injectable()
export class ShoppingCartService {
    constructor(private http: HttpClient) { }

    getCustomerShoppingCartProducts(id: number): Observable<Product[]> {
        return this.http.get<Product[]>("http://localhost:8081/api/customer/" + id + "/shoppingcart/products");
    }
    postShoppingCartProduct(id: number, product: any): Observable<any> {
        const url = "http://localhost:8081/api/customer/" + id + "/shoppingcart/product";
        return this.http.post(url, product);
    }
    deleteShoppingCartProduct(id: number, product: Product): Observable<any> {
        const url = "http://localhost:8081/api/customer/" + id + "/shoppingcart/product/" + product.id;
        return this.http.delete(url);
    }
    getShoppingCartOrderId(customerId: number): Observable<number> {
        return this.http.get<number>("http://localhost:8081/api/customer/" + customerId + "/shoppingcart/orderId");
    }
    getStripePaymentUrl(customerId: number,deliveryPrice:number): Observable<string> {
        const url = `http://localhost:8081/api/payment/link/${customerId}/delivery/${deliveryPrice}`;
        const options = {
          responseType: 'text' as 'json',
          observe: 'body' as 'body',
        };

        return this.http.get<string>(url, options);
      }
    }
