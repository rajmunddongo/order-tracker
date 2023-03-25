import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http'
import { AppComponent } from './components/mainpage/app.component';
import { MerchantService } from './services/merchant.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, HttpClientModule
  ],
  providers: [MerchantService],
  bootstrap: [AppComponent]
})
export class AppModule { }
