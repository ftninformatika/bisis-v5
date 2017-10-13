import {Component,  OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {SelectItem} from "primeng/primeng";

@Component({
  selector: 'app-bisis-search',
  templateUrl: './bisis-search.component.html',
  styleUrls: ['./bisis-search.component.css']
})
export class BisisSearchComponent implements OnInit {

  searchResults: any[];
  // Kada nam je prosledjena biblioteka kao parametar (na pr. /gbns_com)
  lib: string;

  // Bez parametra, biramo po kojoj biblioteci pretrazujemo
  libList: SelectItem[];
  selectedLibrary: string;

  constructor(private route:ActivatedRoute) {
    this.searchResults = [];
    this.lib ='';
    this.selectedLibrary = '';
    this.libList = [];
    this.libList.push({label:'Одабери библиотеку', value:null});
    this.libList.push({label:'Градска библиотека Нови Сад', value:'gbns_com'});
    this.libList.push({label:'Градска библиотека Шабац', value:'gbsa_com'});
    this.libList.push({label:'Библиотека Техничког факултета у Зрењанину', value:'tfzr_uns'});
    this.libList.push({label:'Библиотека ПМФ Нови Сад', value:'pmf_uns_ac_rs'});
  }

  ngOnInit() {
    this.route.params.subscribe(
      params => {
    this.lib = params['lib'];
    console.log("biblioteka " + this.lib);
    }
    );

  }


  setRecords(event) {
    this.searchResults = event;
    console.log(this.searchResults);
  }

}
