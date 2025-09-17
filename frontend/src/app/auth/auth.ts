import { Component } from '@angular/core';
import { Login } from './login/login';
import { Register } from './register/register';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth-service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-auth',
  imports: [ Login, Register,CommonModule],
  standalone: true,
  templateUrl: './auth.html',
  styleUrl: './auth.css'
})
export class Auth {
   constructor(public authService: AuthService, private router: Router) {  }

  ngOnInit(): void {  
    if (this.authService.isloggedin) {
      this.router.navigate(['/movies']); 
    } 
  }
}
