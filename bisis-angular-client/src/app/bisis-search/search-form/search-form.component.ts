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
  // 1st Form
  radioValue: string;
  // 2nd Form
  prefix1: SelectItem;
  prefix2: string;
  prefix3: string;
  prefix4: string;
  prefix5: string;
  prefix6: string;

  constructor( public bisisService: BisisSearchService) {
    this.searchChoices = [];
    this.searchChoices.push({label: 'Universal', value: 'universal'});
    this.searchChoices.push({label: 'Author', value: 'author'});
    this.searchChoices.push({label: 'Title', value: 'title'});
    this.searchChoices.push({label: 'Keyword', value: 'keyword'});
    this.radioValue = 'universal';
    this.prefix1 = {label: 'Author', value: 'AU'};
  }

  search() {
    this.bisisService.getRecordsEP()
    .subscribe(
      response => this.serviceCallResult.emit(response),
      error => console.log(error)
    );
  }

  ngOnInit() {
  }

}
