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
  // 1st Form
  searchText1: string;
  // 2nd Form
  prefix1: SelectItem;
  prefix2: string;
  prefix3: string;
  prefix4: string;
  prefix5: string;
  prefix6: string;

  constructor( public bisisService: BisisSearchService) {
    this.prefix1 = {label: 'Author', value: 'AU'};
    this.searchText1 = '';
  }

  search() {
    this.bisisService.getRecordsEP()
    .subscribe(
      response => this.serviceCallResult.emit(response),
      error => console.log(error)
    );
  }

  searchBy( text) {
    var choice = 'universal';
    if (!this.validateQuery(choice, text)) {
      return;
    }
    this.bisisService.searchRecordsByEP(choice, text)
    .subscribe(
      response => this.serviceCallResult.emit(response),
      error => console.log(error)
    );
  }

  private validateQuery(choice, text): boolean {
    // tslint:disable-next-line:triple-equals
    if (text === '' || text === undefined || text === null || (choice != 'universal'
    // tslint:disable-next-line:triple-equals
        && choice != 'author' && choice != 'title' && choice != 'keyword' )) {
      console.log('Query error');
      console.log('SearchBy ' + choice + ':' + text);
      return false;
    }
    return true;
  }

  ngOnInit() {
  }

}
