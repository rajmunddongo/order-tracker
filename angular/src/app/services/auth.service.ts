import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
import { JwtType } from '../models/jwt.type'
import { User } from '../models/user.type';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  public loggedIn = false
  public merchant = false

  login(email: string, password: string): Observable<JwtType> {
    return this.http.post<JwtType>('http://localhost:8081/api/auth/authenticate', { email, password });
  }
  register(user : any) : Observable<JwtType> {
    return this.http.post<JwtType>('http://localhost:8081/api/auth/customer/register', user);
  }
  registerMerchant(user : any) : Observable<JwtType> {
    return this.http.post<JwtType>('http://localhost:8081/api/auth/merchant/register', user);
  }

  setToken(token: JwtType): void {
    console.log('setToken token:', { token });
    sessionStorage.setItem('access', token.access_token);
    sessionStorage.setItem('refresh', token.refresh_token);
  }

  whoami(): Observable<User> {
    return this.http.get<User>('http://localhost:8081/api/auth/whoami');
  }

  refreshToken() {
    this.http.post<JwtType>('http://localhost:8081/api/auth/refresh-token',"").subscribe(data=> this.setToken(data))
  }

  getToken(): JwtType | null {
    let access_token = sessionStorage.getItem('access');
    let refresh_token = sessionStorage.getItem('refresh');
    console.log('Getting token:', { access_token, refresh_token });
    if (access_token && refresh_token) {
      return { access_token, refresh_token };
    } else {
      return null;
    }
  }
  isLoggedin(): Observable<boolean> {
    console.log("checking loggedin");
    try {
      return  this.http.get<boolean>('http://localhost:8081/api/login');
    } catch (error) {
      console.error('Error occurred while checking login status:', error);
      return of(false);
    }
  }
  isMerchant(): Observable<boolean> {
    console.log("checking if merchant");
    try {
      return this.http.get<User>('http://localhost:8081/api/auth/whoami').pipe(
        map((user: User) => {
          if (user.role === 'MERCHANT') {
            return true;
          } else {
            return false;
          }
        }),
        catchError((error: any) => {
          console.error('Error occurred while checking login status:', error);
          return of(false);
        })
      );
    } catch (error) {
      console.error('Error occurred while checking login status:', error);
      return of(false);
    }
  }
  
  
  
  
}
