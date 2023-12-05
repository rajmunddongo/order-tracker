import { Component, OnInit } from '@angular/core';
import { Merchant } from 'src/app/models/merchant.type';
import { MerchantService } from 'src/app/services/merchant.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-open',
  templateUrl: './open.component.html',
  styleUrls: ['./open.component.scss']
})
export class OpenComponent implements OnInit {
  public merchantIcon: SafeUrl = "";
  public title = 'Angular';
  public merchants: Merchant[] = [];
  public totalRequest :number = 0;
  public completedRequests :number = 0;

  constructor(
    private _merchantService: MerchantService,
    private router: Router,
    private route: ActivatedRoute,
    private fileUploadService: FileManagerService,
    private sanitizer: DomSanitizer,
    public loadingService: LoadingService
  ) {}

  ngOnInit(): void {
    this.loadingService.startLoading();
    this._merchantService.getMerchants().subscribe(
      data => {
        this.merchants = data;
        this.totalRequest = data.length;
        this.merchants.forEach((merchant: Merchant) => {
          this.fileUploadService.downloadFile(merchant.picture).subscribe(
            iconData => {
              merchant.icon = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + iconData);
              this.completedRequests++;
              this.checkIfAllRequestsCompleted();
            },
            error => {
              console.error('Error fetching merchant icon:', error);
              this.loadingService.stopLoading();
            }
          );
        });
      },
      error => {
        console.error('Error fetching merchants:', error);
        this.loadingService.stopLoading();
      }
    );
  }

  private checkIfAllRequestsCompleted(): void {
    if (this.completedRequests === this.totalRequest) {
      this.loadingService.stopLoading();
    }
  }

  goToOtherPage(number: number) {
    this.router.navigate(['/merchant'], { queryParams: { number: number } });
  }
}
