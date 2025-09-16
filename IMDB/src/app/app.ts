import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { AuthService } from './services/auth-service';

@Component({
  selector: 'app-root',
  imports: [CommonModule,Header,RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('IMDB');

  constructor(public authService: AuthService, private router: Router) {  }



}
