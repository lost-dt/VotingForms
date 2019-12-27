import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, map, first } from 'rxjs/operators';
import { BehaviorSubject, Observable, throwError } from 'rxjs';

import { Admin } from '../shared/admin.model';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private currentAdminSubject: BehaviorSubject<Admin>;
  public currentAdmin: Observable<Admin>;

  constructor(private http: HttpClient) {
    this.currentAdminSubject = new BehaviorSubject<Admin>(JSON.parse(localStorage.getItem('currentAdmin')));
    this.currentAdmin = this.currentAdminSubject.asObservable();
  }

  public get currentUserValue(): Admin {
    return this.currentAdminSubject.value;
  }

  login(username: string, password: string): Observable<any> {
    return this.http.get<Admin>(`api/admin_user/username_${username}`).pipe(first()).pipe(catchError(this.handleError)).pipe(map(admin => {
      if (admin && admin.password === password) {
        localStorage.setItem('currentAdmin', JSON.stringify(admin));
        this.currentAdminSubject.next(admin);
        return admin;
      } else if (!admin) {
        return new Error('Invalid username!');
      } else if (admin.password !== password) {
        return new Error('Invalid password!');
      }
    }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentAdmin');
    this.currentAdminSubject.next(null);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  }
}
