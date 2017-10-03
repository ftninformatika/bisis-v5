import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-scroller-view',
  templateUrl: './scroller-view.component.html',
  styleUrls: ['./scroller-view.component.css']
})
export class ScrollerViewComponent implements OnInit {

  @Input() resultRecords: any[];
  selectedRec: any;
  displayDialog: boolean;

  constructor() { }

  selectRec(rec) {
    this.selectedRec = rec;
    this.displayDialog = true;
  }

  ngOnInit() {
  }

}
