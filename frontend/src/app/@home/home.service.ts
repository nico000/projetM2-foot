import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "../@creation/beans/Scenario";
import {Observable} from "rxjs";



@Injectable()
export class HomeService {
    constructor(
        private _http: HttpClient) {
    }

    public getScenarioList(): Observable<Scenario[]> {
        return this._http.get<Scenario[]>("/scenario");
    }

}