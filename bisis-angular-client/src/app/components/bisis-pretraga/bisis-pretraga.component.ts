import { Component, OnInit } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
@Component({
  selector: 'app-bisis-pretraga',
  templateUrl: './bisis-pretraga.component.html',
  styleUrls: ['./bisis-pretraga.component.css']
})
export class BisisPretragaComponent implements OnInit {

  data: any[];
  resultViewTypes: SelectItem[];
  selectedResultViewType: any;

  constructor() { 
    this.resultViewTypes = [];
    this.resultViewTypes.push({label:'Tabelarno', value:"table"});
    this.resultViewTypes.push({label:'Lista', value:"list"});
    this.selectedResultViewType = "table";
  }

  ngOnInit() {
  }

  setRecords(event) {
    this.data = event;
  }

}
