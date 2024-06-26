import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileManagerService {

  constructor(private http: HttpClient) { }

  uploadFile(file: File, name: string) : Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', name);
  
    return this.http.post('http://localhost:8081/api/auth/indexphoto/upload', formData, { responseType: 'text' });
  }
  
  downloadFile(name: string): Observable<string> {
    return this.http.get('http://localhost:8081/api/auth/indexphoto/download/' + name, { responseType: 'text' });
  }
  

}
