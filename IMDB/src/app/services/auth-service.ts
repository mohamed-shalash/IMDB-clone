import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface JwtPayload {
  sub: string;
  role: string;
  exp: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  flag = true;
  isloggedin: boolean = false;
  status = 'register';

  private apiUrl = 'http://localhost:8080/auth';
  private tokenKey = 'authToken';

  constructor(private http: HttpClient) {
    const token = this.getToken();
    if (token) {
      this.isloggedin = true;
    }
  }

  flip() {
    this.flag = !this.flag;
    this.status = this.flag ? 'register' : 'login';
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/authenticate`, { username, password })
      .pipe(
        tap(response => {
          if (response.token) {
            localStorage.setItem(this.tokenKey, response.token);
            this.isAdmin();
          }
        })
      );
  }

  register(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { username, password })
      .pipe(
        tap(response => {
          if (response.token) {
            localStorage.setItem(this.tokenKey, response.token);
          }
        })
      );
  }




  private setToken(token: string) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  public getToken(): string | null {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem(this.tokenKey);
      return token;
    }
    return null;
  }


  logout() {
    this.isloggedin = false;
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.tokenKey);
    }
  }
  checkLogin(): boolean {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem(this.tokenKey);
      return !!token;
    }
    return false;
  }


  getRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      //console.log('Decoded JWT:', decoded.role);  
      return decoded.role === 'ROLE_ADMIN' ? 'admin' : 'user';
    } catch (err) {
      console.error('JWT Decode Error:', err);
      return null;
    }
  }

  isAdmin(): boolean {
    return this.getRole() === 'admin';
  }
  
  getUsername(): String | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      return decoded.sub;
    } catch (err) {
      console.error('JWT Decode Error:', err);
      return null;
    }
  }
}