import {DeplacementFeedBack} from "./DeplacementFeedBack";

export class ResultatFeedBack {
    public essai: Number = 0.0;
    public reussi:Boolean=false;
    public listError:DeplacementFeedBack[]=[];
    public score:number;
}