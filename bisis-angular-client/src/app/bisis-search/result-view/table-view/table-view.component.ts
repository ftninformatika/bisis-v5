import { Component, OnInit, Input } from '@angular/core';
import { Message } from 'primeng/components/common/api';
import { Router } from '@angular/router';

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
    this.selectedRec = event.data;
    this.displayDialog = true;
    console.log(this.selectedRec);
  }

  redirectToRecordView(rec) {
    this.router.navigate(['record-view'], { queryParams: {recId: this.selectedRec.id} });
  }


}
