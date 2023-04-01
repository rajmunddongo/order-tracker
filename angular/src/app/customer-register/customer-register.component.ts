import { Component } from '@angular/core';

@Component({
  selector: 'app-customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.scss']
})
export class CustomerRegisterComponent {

  firstName: string = "";
  lastName: string = "";
  email: string = "";
  address: string = "";
  country: string = "";
  zipCode: string = "";
  password: string = "";
  passwordConfig:string = "";

  public submitForm() {
    console.log("First name:", this.firstName);
    console.log("Last name:", this.lastName);
    console.log("Email:", this.email);
    console.log("Address:", this.address);
    console.log("Country:", this.country);
    console.log("Zip code:", this.zipCode);
    console.log("Password:", this.password);
    console.log("Password config:", this.passwordConfig);
  }
  
}
