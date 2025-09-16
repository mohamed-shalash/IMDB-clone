import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth-service';
import { Observable } from 'rxjs';

export interface User {
  id: string;
  username: string;
  role: 'USER' | 'ADMIN';
}


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getUsers(page: number = 0, size: number = 10): Observable<User[]> {
    const token = this.authService.getToken(); 
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    console.log('Fetching users with token:', token);

    return this.http.get<User[]>(`${this.apiUrl}/find/all?page=${page}&size=${size}`, { headers });
  }


  getCount(): Observable<number> {
    const token = this.authService.getToken(); 
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    console.log('Fetching users with token:', token);

    return this.http.get<number>(`${this.apiUrl}/find/count`, { headers });
  }


  saveUser(username: string, password: string): Observable<User> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    const body = { username, password };
    return this.http.post<User>(`${this.apiUrl}/admin/register`, body, { headers });
  }

  findByUsername(username: String): Observable<User> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<User>(`${this.apiUrl}/find/user/${username}`, { headers });
  }

  deleteUser(id: string): Observable<void> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
  }
}
