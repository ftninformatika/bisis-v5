import { Component, OnInit } from '@angular/core';
import {AuthHelper} from "../auth/utilities/authhelper";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  constructor(public ah: AuthHelper) { }


  ngOnInit() {

  }

}
