import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "./beans/Scenario";
import {Observable} from "rxjs";
import {Entite} from "./beans/Entite";


@Injectable()
export class CreationService {
    constructor(
        private _http: HttpClient) {
    }

    public addScenario (scenario:Scenario): Observable<Scenario>{
        return this._http.post<Scenario>("/scenario",scenario)
    }

    public getScenarioNom(nom:string): Observable<Scenario> {
        return this._http.get<Scenario>(`/scenario/nom?name=${nom}`);
    }

    public addEntite (entite:Entite): Observable<Entite>{
        return this._http.post<Entite>("/entite",entite)
    }

    public getEntiteList(id:Number): Observable<Entite[]> {
        return this._http.get<Entite[]>('/entite/'+id);
    }


}