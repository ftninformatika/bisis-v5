import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Http } from '@angular/http';
import { contentHeaders } from '../headers';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public router: Router, public http: Http) {
  }

  login(event, username, password) {
    username = 'jiricekova31@gmail.com';
    password = 'member1';
    event.preventDefault();
    let body = JSON.stringify({ username, password });
    this.http.post('/memauth', body, { headers: contentHeaders })
      .subscribe(
        response => {
          localStorage.clear();
          localStorage.setItem('token', response.json().token);
          localStorage.setItem('authenticated', response.json().member_info.username);
          localStorage.setItem('shortInfo', JSON.stringify(response.json().member_info));
          //console.log(response.json());
          this.router.navigate(['/']);
        },
        error => {
          alert(error.text());
          //console.log(error.text());
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
