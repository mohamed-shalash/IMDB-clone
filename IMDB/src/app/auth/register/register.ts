import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule],
  standalone: true,  
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
username: string = '';
password: string = ''
confirmPassword: string = '';
error: string = '';



  constructor(public authService: AuthService, private router: Router) {}


  onRegister() {
    if(this.password !== this.confirmPassword) {
      this.error = "Passwords do not match";
      return;
    }
    this.authService.register(this.username, this.password).subscribe({
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
