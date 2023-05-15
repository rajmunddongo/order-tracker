import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http: HttpClient) { }

  uploadFile(file: File, name: string) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', name); // Append the name field
  
    return this.http.post('http://localhost:8081/api/indexphoto/upload', formData);
  }
  

}
