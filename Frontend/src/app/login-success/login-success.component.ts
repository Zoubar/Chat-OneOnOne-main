import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { User } from '../models/user';
import { ChatService } from '../services/chat.service';
import { Chat } from '../models/chat';

@Component({
  selector: 'app-login-success',
  templateUrl: './login-success.component.html',
  styleUrls: ['./login-success.component.css']
})
export class LoginSuccessComponent implements OnInit {
  user: User | null = null;
  users: User[] = [];
  chats: Chat[] = [];


  constructor(
    private authService: AuthService,
    private chatService: ChatService, 
    private router: Router) {}

  ngOnInit(): void {
    this.authService.getUserInfo().subscribe(
      (data: User) => {
        this.user = data;
        console.log('User authenticated:', this.user.username, '(', this.user.email, ')');
      },
      (error) => {
        console.error('Error:', error);
      }
    );
    this.authService.getAllUsers().subscribe(
      (users: User[]) => {
        this.users = users;
      },
      (error) => {
        console.error('Error:', error);
      }
    );
    this.chatService.getUserChats().subscribe(
      (chats: Chat[]) => {
        this.chats = chats;
      },
      (error) => {
        console.error('Error:', error);
      }
    );
  }

  logout(): void {
    this.authService.logout();
  }

  startChat(user2: User): void {
    const messageContent = "Hello! Let's start a chat.";
    console.log('Chat', user2.id, messageContent);

    this.chatService.createChatWithMessage(user2.id, messageContent).subscribe(
      (chat: Chat) => {
        this.router.navigate(['/chat', chat.id]);
      },
      (error) => {
        console.error('Error creating chat:', error);
      }
    );
  }

  navigateToChat(chatId: number): void {
    this.router.navigate(['/chat', chatId]);
  }



}
