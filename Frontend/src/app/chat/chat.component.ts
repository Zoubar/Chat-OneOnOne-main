import { Component, OnDestroy, OnInit, ChangeDetectorRef } from '@angular/core';
import { Message } from '../models/message';
import { Chat } from '../models/chat';
import { User } from '../models/user';
import { WebSocketService } from '../services/web-socket.service';
import { AuthService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChatService } from '../services/chat.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

  messages: Message[] = [];
  newMessage = '';
  user: User | null = null;
  chatId: string | null = null;
  chat: Chat | null = null;

  constructor(
    private webSocketService: WebSocketService,
    private authService: AuthService,
    private chatService: ChatService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.authService.getUser().subscribe(
      (data: User) => {
        this.user = data;
        console.log('User authenticated:', this.user);
        this.tryLoadChat();
      },
      (error) => {
        console.error('Error:', error);
      }
    );

    this.route.paramMap.subscribe(params => {
      this.chatId = params.get('id');
      console.log('Chat ID from route:', this.chatId);
      this.tryLoadChat();
    });

    this.webSocketService.connect();
    this.webSocketService.getMessage().subscribe((message: Message) => {
      this.messages.push(message);
      this.cdr.detectChanges();
    });
  }

  ngOnDestroy(): void {
    this.webSocketService.disconnect();
  }

  tryLoadChat(): void {
    if (this.chatId) {
      this.loadChat();
    } else {
      console.warn('Chat ID not initialized yet');
    }
  }

  loadChat(): void {
    if (this.chatId) {
      console.log('Loading chat by ID:', this.chatId);
      this.chatService.getChatById(this.chatId).subscribe(
        (chat: Chat) => {
          this.chat = chat;
          this.messages = chat.messages; // Directly assign messages from the chat object
          console.log('Chat loaded by ID:', this.chat);
          console.log('Messages loaded:', this.messages);
          this.cdr.detectChanges(); // Ensure Angular detects the changes
        },
        (error) => {
          console.error('Error loading chat:', error);
        }
      );
    } else {
      console.warn('Chat not loaded due to missing chatId');
    }
  }

  sendMessage(): void {
    console.log('Send message called');
    console.log('Current user:', this.user);
    console.log('Current chat:', this.chat);
    console.log('New message:', this.newMessage);

    if (this.newMessage && this.user && this.chat) {
      const message: Message = {
        content: this.newMessage,
        sender: this.user,
        chat: this.chat,
        id: 0,
        timestamp: new Date()
      };

      console.log('Message to be sent:', message);
      this.webSocketService.sendMessage(message);
      this.newMessage = '';
      this.messages.push(message); // Add the new message to the messages array
      this.cdr.detectChanges(); // Ensure Angular detects the changes
    } else {
      console.error('Message not sent due to missing data');
    }
  }
}
