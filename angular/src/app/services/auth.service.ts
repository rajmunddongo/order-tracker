import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtType } from '../models/jwt.type'
import { User } from '../models/user.type';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<JwtType> {
    return this.http.post<JwtType>('http://localhost:8081/api/auth/authenticate', { email, password });
  }

  setToken(token: JwtType): void {
    console.log('setToken token:', { token });
    sessionStorage.setItem('access', token.access_token);
    sessionStorage.setItem('refresh', token.refresh_token);
  }

  whoami() : Observable<User> {
    return this.http.get<User>('http://localhost:8081/api/auth/whoami');
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
}
