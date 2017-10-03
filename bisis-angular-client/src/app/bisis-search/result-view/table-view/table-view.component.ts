import { Component, OnInit, Input } from '@angular/core';
import {Message} from 'primeng/components/common/api';

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.css']
})
export class TableViewComponent implements OnInit {

  @Input() resultRecords: any[];
  selectedRec: any;
  displayDialog: boolean;

  constructor() { }

  ngOnInit() {
  }

  onRowSelect(event) {
    console.log(event);
    this.selectedRec = event.data;
    this.displayDialog = true;
  }


}
