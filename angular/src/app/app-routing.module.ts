import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './components/mainpage/app.component';
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { OpenComponent } from './components/openingpage/open.component'
import { LoginComponent } from './login/login.component';
import { MerchantOverviewComponent } from './components/merchantoverview/merchantoverview.component'
import { CustomerRegisterComponent } from './customer-register/customer-register.component';

const routes: Routes = [
  { path: '', component: OpenComponent },
  { path: 'merchant', component: AppComponent },
  { path: 'm/orders', component: MerchantOverviewComponent },
  { path: 'login', component: LoginComponent },
  { path: 'customer/register', component: CustomerRegisterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
