import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "../@home/home.service";
import {ModalService} from "../@modal/modal.service";
import {SerieService} from "./serie.service";
import {Experience} from "./beans/Experience";
import {Examen} from "./beans/Examen";
import {Serie} from "./beans/Serie";
import {Utilisateur} from "./beans/Utilisateur";
import {Entite} from "../@creation/beans/Entite";
import {Deplacement} from "./beans/Deplacement";
import {Essai} from "./beans/Essai";

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
    protected _scenarioplay:Scenario=new Scenario();
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
    selectScenario(scenario:Scenario ,elementid:string):void {
        this._selectedScenario =scenario;
        this.genEntite(scenario.id);
        this._nbAction=1;

        //recup position tableau
        this.tableau =document.getElementById(elementid)as HTMLTableElement;
        //this.tableau =document.getElementById('tableau_terrain')as HTMLTableElement;
        this.positionPercentage = this.getPositionPercentage(this.tableau);

        this.tabLeft=this.positionPercentage.left;
        this.tabTop=this.positionPercentage.top;
    }
    //lancer la visualisation
    playVisualisation(scenario: Scenario) {
        console.log("play");
        //on recherche la liste des deplacement
        this._serieService.getDepalcementList(scenario.id).subscribe(
            res => {
                this._depalcementList = res;

                // fonction récursive pour parcourir la liste avec un délai entre chaque itération
                const processDepalcement = (index: number) => {
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
                        this._nbAction++;
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
    }

    lance_visu(){
        this._finvisu=false;
        this._nbAction=0;
        // Appel à getScenarioId pour obtenir l'objet Scenario avant de lancer la visualisation
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            this.playVisualisation(scenario);
        });
    }
    lance_simu() {
        this._isLancer = true;
        this._nbVisualisation = 1;
        const visuFeedback = this._serieSelect.experience[this._scenarioLancer].visuFeedback;
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            this.selectScenario(scenario,'tableau_terrain');
        });

        const launchNextVisu = () => {
            if (this._nbVisualisation <= visuFeedback) {
                if (this._finvisu) {
                    this.lance_visu();
                    this._nbVisualisation++;
                    console.log("visu",this._nbVisualisation);
                }
                setTimeout(launchNextVisu, 2000); // Attendre 2 secondes avant de vérifier si une nouvelle visualisation peut être lancée
            }
            else {
                // Appel de maFonction une fois que toutes les visualisations sont terminées
                //this.toTest();
            }
        };

        launchNextVisu(); // Lancer le premier appel de la visualisation
    }

    //l'utilisateur entre les deplacement
    protected _nbEssai:number=0.0;
    protected _selectEntite:boolean=false;
    protected _entiteSelect:Entite=null;

    protected _newDeplacement: Deplacement = new Deplacement();
    protected numAction:number = 0;
    protected _ispostdeplacement=false;
    protected _newEssai:Essai=new Essai();
    protected  _PostDeplacementModal: string = "_PostDeplacementModal";
    toTest(){
        this.openData(this._PostDeplacementModal);
        this.resetData(this._veridModal);
        //on regenere le terrain
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            this.selectScenario(scenario,'tableau_terrain2');
            console.log("regen :",scenario);
        });
        //on enr l'essai
        this._nbEssai++;
        //ajout essaie
        this._newEssai.num=this._nbEssai;
        this._newEssai.experience=this._serieSelect.experience[this._scenarioLancer].id;
        this._newEssai.temps=0.0;
        this._ispostdeplacement=true;
    }
    selectEntite(entite:Entite){
        if (this._selectEntite==false && this._ispostdeplacement){
            this._entiteSelect=entite;
            this._selectEntite=true;
            this._newDeplacement.startPosX=entite.x;
            this._newDeplacement.startPosY=entite.y;
        }else{
            this._entiteSelect=null;
            this._selectEntite=false;
        }
        console.log("select ",this._selectEntite,this._entiteSelect.id)
    }

    isEntiteSelect(entite:Entite):boolean{
        if(this._selectEntite==true && entite.id==this._entiteSelect.id){
            return true;
        }
        return false;
    }

    addDeplacement(event: MouseEvent){
        if(this._selectEntite==true ){

            const offsetX = event.clientX;
            const offsetY = event.clientY;

            const parentWidth = window.innerWidth;
            const parentHeight = window.innerHeight;

            let percentX = (offsetX / parentWidth) * 100;
            let percentY = (offsetY / parentHeight) * 100;

            this.numAction +=  1;
            //recup position tableau
            this.positionPercentage = this.getPositionPercentage(this.tableau);

            this.tabLeft=this.positionPercentage.left;
            this.tabTop=this.positionPercentage.top;
            console.log('tab finale en pourcentage - Left:', this.tabLeft, 'Top:', this.tabTop);
            percentY=percentY-this.tabTop-1.5;
            percentX=percentX-this.tabLeft-1.5;

            // Mettre à jour les coordonnées de l'entité sélectionnée
            this._entiteSelect.y = percentX;
            this._entiteSelect.x = percentY;
            console.log('deplacement finale en pourcentage - Left:', percentX, 'Top:', percentY);
            this._entiteList.forEach(entite => {
                if (entite.id == this._entiteSelect.id) {
                    this._newDeplacement.entite=entite.id;
                    this._newDeplacement.numAction=this.numAction;
                    this._newDeplacement.numScene=1;
                    this._newDeplacement.numBloc=1;
                    entite.y = percentX;
                    entite.x = percentY;
                    this._newDeplacement.endPosX=percentY;
                    this._newDeplacement.endPosY=percentX;
                    //add deplacement dans essai
                    this._newEssai.deplacement.push(this._newDeplacement);
                    console.log("add deplacement",this._newEssai);
                }
            });
            this._entiteSelect=null;
            this._selectEntite=false;
        }
    }


}