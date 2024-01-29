import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Scenario} from "../@creation/beans/Scenario";
import {Observable} from "rxjs";
import {ScenarioList} from "./beans/ScenarioList";


@Injectable()
export class HomeService {
    constructor(
        private _http: HttpClient) {
    }

    public getScenarioList(): Observable<Scenario[]> {
        return this._http.get<Scenario[]>("/scenario");
    }

}