import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "./beans/Scenario";
import {Observable} from "rxjs";


@Injectable()
export class CreationService {
    constructor(
        private _http: HttpClient) {
    }

    public addScenario (scenario:Scenario): Observable<Scenario>{
        return this._http.post<Scenario>("/scenario",scenario)
    }



}