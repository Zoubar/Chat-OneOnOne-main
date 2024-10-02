import { Chat } from './chat';
import { User } from './user';

export interface Message {
  id: number;
  chat: Chat;
  sender: User;
  content: string;
  timestamp: Date;
}