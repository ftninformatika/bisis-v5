import { Component, OnInit, Input } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthHelper} from "../../auth/utilities/authhelper";
import {BisisSearchService} from "../../../service/bisis-search.service";
import {CodersService} from "../../../service/coders.service";
import {PresentItemGenerator} from "../../../tools/PresentItemGenerator";
import {PresentItem} from "../../../model/PresentItem";
@Component({
  selector: 'app-record-view',
  templateUrl: './record-view.component.html',
  styleUrls: ['./record-view.component.css']
})
export class RecordViewComponent  {

  @Input() selectedRec: any;
  viewTypes: SelectItem[];
  selectedViewType: string;
  unimarcRows: any[];
  isPage: boolean;
  presentItems: PresentItem[];


  constructor( private route:ActivatedRoute, public router: Router,
               public bisisService: BisisSearchService, public ah: AuthHelper,
               private codersService: CodersService, private presentItemGenerator: PresentItemGenerator ) {
    this.viewTypes = [];
    this.viewTypes.push({label: 'Основно', value:'general'});
    this.viewTypes.push({label: 'Детаљно', value:'detail'});
    this.viewTypes.push({label: 'Unimarc', value:'unimarc'});
    this.selectedViewType = 'general';
  }

  public getLibCode(): string{
    return localStorage.getItem('libCode');
  }

  ngOnInit() {
    if (this.selectedRec == undefined) {
        this.route.params.subscribe(
              params => {
                if(params['recId'] != undefined)
                {
                  this.bisisService.getRecord(params['recId'], params['libCode']).subscribe(
                    response => {
                      if ( response != null && response != undefined)
                        this.selectedRec = response;
                        console.log(this.selectedRec);
                        //ucitaj status kodere za biblioteku
                        this.codersService.getItemStatusCoders(this.getLibCode()).subscribe(
                            response2 => {
                                //generisi prikazne elemente
                               this.presentItems = this.presentItemGenerator.generatePresentItemsList(response['fullRecord'], response2, response['listOfItems'] );
                            }
                        );

                        this.isPage = true;
                    },
                    error => {
                      this.router.navigate(['/bisis-search']);
                    }
                );
                }
              }
        );
      } else {
        //ucitaj status kodere za biblioteku
        this.codersService.getItemStatusCoders(this.getLibCode()).subscribe(
            response => {
                //generisi prikazne elemente
                this.presentItems = this.presentItemGenerator.generatePresentItemsList(this.selectedRec['fullRecord'], response, this.selectedRec['listOfItems'] );
                console.log(this.presentItems);
            }
        );
    }
  }

  makeUnimarc(record: any) {
    if(!record) {
      //console.log('something went wrong');
      return;
    }

    var retVal = new Array();

    record.fields.forEach(element => {
      var el = {} ;
      el['name'] =  element.name ;

      if (element.ind1 == undefined || element.ind1 == null || element.ind1 == ' ' || element.ind1 == '' ) {
        el['ind1'] = '#';
      } else {
        el['ind1'] = element.ind1;
      }
      if (element.ind2 == undefined || element.ind2 == null || element.ind2 == ' ' || element.ind2 == '' ) {
        el['ind2'] = '#';
      } else {
        el['ind2'] = element.ind2;
      }
      el['subfields'] = []
      element.subfields.forEach(e => {
        el['subfields'].push( {"name": ' [' + e.name + ']', "content": e.content});
        //el['subfields']['content'] = e.content;
      });
       retVal.push(el);

    });
    this.unimarcRows = retVal;
    //console.log(this.unimarcRows);
  }

}
