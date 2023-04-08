import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from '../../services/auth-interceptor.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  constructor(private _authService : AuthService,private router : Router) {}
  ngOnInit() {
  }
  user = { email: '', password: '' };  
onSubmit() {
  this._authService.login(this.user.email, this.user.password)
    .subscribe(token => {
      console.log("Token after login: ",token)
      this._authService.setToken(token);
    });
    this.router.navigate([""]);
}

}
