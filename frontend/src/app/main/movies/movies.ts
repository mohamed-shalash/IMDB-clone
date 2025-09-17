import { Component } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../services/movie-service';

@Component({
  selector: 'app-movies',
  imports: [CommonModule,FormsModule],
  templateUrl: './movies.html',
  styleUrl: './movies.css'
})
export class Movies {
  movies: any[] = [];
  searchTerm: string = '';
  currentPage: number = 1;
  pageSize: number = 4;
  totalPages: number = 1;

  constructor(public authService: AuthService,public router: Router,public movieService: MovieService) {}
  ngOnInit(): void {
    if (!this.authService.checkLogin()) {
      this.router.navigate(['/login']);
    }
    if (typeof window !== 'undefined') {
        this.loadMovies(0, 4);
        this.loadMoviesCount();
    }
  }

  loadMovies(page: number = 1, pageSize: number = this.pageSize) {
    this.movieService.getAllMovies(page, pageSize).subscribe({
      next: (data) => this.movies = data,
      error: (err) => {
        if (err.status === 403) {
          this.router.navigate(['/login']);
        }
      }
    });
  }

  loadMoviesCount() {
    this.movieService.getMoviesCount().subscribe({
      next: (data) => this.totalPages = Math.ceil(data / this.pageSize),
      error: (err) => console.error('Error fetching movie count:', err)
    });
  }

  searchMovies(term: string) {
    if (term.trim() === '') {
      this.loadMovies(0, this.pageSize);
      return;
    }
    this.movieService.searchMovies(term).subscribe({
      next: (data) => this.movies = data,
      error: (err) => {
        if (err.status === 403) {
          alert('Session expired. Please log in again.');
          this.router.navigate(['/login']);
        }
      }
    });
  }

  removeMovie(imdbID: string) {
    const flag=confirm('Are you sure you want to remove this movie?');
    if (flag) {
      this.movieService.deleteMovie(imdbID).subscribe({
        next: () => {
          this.loadMovies(this.currentPage-1, this.pageSize);
          this.loadMoviesCount();
        },
        error: (err) => {
          if (err.status === 403) {
            alert('Session expired. Please log in again.');
            this.router.navigate(['/login']);
          }else{
            console.error('Error deleting movie:', err);
          }
        }
      });
    }
  }

  addMovie() {
    this.router.navigate(['/add-movie']);
  }

  goToMovie(imdbID: string) {
    this.router.navigate(['/movie', imdbID]);
  }



}
