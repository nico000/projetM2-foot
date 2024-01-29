import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "./home.service";
import {ModalService} from "../@modal/modal.service";
import {ScenarioList} from "./beans/ScenarioList";


@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css' ]
})


export class HomeComponent {
    protected _scenarioList: Scenario[];
    protected _selectedScenario:Scenario = new Scenario();
    protected _isFiltering:boolean = false;

    protected _selectScenarioModalName: string = "selectScenarioModal";

    constructor(private _homeService: HomeService,
                private _modalService: ModalService) {}

    ngOnInit(): void{
        this._homeService.getScenarioList().subscribe(
            res  =>
                this._scenarioList = res
        )
    }

    orderBy(type:string): void {
        this._scenarioList.sort((a,b) => {
            if(isNaN(a[type])) {
                return a[type].toLowerCase() < b[type].toLowerCase() ? 1:-1
            } else {
                return Number(a[type]) < Number(b[type]) ? 1:-1
            }
        });
    }

    filterBy(type:string, contain:string) : void {
        this._scenarioList = this._scenarioList.filter( produit => produit[type].includes(contain.toLowerCase()))
    }

    resetFilter():void {
        this._homeService.getScenarioList().subscribe(
            res => {
                this._scenarioList = res;
                this._isFiltering = false;
            }
        )
    }

}