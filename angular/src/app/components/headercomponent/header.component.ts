import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';


@Component({
    selector: 'app-root',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
  export class HeaderComponent {

    constructor(public authService : AuthService) {}
  }