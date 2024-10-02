import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { Message } from '../models/message';



@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private serverUrl = 'http://localhost:8080/ws';
  private stompClient: any;
  private messageSubject = new Subject<Message>();

  connect(): void {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame: any) => {
      this.stompClient.subscribe('/topic/public', (message: any) => {
        if (message.body) {
          this.messageSubject.next(JSON.parse(message.body));
        }
      });
    });
  }


  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  sendMessage(message: Message): void {
    this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message));
  }

  getMessage(): Observable<Message> {
    return this.messageSubject.asObservable();
  }


}
