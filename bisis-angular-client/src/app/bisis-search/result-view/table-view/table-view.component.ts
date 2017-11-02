import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import {LazyLoadEvent} from "primeng/primeng";

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.css']
})
export class TableViewComponent implements OnInit {

  @Input() resultRecords: any[];
  selectedRec: any;
  displayDialog: boolean;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  onRowSelect(event) {
    console.log(event);
    console.log(this.selectedRec);
  }

  loadRecordsLazy(event: LazyLoadEvent){
    console.log(event);
  }



}
