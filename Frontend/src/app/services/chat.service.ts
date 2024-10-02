import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Chat } from '../models/chat';
import { Message } from '../models/message';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getChatBetweenUsers(userId1: string, userId2: string): Observable<Chat> {
    return this.http.get<Chat>(`${this.apiUrl}/chats/between/${userId1}/${userId2}`);
  }

  getChatMessages(chatId: string): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/chats/${chatId}/messages`, { withCredentials: true });
  }

  createChatWithMessage(user2Id: number, messageContent: string): Observable<Chat> {
    const payload = { user2Id, messageContent };
    return this.http.post<Chat>(`${this.apiUrl}/chat-with-message`, payload, { withCredentials: true });
  }

  getUserChats(): Observable<Chat[]> {
    return this.http.get<Chat[]>(`${this.apiUrl}/chats`, { withCredentials: true });
  }


  getChatById(chatId: string): Observable<Chat> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Chat>(`${this.apiUrl}/chats/${chatId}`, { headers, withCredentials: true });
  }






}
