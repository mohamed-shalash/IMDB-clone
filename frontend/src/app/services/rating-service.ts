import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth-service';

export interface Rate {
  id: string;
  score: string;
}

@Injectable({
  providedIn: 'root'
})
export class RatingService {
   private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private authService: AuthService) {}


  getAvarageRatings(id: string): Observable<number> {
    const token = this.authService.getToken();
    
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    return this.http.get<number>(`${this.apiUrl}/ratings/${id}`, { headers });
  }

  getMyRating(id: string): Observable<number> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<number>(`${this.apiUrl}/ratings/${id}/me`, { headers });
  }

  rateMovie(id: string, score: number): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });
    return this.http.post<any>(`${this.apiUrl}/ratings/${id}?score=${score}`,{}, { headers });
  }
}
