import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BisisSearchService } from '../service/bisis-search.service';
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent  {

  _selectedRec: any;
  fullRecord: any;

  @Input() set selectedRec(rec: any) {
    this._selectedRec = rec;
    if (rec) { // Prvi put ulazi sa rec == undefined
      this.getFullRec(rec.id);
    }
  }

  get selectedRec(): any {
    return this._selectedRec;
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    public bisisService: BisisSearchService
  ) {}

  readUnimarc(rec) {
    this.bisisService.getUnimarcForRecord(rec.id)
    .subscribe(
      response => console.log(response),
      error => console.log(error)
    );
    console.log(this.fullRecord);
    this.makeUnimarc(this.fullRecord);
    console.log('onde');
    }

  getFullRec(id: any) {
    this.bisisService.getRecord(id)
    .subscribe(
      response => {
        console.log(response);
        console.log('ovde');
        this.fullRecord = response;
      },
      error => console.log(error)
    );
  }

  makeUnimarc(record: any): any {
    var retVal = '';

    record.fields.forEach(element => {
      console.log(element);
    });

  }

}
