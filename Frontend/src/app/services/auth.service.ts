import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userInfoUrl = 'http://localhost:8080/api/user-info';
  private logoutUrl = 'http://localhost:8080/api/logout';
  private allUsersUrl = 'http://localhost:8080/api/users';
  private apiUrl = 'http://localhost:8080/api';



  constructor(private http: HttpClient) {}

  getUserInfo(): Observable<User> {
    return this.http.get<User>(this.userInfoUrl, { withCredentials: true });
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.allUsersUrl, { withCredentials: true });
  }


  getUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/user`, { withCredentials: true });
  }

  logout(): void {
    this.http.post(this.logoutUrl, {}, { withCredentials: true }).subscribe(
      () => {
        window.location.href = '/login';
      },
      (error) => {
        console.error('Logout failed:', error);
      }
    );
  }
}
