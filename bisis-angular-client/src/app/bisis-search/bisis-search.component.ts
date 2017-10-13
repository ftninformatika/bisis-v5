import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css']
})
export class BisisSearchComponent implements OnInit {

  searchResults: any[];
  lib: string;

  constructor(private route:ActivatedRoute) {
    this.searchResults = [];
    this.lib ='';
  }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
    this.lib = params['lib'];
    console.log("biblioteka " + this.lib);
    }
    );

  }

  setRecords(event) {
    this.searchResults = event;
    console.log(this.searchResults);
  }

}
