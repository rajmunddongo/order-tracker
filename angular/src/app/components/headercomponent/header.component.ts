import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { LoadingService } from 'src/app/services/loading.service';
import { SearchService } from 'src/app/services/search.service';


@Component({
  selector: 'app-root',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private searchService:SearchService,public authService: AuthService, private router: Router, private fileUploadService:FileManagerService, private sanitizer:DomSanitizer,public loadingService: LoadingService) { }
  public isMerchant: boolean = false
  public user  : User | undefined;
  public imgSource:SafeUrl = "";
  public settingsIcon:SafeUrl="";
  public merchanticon:SafeUrl="";
  public shoppingcarticon:SafeUrl="";
    public deliveryicon:SafeUrl="";
    searchQuery: string = '';

  ngOnInit() {
    let numofRequests=4;
    let doneRequests= 0;
    this.authService.whoami().subscribe(data => {
      this.user = data;
      this.fileUploadService.downloadFile(data.profilePicture).subscribe(data => {
        this.imgSource = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
        doneRequests++;
        this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
      },
      error => {
        console.error('Error fetching picture:', error);
        doneRequests++;
        this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
      });
    });
    this.fileUploadService.downloadFile("settingsicon").subscribe(data => {
      this.settingsIcon = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    },
    error => {
      console.error('Error fetching picture:', error);
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    });
    this.fileUploadService.downloadFile("merchanticon").subscribe(data => {
      this.merchanticon = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    },
    error => {
      console.error('Error fetching picture:', error);
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    });
          this.fileUploadService.downloadFile("shoppingcarticon").subscribe(data => {
      this.shoppingcarticon = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    },
    error => {
      console.error('Error fetching picture:', error);
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    });
          this.fileUploadService.downloadFile("deliveryicon").subscribe(data => {
      this.deliveryicon = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    },
    error => {
      console.error('Error fetching picture:', error);
      doneRequests++;
      this.checkIfAllRequestsCompleted(numofRequests,doneRequests);
    });
    this.authService.isLoggedin().subscribe(result => {
      this.authService.loggedIn = result;
      if(!result) this.user = undefined;
    });
    this.authService.isMerchant().subscribe(result => {
      this.authService.merchant = result;
    });
  }
  logout(): void {
    sessionStorage.removeItem('access');
    sessionStorage.removeItem('refresh');
    this.authService.loggedIn = false;
    this.router.navigate(['/']);
  }
  private checkIfAllRequestsCompleted(completedRequests:number, totalRequest:number): void {
    if (completedRequests === totalRequest) {
      this.loadingService.stopLoading();
    }
  }
  onSearch() {
    this.searchService.setSearchQuery(this.searchQuery);
    console.log('Search query:', this.searchQuery);
  }
  
}