import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { routes } from './app.routes';

import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms'; // <-- Add this line

import { CommonModule } from '@angular/common';

import { LoginSuccessComponent } from './login-success/login-success.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginSuccessComponent,
    LoginComponent // Declare LoginComponent here
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
