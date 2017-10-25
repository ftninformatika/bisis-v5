import {Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import { BisisSearchService } from '../service/bisis-search.service';
import {SelectItem} from 'primeng/primeng';


@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  @Input() lib: string;
  @Input() selectedLibrary: string;
  @Output() serviceCallResult: EventEmitter<any> = new EventEmitter();
  // 1st Form
  searchText1: string;
  // 2nd Form
  allPrefixes: SelectItem[];
  bondings: SelectItem[];
  bonding1: string;
  bonding2: string;
  bonding3: string;
  bonding4: string;
  prefix1: string;
  prefix2: string;
  prefix3: string;
  prefix4: string;
  prefix5: string;
  text1: string;
  text2: string;
  text3: string;
  text4: string;
  text5: string;




  search() {
    this.bisisService.getRecordsEP();

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

  setLib(lib) {
    this.selectedLibrary = lib.value;
    console.log(this.lib, this.selectedLibrary);
  }

  private validateQuery(choice, text): boolean {
    if (text === '' || text === undefined || text === null || (choice != 'universal'
        && choice != 'author' && choice != 'title' && choice != 'keyword' )) {
      console.log('Query error');
      console.log('SearchBy ' + choice + ':' + text);
      return false;
    }
    return true;
  }

  ngOnInit() {
  }

  searchAdvanced(text1,text2,text3,text4,text5,prefix1,prefix2,prefix3,prefix4,prefix5,bonding1,bonding2,bonding3,bonding4) {
    var searchModel = {
      pref1: prefix1,
      pref2: prefix2,
      pref3: prefix3,
      pref4: prefix4,
      pref5: prefix5,
      text1: text1,
      text2: text2,
      text3: text3,
      text4: text4,
      text5: text5,
      oper1: bonding1,
      oper2: bonding2,
      oper3: bonding3,
      oper4: bonding4
    };
    this.bisisService.searchRecordAdvanced(searchModel)
        .subscribe(
            response => this.serviceCallResult.emit(response),
            error => console.log(error)
        );
    console.log(searchModel);
  }

  constructor( public bisisService: BisisSearchService) {
    this.prefix1 = "AU";
    this.prefix2 = "au";
    this.prefix3 = "au";
    this.prefix4 = "au";
    this.prefix5 = "au";
    this.bonding1 = "and";
    this.bonding2 = "and";
    this.bonding3 = "and";
    this.bonding4 = "and";
    this.allPrefixes = [];
    this.bondings = [];
    this.allPrefixes.push({label: 'Аутор', value: 'AU'});
    this.allPrefixes.push({label: 'Наслов', value: 'TI'});
    this.bondings.push({label: 'AND', value: 'and'});
    this.bondings.push({label: 'OR', value: 'or'});
    this.bondings.push({label: 'NOT', value: 'not'});

  }

}
