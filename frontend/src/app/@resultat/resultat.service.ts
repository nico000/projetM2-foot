import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Utilisateur} from "./beans/Utilisateur";
import { Observable } from 'rxjs';



@Injectable()
export class ResultatService {
    constructor(
        private _http: HttpClient) {
    }

    public getUserList(): Observable<Utilisateur[]> {
        return this._http.get<Utilisateur[]>("/resultat_examen/getIdentite");
    }
    public exportExel(nom:string,prenom:string):Observable<ArrayBuffer>{
        console.log("nom:" ,nom,"prenom",prenom);
        return this._http.get<ArrayBuffer>(`/report/export?nom=${nom}&prenom=${prenom}`);
    }

}