import { Country } from "./country";

export class Institution {
  constructor(
    public id: number,
    public title: string,
    public city: string,
    public address: string,
    public postalCode: string,
    public ecrisid: number,
    public establishmentDate: Date,
    public creationDate: Date
    /*public country: Country*/) { }

  
}