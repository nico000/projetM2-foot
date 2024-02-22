import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "../@creation/beans/Scenario";
import {Observable} from "rxjs";
import {Examen} from "./beans/Examen";
import {Experience} from "./beans/Experience";
import {Deplacement} from "../@home/beans/Deplacement";
import {Entite} from "../@creation/beans/Entite";
import {Essai} from "./beans/Essai";
import {ResultatFeedBack} from "./beans/ResultatFeedBack";
import {Utilisateur} from "./beans/Utilisateur";
import {ResultatUser} from "./beans/ResultatUser";


@Injectable()
export class SerieService {
    constructor(
        private _http: HttpClient) {
    }

    public getSerieList(): Observable<Examen[]> {
        return this._http.get<Examen[]>("/examen/all");
    }

    public getExperience(id:Number):Observable<Experience> {
        return this._http.get<Experience>("/experience/"+id);
    }

    public getDepalcementList(id:Number): Observable<Deplacement[]> {
        return this._http.get<Deplacement[]>(`/deplacement?scenario=${id}`);
    }

    public getEntiteList(id:Number): Observable<Entite[]> {
        return this._http.get<Entite[]>('/entite/'+id);
    }
    public getScenarioId(id:Number):Observable<Scenario> {
        return this._http.get<Scenario>(`/scenario/`+id);
    }

    public addEssai(essai:Essai):Observable<ResultatFeedBack>{
        return this._http.post<ResultatFeedBack>(`/essai/`,essai);
    }

    public addUser(user:Utilisateur):Observable<ResultatUser> {
        return this._http.post<ResultatUser>("/resultat_examen/",user)
    }
}