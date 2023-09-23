import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Merchant } from "../models/merchant.type";
import { Product } from "../models/product.type";

@Injectable()
export class UserService {
    constructor(private http: HttpClient) { }
    changeProfilePic(id: any, picture : any): Observable<any> {
        const formData = new FormData();
        formData.append('id', id);
        formData.append('picture', picture); 
        return this.http.patch("http://localhost:8081/api/profile/picture", formData, { responseType: 'text' });
      }
      resetEmail(email:string) : Observable<any> {
        return this.http.post("http://localhost:8081/api/auth/mail/forgotpass", email);
      }
      resetPassword(password:string, link :string) : Observable<any> {
        return this.http.post("http://localhost:8081/api/auth/profile/resetpass?pass=" + link, password);
      }
  }