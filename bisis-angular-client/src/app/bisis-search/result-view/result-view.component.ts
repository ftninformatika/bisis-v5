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
    this.resultViewTypes.push({label: 'Tabelarno', value: 'table'});
    this.resultViewTypes.push({label: 'Lista', value: 'table'});
    this.resultViewTypes.push({label: 'Grid', value: 'table'});
    this.resultViewTypes.push({label: 'Carousel', value: 'table'});
    this.selectedResultViewType = 'table';

  }

  ngOnInit() {
  }

}
