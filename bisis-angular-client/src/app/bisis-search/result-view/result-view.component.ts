import { Component, OnInit, Input } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() data: any[];
  resultViewTypes: SelectItem[];
  selectedResultViewType: any;

  constructor() {
    this.resultViewTypes = [];
    this.resultViewTypes.push({label: 'Tabelar', value: 'table'});
    this.resultViewTypes.push({label: 'Data grid', value: 'data-grid'});
    this.resultViewTypes.push({label: 'Scroller', value: 'scroller'});
    this.resultViewTypes.push({label: 'List', value: 'list'});
    this.selectedResultViewType = 'table';

  }

  ngOnInit() {
  }

}
