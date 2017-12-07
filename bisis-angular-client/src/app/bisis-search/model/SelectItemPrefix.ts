import {SelectItem} from 'primeng/primeng';
import {Prefix} from "./Prefix";

export class SelectItemPrefix implements  SelectItem{

    label: any;
    value: Prefix;
    styleClass?: string;

    constructor(label: any, value: any, isCoder: boolean = false){
        this.label = label;
        this.value = new Prefix(value,  isCoder);
    }


}