import { Component } from '@angular/core';
import { AuthService } from '../services/auth-service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {

  constructor(public authService: AuthService,public router: Router) {}

  openLogoutDialog() {
    const dialog = document.getElementById('logoutDialog') as HTMLDialogElement;
    dialog?.showModal();
  }

  closeDialog() {
    const dialog = document.getElementById('logoutDialog') as HTMLDialogElement;
    dialog?.close();
  }

  confirmLogout() {
    this.closeDialog();
    console.log('User logged out ');
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
