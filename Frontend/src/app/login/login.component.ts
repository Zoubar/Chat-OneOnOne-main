import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Check if the user is already authenticated
    this.authService.getUserInfo().subscribe(
      (data: User) => {
        this.user = data;
        console.log('User authenticated:', this.user.username, '(', this.user.email, ')');
      },
      (error) => {
        console.error('Error:', error);
        // If the user is not authenticated, redirect to OAuth2 login
      }
    );
  }
}
