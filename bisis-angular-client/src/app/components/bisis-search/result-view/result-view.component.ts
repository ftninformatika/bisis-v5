import { Component, OnInit, Input } from '@angular/core';
import {MenuItem, SelectItem, TabMenuModule} from 'primeng/primeng';
import {TranslateService} from "@ngx-translate/core";
import { RecordsPageModel } from '../../model/RecordsPageModel';
@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() data: RecordsPageModel;
  resultViewTypes: MenuItem[];
  selectedResultViewType: any;

  constructor( private translate: TranslateService) {

    this.resultViewTypes = [];
    this.resultViewTypes.push({label: 'Табеларно', command: (event: any) => {this.selectedResultViewType = 'table'}});
    this.resultViewTypes.push({label: 'Грид', command: (event: any) => {this.selectedResultViewType = 'data-grid'}});
    this.resultViewTypes.push({label: 'Скролер', command: (event: any) => {this.selectedResultViewType = 'scroller'}});
    this.resultViewTypes.push({label: 'Листа',command: (event: any) => {this.selectedResultViewType = 'list'}});
    this.selectedResultViewType = 'table';

  }

  ngOnInit() {
  }

}
