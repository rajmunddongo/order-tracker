import { Component, ElementRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { HeaderComponent } from '../headercomponent/header.component';

@Component({
  selector: 'app-merchant-register',
  templateUrl: './merchant-register.component.html',
  styleUrls: ['./merchant-register.component.scss']
})
export class MerchantRegisterComponent implements OnInit {
  headerComponent!: HeaderComponent;
  myuser!: User;
  constructor(private authService: AuthService, private router: Router, private headerRef: ElementRef) { }
  ngOnInit(): void {
    this.headerComponent = this.headerRef.nativeElement as HeaderComponent;
  }

  restaurantName: string = "";
  email: string = "";
  address: string = "";
  country: string = "";
  zipCode: string = "";
  password: string = "";
  passwordConfig: string = "";
  city: string = "";

  public submitForm() {
    console.log("RestaurantName:", this.restaurantName);
    console.log("Email:", this.email);
    console.log("Address:", this.address);
    console.log("Country:", this.country);
    console.log("City:", this.city);
    console.log("Zip code:", this.zipCode);
    console.log("Password:", this.password);
    console.log("Password config:", this.passwordConfig);
    var merchant = {
      email: this.email,
      password: this.password,
      profilePicture: "",
      merchant: {
        name: this.restaurantName,
        address: {
          address: this.address,
          zipCode: this.zipCode,
          country: this.country,
          city: this.city
        }
      },
      customer: null
    };
    this.authService.registerMerchant(merchant)
      .subscribe(token => {
        console.log("Token after login: ", token)
        this.authService.setToken(token);
      });
    this.authService.loggedIn = true;
    this.router.navigate(["/merchant/add-product"]);
  }

}
