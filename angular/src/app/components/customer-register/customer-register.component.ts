import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.scss']
})
export class CustomerRegisterComponent {

  myuser!: User; 
  constructor(private authService:AuthService,private router:Router) {}
  firstName: string = "";
  lastName: string = "";
  email: string = "";
  address: string = "";
  country: string = "";
  zipCode: string = "";
  password: string = "";
  passwordConfig:string = "";
  city:string = "";

  public submitForm() {
    console.log("First name:", this.firstName);
    console.log("Last name:", this.lastName);
    console.log("Email:", this.email);
    console.log("Address:", this.address);
    console.log("Country:", this.country);
    console.log("City:", this.city);
    console.log("Zip code:", this.zipCode);
    console.log("Password:", this.password);
    console.log("Password config:", this.passwordConfig);
    var user  = {
      firstname: this.firstName,
      lastname: this.lastName,
      email: this.email,
      password: this.password,
      profilePicture:"",
      customer: {
        address: {
          address: this.address,
          zipCode: this.zipCode,
          country: this.country,
          city: this.city
        }
      },
      merchant:null
    };
      this.authService.register(user)
        .subscribe(token => {
          console.log("Token after login: ", token)
          this.authService.setToken(token);
        });
      this.authService.loggedIn=true;
      this.router.navigate([""]);
  }
  
}
