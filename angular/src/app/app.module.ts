import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'
import { AppComponent } from './components/mainpage/app.component';
import { MerchantService } from './services/merchant.service';
import { ProductService } from './services/product.service';
import { ShoppingCartService } from './services/shoppingcart.service';
import { OrderService } from './services/order.service';
import { HeaderComponent} from './components/headercomponent/header.component'
import { LoginComponent } from './login/login.component';
import { OpenComponent } from './components/openingpage/open.component'
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { MerchantOverviewComponent } from './components/merchantoverview/merchantoverview.component';
import { RouterModule } from '@angular/router';
import {AppRoutingModule} from './app-routing.module'
import { FormsModule } from '@angular/forms';
import { CustomerRegisterComponent } from './components/customer-register/customer-register.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { CheckoutPageComponent } from './components/checkout-page/checkout-page.component';

@NgModule({
  declarations: [
      TestcomponentComponent,
      HeaderComponent,
      AppComponent,
      OpenComponent,
      MerchantOverviewComponent,
      LoginComponent,
      CustomerRegisterComponent,
      AdminPageComponent,
      CheckoutPageComponent
  ],
  imports: [
    BrowserModule,AppRoutingModule,HttpClientModule,FormsModule
  ],
  providers: [MerchantService,ShoppingCartService,ProductService,OrderService],
  bootstrap: [HeaderComponent]
})
export class AppModule { }
