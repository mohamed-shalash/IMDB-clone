import { Routes } from '@angular/router';
import { Movies } from './main/movies/movies';
import { Users } from './main/users/users';
import { Auth } from './auth/auth';
import { MovieSearch } from './main/movies/movie-search/movie-search';
import { ShowMovie } from './main/movies/show-movie/show-movie';

export const routes: Routes = [
    {path: 'movies',component: Movies},
    {path: 'users',component: Users},
    {path: 'login',component: Auth},
    { path: 'movie/:id', component: ShowMovie },
    {path: 'add-movie', component: MovieSearch},
    {path: '', redirectTo: 'login', pathMatch: 'full' }
];
