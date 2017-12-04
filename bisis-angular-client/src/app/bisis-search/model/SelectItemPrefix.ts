import {SelectItem} from 'primeng/primeng';

export class SelectItemPrefix implements  SelectItem{

    label: any;
    value: any;
    styleClass?: string;
    coder: boolean = false;

    constructor(l: string, v: any){
        this.label = l;
        this.value = v;
    }

}