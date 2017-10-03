import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { BisisSearchService } from '../service/bisis-search.service';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  @Output() serviceCallResult: EventEmitter<any> = new EventEmitter();

  constructor( public bisisService: BisisSearchService) {
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
