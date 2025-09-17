import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MovieService } from '../../../services/movie-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movie-search',
  imports: [CommonModule, FormsModule],
  templateUrl: './movie-search.html',
  styleUrls: ['./movie-search.css'] 
})
export class MovieSearch {
  searchTerm: string = '';

  allMovies: any[] = [  ];

  constructor(private movieService: MovieService) {}

  searchMovies(searchTerm: string) {
    this.movieService.search(searchTerm).subscribe( {
      next: (movies) => {
        this.allMovies = movies;
          console.log('Movies fetched:', movies);
      },
      error: (err) => console.error('Error fetching movies:', err)
    });
  }

  addMovie(addMovie: string) {
    this.movieService.addMovie(addMovie).subscribe({
      next: (response) => {
        console.log('Movie added successfully:', response);
        alert('Movie added successfully!');
      },
      error: (err) => console.error('Error adding movie:', err)
    });
  }
}
