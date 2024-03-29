import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    const authToken = this.authService.getToken();
    console.log("Intercepting token:"+authToken?.access_token)
    if (authToken && authToken.refresh_token) {
      const authRequest = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${authToken.access_token}`)
      });
      return next.handle(authRequest);
    } else {
      return next.handle(request);
    }
  }
}
