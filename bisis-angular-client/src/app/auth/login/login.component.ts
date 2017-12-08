import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { contentHeaders } from '../headers';
import {MessageService} from "primeng/components/common/messageservice";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public router: Router, public http: Http, private messageService: MessageService) {
  }

  login(event, username, password) {
    username = 'jiricekova31@gmail.com';
    //password = 'member1';
    event.preventDefault();
    let body = JSON.stringify({ username, password });
    this.messageService.clear();
    this.http.post('/memauth', body, { headers: contentHeaders })
      .subscribe(
        response => {
          localStorage.clear();
          localStorage.setItem('token', response.json().token);
          localStorage.setItem('authenticated', response.json().member_info.username);
          localStorage.setItem('authenticatedUserId', response.json().member_info.index);;
          localStorage.setItem('authenticatedUserLib', response.json().member_info.libraryPrefix);
          localStorage.setItem('shortInfo', JSON.stringify(response.json().member_info));
            this.messageService.add({
                severity: 'info',
                summary: 'Обавештење',
                detail: 'Успешно сте се пријавили на БИСИС!'
            });
          this.router.navigate(['/']);
        },
        error => {
            this.messageService.add({
                severity: 'error',
                summary: 'Грешка',
                detail: 'Неуспело пријављивање!'
            });
        }
      );
  }



  logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('shortInfo');
  }

  forgotPass(){
      this.router.navigate(['/forgot-pass'])
  }

  ngOnInit() {
  }

}
