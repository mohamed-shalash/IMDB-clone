import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user-service';
import { Router } from '@angular/router';
import e from 'express';
import { AuthService } from '../../services/auth-service';

interface User {
  id: string;
  username: string;
  role: 'USER' | 'ADMIN';
}

@Component({
  selector: 'app-users',
  imports: [CommonModule, FormsModule],
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class Users {
  users: User[] = [];
  searchTerm: string = '';
  currentPage: number = 1;
  pageSize: number = 2;
  totalPages: number = 1;


  username: string = '';
  password: string = '';
  confirmPassword: string = '';
  

  constructor(private userService: UserService, private router: Router, public authService: AuthService) {
    
  }

  ngOnInit() {
    if (typeof window !== 'undefined') {
      this.loadInitialUsers();
    }
  }

  loadInitialUsers() {
    this.userService.getUsers(this.currentPage - 1, this.pageSize).subscribe({
      next: (data) => this.users = data,
      error: (err) => {
        if (err.status === 403) {
          this.router.navigate(['/movies']); 
        }
      }
    });
    this.userService.getCount().subscribe({
      next: (count) => {
        this.totalPages = Math.ceil(count / this.pageSize);
      }
    });
  }

  loadUsers(currentPage: number) {
    this.userService.getUsers(currentPage - 1, this.pageSize).subscribe({
      next: (data) => { this.users = data; console.log(data); },
      error: (err) => {
        console.error('Error loading users:', err);

        if (err.status === 403) {
          this.router.navigate(['/movies']); 
        }
      }
    });
    
  }

    findByUser(username: String) {
      if (username.trim() === '') {
        this.loadInitialUsers();
        return;
      }
      this.userService.findByUsername(username).subscribe({
        next: (data) => { 
          this.users = [data];
          this.totalPages = Math.ceil(this.users.length / this.pageSize); 
        },
        error: (err) => {
          if (err.status === 403) {
            this.router.navigate(['/movies']); 
          }
        }
    });
    
  }


  showPopup: boolean = false;

  editingUser: User | null = null;


  removeUser(id: string) {
      const deletion =confirm("Are you sure?");
      if(deletion){
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loadUsers(this.currentPage);
        },
        error: (err) => {
          console.error('Error deleting user:', err);
          alert("User not deleted");
        }
      });

      this.userService.getCount().subscribe({
        next: (count) => {
          this.totalPages = Math.ceil(count / this.pageSize);
        }
      });
    }
  }



  addUserPopup() {
    this.showPopup = true;
    this.editingUser = null;
    
  }

  saveUser(username: string, password: string) {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match!');
      return;
    }
    this.userService.saveUser(username, password).subscribe({
      next: (newUser) => {
        this.loadUsers(this.currentPage);
      },
      error: (err) => {
        console.error('Error saving user:', err);
        alert(err.error?.message || 'Error saving user');
      }
    });

    this.closePopup();
  }

  closePopup() {
    this.showPopup = false;
    this.username = '';
    this.password = '';
    this.confirmPassword = '';
    this.editingUser = null;
  }

}
