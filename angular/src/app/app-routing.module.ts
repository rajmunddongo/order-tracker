import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './components/mainpage/app.component';
import { TestcomponentComponent } from './components/testcomponent/testcomponent.component';
import { IndexComponent } from './components/indexpage/index.component'
import { OpenComponent } from './components/openingpage/open.component'

const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '', component: OpenComponent },
  { path: 'merchant', component: AppComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
