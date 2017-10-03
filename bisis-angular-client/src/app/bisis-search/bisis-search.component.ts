import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css']
})
export class BisisSearchComponent implements OnInit {

  searchResults: any[];

  constructor() {
    this.searchResults = [];
   }

  ngOnInit() {
  }

  setRecords(event) {
    this.searchResults = event;
    console.log(this.searchResults);
  }

}
