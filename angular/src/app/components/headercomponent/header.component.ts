import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(public authService: AuthService,private router: Router) { }

  ngOnInit() {
    this.authService.isLoggedin().subscribe(result => {
      this.authService.loggedIn = result;
    });
    this.authService.isMerchant().subscribe(data=>
      this.authService.merchant=data
      )
  }
  logout(): void {
    sessionStorage.removeItem('access');
    sessionStorage.removeItem('refresh');
    this.authService.loggedIn=false;
    this.router.navigate(['/']);
  }

}