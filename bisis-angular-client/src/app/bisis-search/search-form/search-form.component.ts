import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BisisSearchService } from '../service/bisis-search.service';
import {SelectItem} from 'primeng/primeng';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  @Output() serviceCallResult: EventEmitter<any> = new EventEmitter();
  searchChoices: SelectItem[];
  selectedChoice: any;

  constructor( public bisisService: BisisSearchService) {
    this.searchChoices = [];
    this.searchChoices.push({label: 'Universal', value: 'universal'});
    this.searchChoices.push({label: 'Author', value: 'author'});
    this.searchChoices.push({label: 'Title', value: 'title'});
    this.searchChoices.push({label: 'Keyword', value: 'keyword'});
    this.selectedChoice = 'universal';
  }

  search() {
    this.bisisService.fullSearch()
    .subscribe(
      response => this.serviceCallResult.emit(response),
      error => console.log(error)
    );
  }

  ngOnInit() {
  }

}
