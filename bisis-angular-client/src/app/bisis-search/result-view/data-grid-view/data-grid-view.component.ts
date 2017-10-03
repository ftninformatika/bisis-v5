import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-data-grid-view',
  templateUrl: './data-grid-view.component.html',
  styleUrls: ['./data-grid-view.component.css']
})
export class DataGridViewComponent implements OnInit {

  @Input() resultRecords: any[];
  selectedRec: any;
  displayDialog: boolean;


  constructor() { }

  ngOnInit() {
  }

  selectRecord(event) {
    this.selectedRec = event;
    this.displayDialog = true;
  }

}
