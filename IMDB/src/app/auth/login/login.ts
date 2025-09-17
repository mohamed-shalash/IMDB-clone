import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

username: string = '';
password: string = '';
error: string = '';

  constructor(public authService: AuthService, private router: Router) {  }

onLogin() {
   if(this.username.trim() === '') {
      this.error = "Username is required";
      alert(this.error);
      return;
    }
    else if(this.password.trim() === '') {
      this.error = "Password is required";
      alert(this.error);
      return;
    }
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        this.router.navigate(['/movies']); 
      },
      error: (err) => {
        alert(err.error);
        //console.log(err);
      }
    });
  }
}


