import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchQuery: string = '';
  private searchQuerySubject: Subject<string> = new Subject<string>();

  setSearchQuery(query: string) {
    this.searchQuery = query;
    this.searchQuerySubject.next(query);
  }

  getSearchQuery(): string {
    return this.searchQuery;
  }

  getSearchQueryObservable(): Observable<string> {
    return this.searchQuerySubject.asObservable();
  }
}
