import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Product } from "../models/product.type";

@Injectable()
export class ProductService {
    constructor(private http: HttpClient) {}

    getProducts() : Observable<Product[]> {
        return this.http.get<Product[]>("http://localhost:8081/api/products");
    }
    addProduct(prod:any) : void {
        this.http.post("http://localhost:8081/api/merchant/product", prod)
    }
}