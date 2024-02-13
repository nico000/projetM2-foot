import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "./home.service";
import {ModalService} from "../@modal/modal.service";
import {Entite} from "../@creation/beans/Entite";
import {Experience} from "./beans/Experience";
import {Examen} from "./beans/Examen";



@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css' ]
})


export class HomeComponent {
    protected _scenarioList: Scenario[]= [];
    protected _selectedScenario:Scenario = new Scenario();
    protected _selectedScenarioEntite:Scenario = new Scenario();
    protected _isFiltering:boolean = false;
    protected _entiteList: Entite[]= [];

    protected _selectScenarioModalName: string = "selectScenarioModal";

    // recuperer position du tableau
    protected tableau: HTMLTableElement | null = null;
    protected tabTop:number=0.0;
    protected tabLeft:number=0.0;

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

    //detail
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
        //recup position tableau
        this.tableau =document.getElementById('tableau_terrain')as HTMLTableElement;
        this.positionPercentage = this.getPositionPercentage(this.tableau);

        this.tabLeft=this.positionPercentage.left;
        this.tabTop=this.positionPercentage.top;
    }

    //delete scenario
    delScenario(scenario:Scenario): void{
        this._homeService.DelScenario(scenario.id).subscribe(
            ()=>this._homeService.getScenarioList().subscribe(
                res  =>
                    this._scenarioList = res
            )
        );
    }

    nb_couloir(nombreDeCouloirs: number): number[] {
        return Array.from({length: nombreDeCouloirs}, (_, index) => index + 1);
    }

    //recup position tableau
    getPositionPercentage(element: HTMLTableElement | null): { top: number, left: number } {
        if (!element) return { top: 0, left: 0 };

        const rect = element.getBoundingClientRect();
        const windowWidth = window.innerWidth;
        const windowHeight = window.innerHeight;

        const popupScreenX = window.screenX || window.screenLeft || 0;
        const popupScreenY = window.screenY || window.screenTop || 0;
        console.log("Position de page par rapport à l'écran : ", popupScreenX, popupScreenY);
        console.log("Position du popup par rapport à l'écran : ", popupScreenX, popupScreenY);

        console.log("fonction get top :",(rect.top / windowHeight) * 100,"left :",(rect.left / windowWidth) * 100);
        return {
            top: (rect.top / windowHeight) * 100,
            left: (rect.left / windowWidth) * 100
        };
    }

    protected positionPercentage = this.getPositionPercentage(this.tableau);

    //creation serie

    public _scenarioSelect: Scenario[]= [];
    protected isAddSerie :boolean =false;
    protected _designModal: string = "_designModal";
    protected _feedBackModal: string = "_FeedBackModal";

    protected _newExeperience: Experience[]=[];
    protected _newExam:Examen =new Examen();
    toggleSelection(nom:Scenario) {
        const index = this._scenarioSelect.indexOf(nom);
        console.log("tooooo",index);
            if (index === -1) {
                this._scenarioSelect.push(nom);
                console.log("push");
            } else {
                this._scenarioSelect.splice(index, 1);
            }
        console.log("liste",this._scenarioSelect);

    }
    resetData(modal: string): void {
        this._modalService.close(modal);
    }

    changeDesigne(modal:string){
        this.isAddSerie=false;
        this._modalService.open(modal);
    }

    updateModeScenario(nom: string,scenario:Scenario) {
        scenario.mode_scene = nom;
    }

    updateCouleur(nom: string,scenario:Scenario) {
        scenario.terrain_couleur = nom;
    }
    addDesign(modal:string){
        this._modalService.open(modal);
        let nomScenario:string;

        // Parcours de la liste _scenarioSelect
        this._scenarioSelect.forEach(scenario => {
            const exp =new Experience();
            exp.scenario=scenario.id;
            exp.mode_scene=scenario.mode_scene;
            exp.zone_display=scenario.zone_display;
            exp.terrain_couleur=scenario.terrain_couleur;
            exp.zone_nb_couloir=scenario.zone_nb_couloir;
            exp.zone_nb_zone=scenario.zone_nb_zone;
            this._newExeperience.push(exp);
            console.log("exp");
        });
        this.resetData(this._designModal);
    }

    updateTypeFeed(nom: string,experience:Experience){
        experience.typeFeedback=nom;
    }
    updateFrequenceFeed(nom: Number,experience:Experience){
        experience.freqFeedback=nom;
    }
    updateVisuFeed(nom: Number,experience:Experience){
        experience.visuFeedback=nom;
    }

    resetFeedBack(modal:string){
        this._newExeperience = null;
        this.resetData(modal);
    }

    addFeedBack(modal:string){
        //creation examen

        //ajout des exp

        //reset
        this.resetFeedBack(modal);
    }

}