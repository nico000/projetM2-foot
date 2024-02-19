import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "../@home/home.service";
import {ModalService} from "../@modal/modal.service";
import {SerieService} from "./serie.service";
import {Experience} from "./beans/Experience";
import {Examen} from "./beans/Examen";
import {Serie} from "./beans/Serie";
import {Utilisateur} from "./beans/Utilisateur";
import {Deplacement} from "../@home/beans/Deplacement";
import {Entite} from "../@creation/beans/Entite";

@Component({
    selector: 'serie',
    templateUrl: './serie.component.html',
    styleUrls: ['./serie.component.css']
})


export class SerieComponent {

    protected _isFiltering:boolean = false;
    protected _examList: Examen[]= [];
    protected _serieList: Serie[]= [];
    protected _experienceList: Experience[]= [];
    protected _experience: Experience =null;

    constructor(private _serieService: SerieService,
                private _modalService: ModalService) {}
    ngOnInit(): void {
        this._serieService.getSerieList().subscribe(
            res => {
                this._examList = res;
                this._examList.forEach(examSerie => {
                    let serie: Serie = {
                        id: examSerie.id,
                        nom: examSerie.nom,
                        mode: examSerie.mode,
                        experience: []
                    };

                    examSerie.experience.forEach(experienceId => {
                        this.getExperience(experienceId, serie);
                    });

                    console.log("ngon serie:", serie);
                    this._serieList.push(serie);
                });
                console.log("ngon :", this._serieList);
            }
        );
    }

    orderBy(type:string): void {
        this._examList.sort((a, b) => {
            if(isNaN(a[type])) {
                return a[type].toLowerCase() < b[type].toLowerCase() ? 1:-1
            } else {
                return Number(a[type]) < Number(b[type]) ? 1:-1
            }
        });
    }

    filterBy(type:string, contain:string) : void {
        this._examList = this._examList.filter(produit => produit[type].includes(contain.toLowerCase()))
    }

    resetFilter():void {
        this._serieService.getSerieList().subscribe(
            res => {
                this._examList = res;
                this._isFiltering = false;
            }
        )
    }

    getExperience(id: number, serie: Serie): void {
        this._serieService.getExperience(id).subscribe(
            res => {
                serie.experience.push(res);
                console.log("get test", res);
            }
        );
    }

    nb_couloir(nombreDeCouloirs: number): number[] {
        return Array.from({length: nombreDeCouloirs}, (_, index) => index + 1);
    }
    //modal
    resetData(modal: string): void {
        this._modalService.close(modal);
    }
    openData(modal: string): void {
        this._modalService.open(modal);
    }
    //ajoute l'utilisateur
    protected  _simulationModal: string = "_simulationModal";
    protected  _veridModal: string = "_veridModal";
    protected _newUtilisateur : Utilisateur=new Utilisateur();
    protected _isLancer:boolean=false;
    addUser(modal: string){
        this.openData(modal);
        this.resetData(this._simulationModal);
        this._isLancer=false;
    }
    //update info user
    updateSex(sex: string){
        this._newUtilisateur.sex=sex;
    }
    updatePro(bool:boolean){
        this._newUtilisateur.pro=bool;
    }

    // lancer la simulation
    protected _serieSelect:Serie=new Serie();
    protected _depalcementList: Deplacement[]= [];
    protected _entiteList: Entite[];
    protected _selectedScenario:Scenario=new Scenario();
    protected _scenarioLancer:number=0;
    protected _nbVisualisation:number=1;
    protected _nbAction :number=1;
    protected _finvisu:boolean=true;

    //recuperer position du tableau
    protected tableau: HTMLTableElement | null = null;
    protected tabTop:number=0.0;
    protected tabLeft:number=0.0;

    //open simulation
    openSimu(modal: string,serie:Serie){
        this.openData(modal);
        this._serieSelect=serie;
    }

    //recup position tableau
    protected positionPercentage = this.getPositionPercentage(this.tableau);
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
    //genere les entite
    private genEntite(id:Number){
        this._serieService.getEntiteList(id).subscribe(
            res  =>
                this._entiteList = res
        )
    }
    //selectionner le scenario
    selectScenario(scenario:Scenario ):void {
        this._selectedScenario =scenario;
        this.genEntite(scenario.id);
        this._nbAction=1;

        //recup position tableau
        this.tableau =document.getElementById('tableau_terrain')as HTMLTableElement;
        this.positionPercentage = this.getPositionPercentage(this.tableau);

        this.tabLeft=this.positionPercentage.left;
        this.tabTop=this.positionPercentage.top;
    }
    //lancer la visualisation
    playVisualisation(scenario: Scenario) {
        //on recherche la liste des deplacement
        this._serieService.getDepalcementList(scenario.id).subscribe(
            res => {
                this._depalcementList = res;

                // fonction récursive pour parcourir la liste avec un délai entre chaque itération
                const processDepalcement = (index: number) => {
                    this._nbAction++;
                    // vérifie si nous avons atteint la fin de la liste
                    if (index < this._depalcementList.length) {
                        const deplacement = this._depalcementList[index];
                        // parcourt la liste des entités
                        this._entiteList.forEach(entite => {
                            // cherche l'entité devant être déplacée
                            if (entite.id === deplacement.entite) {
                                //on déplace l'entité
                                entite.x = deplacement.endPosX;
                                entite.y = deplacement.endPosY;
                                console.log("entite trouvée");
                            }
                        });
                        console.log("deplacement");
                        // Appelle la fonction processDepalcement avec l'indice suivant après un délai
                        setTimeout(() => {
                            processDepalcement(index + 1);
                        }, 2000); // délai de x seconde entre chaque déplacement
                    } else {
                        this._finvisu = true; // Définit la variable  sur true lorsque le traitement est terminé
                    }
                };
                // démarrer le traitement avec l'indice 0
                processDepalcement(0);
            }
        );
        this.selectScenario(scenario);
    }

    lance_visu(){
        this._finvisu=false;
        // Appel à getScenarioId pour obtenir l'objet Scenario avant de lancer la visualisation
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            this.playVisualisation(scenario);
        });
    }
    lance_simu() {
        this._isLancer = true;
        while(this._nbVisualisation<=this._serieSelect.experience[this._scenarioLancer].visuFeedback){
            if (this._finvisu===true){
                this.lance_visu();
                console.log("visu");
            }
        }
    }



}