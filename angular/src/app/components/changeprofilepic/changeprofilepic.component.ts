import { Component, OnInit } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { FileUploadComponent } from '../file-upload/file-upload.component';
import { FileManagerService } from 'src/app/services/fileupload.service';
import { v4 as uuidv4 } from 'uuid';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { HttpParams } from '@angular/common/http';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-changeprofilepic',
  templateUrl: './changeprofilepic.component.html',
  styleUrls: ['./changeprofilepic.component.scss']
})

export class ChangeprofilepicComponent implements OnInit {

  constructor(private authService: AuthService, private fileUploadService : FileManagerService, private userService : UserService,private sanitizer: DomSanitizer){ };
  public successfulUpload :boolean =false;
  public unSuccessfulUpload :boolean =false;
  selectedFile: File | undefined;
  isFileSelected: boolean = false;
  maxFileNameLength: number = 10;
  imgSource : SafeUrl = "";
  user: User | undefined;
  ngOnInit(): void {
    this.authService.whoami().subscribe(data => {
      this.user = data;
      this.fileUploadService.downloadFile(data.profilePicture).subscribe(data => {
        this.imgSource = this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + data)
      });
    })
  }

  //this.authService.whoami().subscribe(data => {
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    const truncatedFileName = fileName.substring(0, this.maxFileNameLength) + "(...)"+ fileName.substring(fileName.length-4, fileName.length);
    document.getElementById('file-name')!.textContent = truncatedFileName;
    this.isFileSelected = true;
  }

  onSave(name:string) : Observable<String> {
    if (!this.selectedFile) {
      throw console.error("Error while uploading");
      ;
    }
    return this.fileUploadService.uploadFile(this.selectedFile,name);
  }

  public submitForm() {
    const uuid = uuidv4();
    let picname = uuid;
    console.log("ImgSource:", picname);
    let num  =this.user? this.user.id: 1;
    this.onSave(picname)
    .pipe(
      switchMap((result) => this.userService.changeProfilePic(num,picname)))
    .subscribe(
      (response) => {
        console.log('Img added successfully', response);
        this.successfulUpload= true;
        this.unSuccessfulUpload=false;
        return;
      },
      (error) => {
        console.error('Error while adding img', error);
      }
    );
    if(this.successfulUpload == false)this.unSuccessfulUpload=true;
    this.successfulUpload= false;
  }

}
