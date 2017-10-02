import { Component, OnInit } from '@angular/core';
import {DropdownModule} from 'primeng/primeng';
import {SelectItem} from 'primeng/primeng';
import {BisisPretragaService} from "../service/bisis-pretraga.service";

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css'],
  providers: [BisisPretragaService]
})
export class SearchFormComponent implements OnInit {

  searchChoices: SelectItem[];
  selectedSearchChoice: any[];

  results: any[];
  errorMessage: any;

  constructor(public bisisService: BisisPretragaService) {
    this.searchChoices = [];
    this.searchChoices.push({label:'Universal', value:"universal"});
    this.searchChoices.push({label:'Author', value:"author"});
    this.searchChoices.push({label:'Title', value:"title"});
    this.searchChoices.push({label:'Keyword', value:"keyword"});
   }

  ngOnInit() {
  }

  searchDummy(){
    this.bisisService.search()
    .subscribe(
      response =>{ this.results = response; console.log(response) },
      error => this.errorMessage = <any>error
    )
  }

}
