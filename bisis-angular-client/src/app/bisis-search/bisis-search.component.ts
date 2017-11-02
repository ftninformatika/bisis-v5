import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SelectItem} from "primeng/primeng";
import {LibraryService} from "../service/library.service";
import {TranslateService} from "@ngx-translate/core";
import { RecordsPageModel } from './model/RecordsPageModel';

@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css']
})
export class BisisSearchComponent implements OnInit {


  searchResults: RecordsPageModel;
  // Kada nam je prosledjena biblioteka kao parametar (na pr. /gbns_com)
  lib: string;

  // Bez parametra, biramo po kojoj biblioteci pretrazujemo
  libList: SelectItem[];
  selectedLibrary: string;


  constructor(private route:ActivatedRoute, public router: Router, public libService: LibraryService,  private translate: TranslateService) {
    this.searchResults = new RecordsPageModel();
    this.selectedLibrary = 'gbns_com';
    localStorage.setItem("libCode", "gbns_com");
    this.libList = [];

    this.libList.push({label:'Градска библиотека Нови Сад', value:'gbns_com'});
    this.libList.push({label:'Градска библиотека Шабац', value:'gbsa_com'});
    this.libList.push({label:'Библиотека Техничког факултета у Зрењанину', value:'tfzr_uns'});
    this.libList.push({label:'Библиотека ПМФ Нови Сад', value:'pmf_uns_ac_rs'});
  }

  ngOnInit() {

    this.libService.getLibs().subscribe(
        response => {
          this.route.params.subscribe(
              params => {
                if (params['lib']) {
                  if (response.includes(params['lib'])){
                    this.lib = params['lib'];
                    console.log("pretraga kao bilbioteka :" + this.lib);
                    localStorage.setItem('libCode', params['lib']);
                  }
                  else{
                    console.log("nepostojeca putanja, biblioteka, redirekcija na univerazalnu!!!");
                    this.router.navigate(['/bisis-search']);
                  }
                }
              }
          );
        }
    );


  }


  setRecords(event) {
    this.searchResults = event;
    console.log(this.searchResults);
  }

}
