import {Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import { BisisSearchService } from '../service/bisis-search.service';
import {SelectItem} from 'primeng/primeng';
import {LibraryService} from "../../service/library.service";
import {forEach} from "@angular/router/src/utils/collection";
import {arrayify} from "tslint/lib/utils";
import {RecordsPageModel} from "../model/RecordsPageModel";
import {MessageService} from "primeng/components/common/messageservice";
import {GetCoder} from "../service/get-local-data.service";
import {SelectItemPrefix} from "../model/SelectItemPrefix";
import {isNullOrUndefined} from "util";
import {Prefix} from "../model/Prefix";
import {ProgressBarModule} from 'primeng/primeng';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  @Input() lib: string;
  @Input() selectedLibrary: string;
  @Input() selectedDeps: string[];
  @Output() serviceCallResult: EventEmitter<RecordsPageModel> = new EventEmitter();

  //loading bar
  displayDialog = false;
  value = 0;
  interval: any;
  // 1st Form
  searchText1: string;
  // 2nd Form
  allPrefixes: SelectItemPrefix[];
  bondings: SelectItem[];
  bonding1: string;
  bonding2: string;
  bonding3: string;
  bonding4: string;
  prefix1: Prefix;
  prefix2: Prefix;
  prefix3: Prefix;
  prefix4: Prefix;
  prefix5: Prefix;
  text: string[];
  coderValues: SelectItem[][];


  changed(pref, fieldNum){
    this.text[fieldNum] = "";

    if (pref.isCoder) { // If prefix is coder
      this.getCoder.getCoderData(pref.code).subscribe(
          response => {
            //console.log(response);
            arrayify(response[pref.code].codes).forEach(
                d => {
                  this.coderValues[fieldNum].push({"label": d.value, "value": d.code});
                });
          });
      }
    }

  search() {
    this.bisisService.getRecordsEP();

  }
  searchBy( text) {
    if ( localStorage.getItem('libCode') == null || this.selectedLibrary == null || this.selectedLibrary == '') {
      this.messageService.clear();
      this.messageService.add({
        severity: 'warning',
        summary: 'Упозорење',
        detail: 'Одаберите библиотеку!'
      });
      return;
    }

    var choice = 'universal';
    if (localStorage.getItem('libCode') == undefined)
      return;

    if (!this.validateQuery(choice, text)) {
      return;
    }
    this.displayDialog = true;
    this.runProgressBar();
    this.bisisService.searchRecordsByEP(choice, text, this.selectedDeps)
    .subscribe(    // ovo postoji zbog paginga i sortiga u drugim komponentama
      response => {
        response['query'] = text;
        response['deps'] = this.selectedDeps;
        this.serviceCallResult.emit(response);
        this.displayDialog = false;
      },
      error => {
        console.log(error);
        this.displayDialog = false;
      }
    );
  }

  runProgressBar(){
    this.value = 0;
    this.interval = setInterval(() => {
      this.value = this.value + Math.floor(Math.random() * 10) + 1;
      if (this.value >= 100) {
        this.value = 100;
        clearInterval(this.interval);
        this.interval = null;
      }
    }, 10);
  }


  setLib(lib) {
    this.selectedLibrary = lib.value;
    localStorage.setItem('libCode', lib.value);
    //console.log(this.lib, this.selectedLibrary);
  }

  private validateQuery(choice, text): boolean {
    if (text === '' || text === undefined || text === null || (choice != 'universal'
        && choice != 'author' && choice != 'title' && choice != 'keyword' )) {
      this.messageService.clear();
      this.messageService.add({
        severity: 'warning',
        summary: 'Упозорење',
        detail: 'Унесите текст претраге!'
      });
      return false;
    }
    return true;
  }

  ngOnInit() {
  }

  searchAdvanced(text1,text2,text3,text4,text5,prefix1,prefix2,prefix3,prefix4,prefix5,bonding1,bonding2,bonding3,bonding4, selectedDeps = null) {

    if ( localStorage.getItem('libCode') == null || this.selectedLibrary == null || this.selectedLibrary == '') {
      this.messageService.clear();
      this.messageService.add({
        severity: 'warning',
        summary: 'Упозорење',
        detail: 'Одаберите библиотеку!'
      });
      return;
    }
    else {
      var searchModel = {
        pref1: prefix1.code,
        pref2: prefix2.code,
        pref3: prefix3.code,
        pref4: prefix4.code,
        pref5: prefix5.code,
        text1: text1,
        text2: text2,
        text3: text3,
        text4: text4,
        text5: text5,
        oper1: bonding1,
        oper2: bonding2,
        oper3: bonding3,
        oper4: bonding4,
        departments: this.selectedDeps
      };
      this.bisisService.searchRecordsAdvanced(searchModel)
          .subscribe(
              response => {
                response['searchModel'] = searchModel;

                this.serviceCallResult.emit(response)
              },
              error => console.log(error)
          );
      //console.log(searchModel);
    }
  }

  constructor( public bisisService: BisisSearchService, public libraryService: LibraryService, public messageService: MessageService, public getCoder: GetCoder) {
      this.populateAdvancedFormCombos();
  }

  private populateAdvancedFormCombos(){
    this.text = [];
    this.prefix1 = new Prefix('AU', false)
    this.prefix2 = new Prefix('TI', false);
    this.prefix3 = new Prefix("KW", false);
    this.prefix4 = new Prefix("PU", false);
    this.prefix5 = new Prefix("PY", false);
    this.bonding1 = "AND";
    this.bonding2 = "AND";
    this.bonding3 = "AND";
    this.bonding4 = "AND";
    this.allPrefixes = [];
    this.bondings = [];
    //TODO - staviti ovo ucitavanje prefiksa sa nekog drugog mesta
    this.allPrefixes.push(new SelectItemPrefix('Аутор',  'AU'));
    this.allPrefixes.push(new SelectItemPrefix('Наслов', 'TI'));
    this.allPrefixes.push(new SelectItemPrefix('Кључне речи', 'KW'));
    this.allPrefixes.push(new SelectItemPrefix('Издавач', 'PU'));
    this.allPrefixes.push(new SelectItemPrefix('Година издавања', 'PY'));
    this.allPrefixes.push(new SelectItemPrefix('Држава издавања', 'CO'));
    this.allPrefixes.push(new SelectItemPrefix('Библиографски ниво', 'DT', true));
    this.allPrefixes.push(new SelectItemPrefix('UDK', 'DC'));
    this.allPrefixes.push(new SelectItemPrefix('ISBN', 'BN'));
    this.allPrefixes.push(new SelectItemPrefix('ISSN',  'SP'));
    this.allPrefixes.push(new SelectItemPrefix('Код за врсту записа', 'RT', true)); //coder
    this.allPrefixes.push(new SelectItemPrefix('Код за врсту садржаја','CC', true)); //coder //kod za vrstu sadrzaja dodati 105
    this.allPrefixes.push(new SelectItemPrefix('Збирка',  'CL'));
    //korporativno telo ako izbacimo onda АУ treba da pretrazuje 71x i 91x
    //predmetne odrednice - sve (da li sve u jedan prefiks ili sve vrste p o ubaciti?)
    this.allPrefixes.push(new SelectItemPrefix('Инвентарни број', 'IN'));
    this.allPrefixes.push(new SelectItemPrefix('Место издавања', 'PP'));
    this.allPrefixes.push(new SelectItemPrefix('Број записа', 'RN'));
    this.allPrefixes.push(new SelectItemPrefix('Језик', 'LA',true)); ////Jezik LG, 101c, LO izbaciti (verovatno misli na LA i LO???)
    this.allPrefixes.push(new SelectItemPrefix('Наслов', 'SG')); //signatura
    this.bondings.push({label: 'AND', value: 'AND'});
    this.bondings.push({label:'OR', value: 'OR'});
    this.bondings.push({label:'NOT', value: 'NOT'});

    this.coderValues = [];

    for(var i: number = 0; i < 5; i++) {
      this.coderValues[i] = [];

    }

  }

}
