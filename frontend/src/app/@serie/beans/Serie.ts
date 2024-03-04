import {Experience} from "./Experience";

export class Serie{
    public id:number=0.0;
    public nom: string = "";
    public sequencage: string = "";
    public complexite: string = "";
    public contenuFeedback: string = "";
    public experience: Experience[] = [];
}