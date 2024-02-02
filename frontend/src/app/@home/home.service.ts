import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "../@creation/beans/Scenario";
import {Observable} from "rxjs";
import {Entite} from "../@creation/beans/Entite";



@Injectable()
export class HomeService {
    constructor(
        private _http: HttpClient) {
    }

    public getScenarioList(): Observable<Scenario[]> {
        return this._http.get<Scenario[]>("/scenario");
    }

    public getEntiteList(id:Number): Observable<Entite[]> {
        return this._http.get<Entite[]>('/entite/'+id);
    }

    public getScenarioNom(nom:string): Observable<Scenario> {
        return this._http.get<Scenario>(`/scenario/nom?name=${nom}`);
    }

    public DelScenario(id:Number): Observable<any> {
        return this._http.delete('/scenario/'+id)
    }

}