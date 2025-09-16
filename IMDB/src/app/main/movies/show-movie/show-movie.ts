import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Movie, MovieService } from '../../../services/movie-service';
import { RatingService } from '../../../services/rating-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-show-movie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './show-movie.html',
  styleUrls: ['./show-movie.css']
})
export class ShowMovie {
  movieId: string | null = null;
  movie: Movie | null = null;
  ratings: number = 0;
  userRating: number = 0;


  constructor(private route: ActivatedRoute,private authService: AuthService, private movieService: MovieService, private ratingService: RatingService) {}

  ngOnInit() {
    this.movieId = this.route.snapshot.paramMap.get('id');
    const token = this.authService.getToken();
      if (token) {
        this.loadMovieDetails(this.movieId);
        this.loadAverageRatings(this.movieId);
        this.loadMyRating(this.movieId);
      } else {
        console.log('User not logged in yet, skipping API calls.');
      }

  }

loadAverageRatings(movieId: string | null) {
  const token = this.authService.getToken();
  if (!token || !movieId) return;
  if (movieId) {
    this.ratingService.getAvarageRatings(movieId).subscribe({
      next: (data) => {
        this.ratings = data; 
        console.log('Average ratings loaded:', this.ratings);
      },
      error: (err) => console.error('Error loading average ratings:', err)
    });
  }
}

loadMyRating(movieId: string | null) {
  if (movieId) {
    this.ratingService.getMyRating(movieId).subscribe({
      next: (data) => {
        this.userRating = data; 
        console.log('User rating loaded:', this.userRating);
      },
      error: (err) => console.error('Error loading user rating:', err)
    });
  }
}


  loadMovieDetails(movieId: string | null) {
    if (movieId) {
      this.movieService.getMoviesById(movieId).subscribe({
        next: (data) => {
          this.movie = data;
        },
        error: (err) => {
          console.error('Error loading movie details:', err);
        }
      });
    }
  }



  rateMovie(stars: number) {
      if (this.movieId) {
        this.ratingService.rateMovie(this.movieId, stars).subscribe({
          next: (data) => {
            console.log('Movie rated successfully:', data);
            this.userRating = stars; 
            this.loadAverageRatings(this.movieId); 
          },
          error: (err) => {
            console.error('Error rating movie:', err);
          }
        });
    }
  }


  
}
