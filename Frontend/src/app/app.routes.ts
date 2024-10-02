import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LoginSuccessComponent } from './login-success/login-success.component';
import { ChatComponent } from './chat/chat.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'loginSuccess', component: LoginSuccessComponent },
  { path: 'chat/:id', component: ChatComponent },
  { path: '**', redirectTo: 'login' }
];
