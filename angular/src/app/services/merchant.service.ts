import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Address } from "../models/address.type";

@Injectable()
export class MerchantService {

    constructor(private http: HttpClient) {}
    getMerchants(): Observable<Merchant[]>{
        return this.http.get<Merchant[]>("http://localhost:8081/api/merchants");
    }
    getMerchantAddress(merchantId : number): Observable<Address>{
        return this.http.get<Address>("http://localhost:8081/api/merchant/"+merchantId);
    }

    getMerchant(id:number): Observable<Merchant>{
        return this.http.get<Merchant>("http://localhost:8081/api/merchant/"+id);
    }
}