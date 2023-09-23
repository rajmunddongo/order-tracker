import { Component, OnInit } from '@angular/core';
import { FileManagerService } from 'src/app/services/fileupload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {

  selectedFile: File | undefined;
  isFileSelected: boolean = false;
  maxFileNameLength: number = 10;

  constructor(private fileUploadService: FileManagerService) { }

  ngOnInit(): void {
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    const fileName = this.selectedFile ? this.selectedFile.name : '';
    const truncatedFileName = fileName.substring(0, this.maxFileNameLength) + "(...)"+ fileName.substring(fileName.length-4, fileName.length);
    document.getElementById('file-name')!.textContent = truncatedFileName;
    this.isFileSelected = true;
  }

  onSave(name:string) {
    if (!this.selectedFile) {
      return;
    }

    this.fileUploadService.uploadFile(this.selectedFile,name).subscribe(
      (response) => {
        console.log('File uploaded successfully');
        // Do something after the file is uploaded
      },
      (error) => {
        console.error('File upload failed:', error);
      }
    );
  }

}