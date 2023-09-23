import { Component, ElementRef, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../services/auth-interceptor.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { HeaderComponent } from '../headercomponent/header.component';
@Component({
  selector: 'app-register',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  headerComponent!: HeaderComponent;
  constructor(private _authService: AuthService, private router: Router, private headerRef: ElementRef) { }
  ngOnInit() {
    this.headerComponent = this.headerRef.nativeElement as HeaderComponent;
  }
  user = { email: '', password: '' };
  onSubmit() {
    this._authService.login(this.user.email, this.user.password)
      .subscribe(token => {
        console.log("Token after login: ", token)
        this._authService.setToken(token);
        this._authService.loggedIn = true;
        this._authService.isMerchant().subscribe(data => {
          this._authService.merchant = data;
          window.location.reload();
          if (this._authService.merchant) {
            this.router.navigate(["/merchant/add-product"]);
          } else {
            this.router.navigate([""]);
          }
        });
      });
  }
  navigateToForgotPassword() {
    this.router.navigate(['/profile/forgotpass']);
  }



}
