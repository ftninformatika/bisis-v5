import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  someText: string;

  constructor() { }

  get user(): string {
    return localStorage.getItem('authenticated');
  }

  ngOnInit() {
    console.log(localStorage);
    this.someText = JSON.parse(localStorage.getItem('shortInfo')).username;
  }

}
