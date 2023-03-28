import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'
import { AppComponent } from './components/mainpage/app.component';
import { MerchantService } from './services/merchant.service';
import { ProductService } from './services/product.service';
import { ShoppingCartService } from './services/shoppingcart.service';
import { IndexComponent } from './components/indexpage/index.component';
import { HeaderComponent} from './components/headercomponent/header.component'
import { OpenComponent } from './components/openingpage/open.component'
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { RouterModule } from '@angular/router';
import {AppRoutingModule} from './app-routing.module'

@NgModule({
  declarations: [
      IndexComponent,
      TestcomponentComponent,
      HeaderComponent,
      AppComponent,
      OpenComponent
  ],
  imports: [
    BrowserModule,AppRoutingModule,HttpClientModule
  ],
  providers: [MerchantService,ShoppingCartService,ProductService],
  bootstrap: [HeaderComponent]
})
export class AppModule { }
