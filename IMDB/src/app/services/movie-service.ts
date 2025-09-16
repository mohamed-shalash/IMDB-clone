import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth-service';
import { Observable } from 'rxjs';


export interface IMDB {
  imdbId: string;
  title: string;
  year: string;
  type: string;
  poster: string;
}

export interface Movie {
  imdbId: string;
  title: string;
  year: string;
  type: string;
  poster: string;
}

@Injectable({
  providedIn: 'root'
})
export class MovieService {

   private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private authService: AuthService) {}


  search(name: string): Observable<IMDB[]> {
    const token = this.authService.getToken(); 
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    console.log('Fetching movies with token:', token);

    return this.http.get<IMDB[]>(`${this.apiUrl}/omdb/search?title=${name}`, { headers });
  }


  addMovie(id: string): Observable<IMDB[]> {
      const token = this.authService.getToken(); 
      console.log('Retrieved token:', token);
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}` 
      });

      console.log('Fetching movies with token:', token);

      return this.http.post<IMDB[]>(`${this.apiUrl}/movies/add`, { 'imdbId': id }, { headers });
  }


  getAllMovies(page: number, pageSize: number): Observable<Movie[]> {
    const token = this.authService.getToken(); 
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    console.log('Fetching movies with token:', token);

    return this.http.get<Movie[]>(`${this.apiUrl}/movies/all?page=${page}&size=${pageSize}`, { headers });
  }

  getMoviesById(id: string): Observable<Movie> {
    const token = this.authService.getToken(); 
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    console.log('Fetching movies with token:', token);

    return this.http.get<Movie>(`${this.apiUrl}/movies/find/${id}`, { headers });
  }

  getMoviesCount(): Observable<number> {
    const token = this.authService.getToken();
    console.log('Retrieved token:', token);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}` 
    });

    return this.http.get<number>(`${this.apiUrl}/movies/count`, { headers });
  }

  searchMovies(term: string): Observable<Movie[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<Movie[]>(`${this.apiUrl}/movies/search/${term}`, { headers });
  }

  deleteMovie(imdbID: string): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete(`${this.apiUrl}/movies/remove/${imdbID}`, { headers });
  }
}



