import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import {LazyLoadEvent} from "primeng/primeng";
import {RecordsPageModel} from "../../../../model/RecordsPageModel";
import {BisisSearchService} from "../../../../service/bisis-search.service";

@Component({
  selector: 'app-table-view',
  templateUrl: './table-view.component.html',
  styleUrls: ['./table-view.component.css']
})
export class TableViewComponent implements OnInit {

  @Input() resultRecords: RecordsPageModel;
  selectedRec: any;
  displayDialog: boolean;

  constructor(private router: Router, public bisisService: BisisSearchService) {
  }

  ngOnInit() {
  }

  onRowSelect(event) {
    //console.log(event);
    //console.log(this.selectedRec);
  }

  /*loadRecordsLazy(event: LazyLoadEvent){
    console.log(event);

    if (this.resultRecords.content == undefined)
      return;

    var page = event.first / event.rows;
    var size = 20;

    if (this.resultRecords['query'] != undefined) { //univerzalna pretraga
      this.bisisService.searchRecordsByEP('universal', this.resultRecords['query'], this['deps'],  page, size).subscribe(
          response => {
            //var responseObject = this.createInstanceFromJson(RecordsPageModel, response);
            if (this.resultRecords.content.length < 200){
              //this.resultRecords.content = this.resultRecords.content.concat(response['content']);
              this.resultRecords.content = [];
              this.resultRecords.content = response['content'];
            }

          }
      );
    }
  }*/

  createInstanceFromJson<T>(objType: { new(): T; }, json: any) {
    const newObj = new objType();
    const relationships = objType['relationships'] || {};

    for (const prop in json) {
      if (json.hasOwnProperty(prop)) {
        if (newObj[prop] == null) {
          if (relationships[prop] == null) {
            newObj[prop] = json[prop];
          } else {
            newObj[prop] = this.createInstanceFromJson(relationships[prop], json[prop]);
          }
        } else {
          console.warn(`Property ${prop} not set because it already existed on the object.`);
        }
      }
    }

    return newObj;
  }

}
