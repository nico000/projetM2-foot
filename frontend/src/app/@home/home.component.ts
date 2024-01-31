import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "./home.service";
import {ModalService} from "../@modal/modal.service";
import {Entite} from "../@creation/beans/Entite";



@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css' ]
})


export class HomeComponent {
    protected _scenarioList: Scenario[];
    protected _selectedScenario:Scenario = new Scenario();
    protected _selectedScenarioEntite:Scenario = new Scenario();
    protected _isFiltering:boolean = false;
    protected _entiteList: Entite[];

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

    private getScenarioName(nom: string): Promise<any> {
        return new Promise((resolve, reject) => {
            this._homeService.getScenarioNom(nom).subscribe(
                res => {
                    this._selectedScenarioEntite = res;
                    //console.log("last scenario nom:", this.lastNom, " id :", this.LastScenario.id);
                    resolve();
                },
                error => {
                    reject(error);
                }
            );
        });
    }

    private genEntite(nom:string){
        this.getScenarioName(nom).then(() => {
            this._homeService.getEntiteList(this._selectedScenarioEntite.id).subscribe(
                res  =>
                    this._entiteList = res
            )
        }).catch(error => {
            console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
            // Gérer l'erreur ici si nécessaire
        });
    }

    selectScenario(modal:string, scenario:Scenario ):void {
        this._modalService.open(modal);
        this._selectedScenario =scenario;
        this.genEntite(scenario.nom);
    }



}