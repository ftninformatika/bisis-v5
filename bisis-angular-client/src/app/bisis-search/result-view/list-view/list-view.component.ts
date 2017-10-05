import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.css']
})
export class ListViewComponent implements OnInit {

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
