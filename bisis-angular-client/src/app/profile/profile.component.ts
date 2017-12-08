import { Component, OnInit } from '@angular/core';
import {SelectItem} from "primeng/primeng";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  demos: SelectItem[];
  selected: any;

  constructor() {
    this.demos = [];
    this.demos.push({label:'Select City', value:null});
    this.demos.push({label:'Select City', value:null});
    this.demos.push({label:'Select City', value:null});
    this.demos.push({label:'Select City', value:null});
    this.demos.push({label:'Select City', value:null});
  }

  ngOnInit() {

  }

}
