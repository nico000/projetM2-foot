import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "../@creation/beans/Scenario";
import {Observable} from "rxjs";
import {Entite} from "../@creation/beans/Entite";
import {Examen} from "./beans/Examen";
import {Experience} from "./beans/Experience";
import {Deplacement} from "./beans/Deplacement";



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
        return this._http.get<Scenario>(`/scenario?nom=${nom}`);
    }

    public DelScenario(id:Number): Observable<any> {
        return this._http.delete('/scenario/'+id)
    }

    public addExamen (examen:Examen):Observable<Examen>{
        return this._http.post<Examen>("/examen",examen);
    }

    public addExeperience (exp:Experience,nom:String):Observable<Examen>{
        return this._http.put<Examen>(`/examen/add?nom=${nom}`,exp);
    }

    public getDepalcementList(id:Number): Observable<Deplacement[]> {
        return this._http.get<Deplacement[]>(`/deplacement?scenario=${id}`);
    }

}