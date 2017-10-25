import { Component, OnInit, Input } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {TranslateService} from "@ngx-translate/core";
@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() data: any[];
  resultViewTypes: SelectItem[];
  selectedResultViewType: any;

  constructor( private translate: TranslateService) {

    this.resultViewTypes = [];

   // if (this.translate.getDefaultLang() == "srb-cyr") {
      this.resultViewTypes.push({label: 'Табеларно', value: 'table'});
      this.resultViewTypes.push({label: 'Грид', value: 'data-grid'});
      this.resultViewTypes.push({label: 'Скролер', value: 'scroller'});
      this.resultViewTypes.push({label: 'Листа', value: 'list'});
  //  }

    if (this.translate.getDefaultLang() == "srb-lat") {
      this.resultViewTypes.push({label: 'Tabelarno', value: 'table'});
      this.resultViewTypes.push({label: 'Grid', value: 'data-grid'});
      this.resultViewTypes.push({label: 'Skroler', value: 'scroller'});
      this.resultViewTypes.push({label: 'Lista', value: 'list'});
    }

    if (this.translate.getDefaultLang() == "en") {
      this.resultViewTypes.push({label: 'Table', value: 'table'});
      this.resultViewTypes.push({label: 'Grid', value: 'data-grid'});
      this.resultViewTypes.push({label: 'Scroller', value: 'scroller'});
      this.resultViewTypes.push({label: 'List', value: 'list'});
    }

    this.selectedResultViewType = 'table';

  }

  ngOnInit() {
  }

}
