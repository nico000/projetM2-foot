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
import {ResultatFeedBack} from "./beans/ResultatFeedBack";
import {ResultatUser} from "./beans/ResultatUser";
import {DeplacementFeedBack} from "./beans/DeplacementFeedBack";

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
                        mode: examSerie.sequencage,
                        contenuFeedback:examSerie.contenuFeedback,
                        complexite :examSerie.complexite,
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
        this._newEssai=new Essai();
        this._modalService.open(modal);
    }
    //ajoute l'utilisateur
    protected  _simulationModal: string = "_simulationModal";
    protected  _veridModal: string = "_veridModal";
    protected _newUtilisateur : Utilisateur=new Utilisateur();
    protected _isLancer:boolean=false;
    protected resultUser:ResultatUser=new ResultatUser();
    addUser(modal: string){
        this.openData(modal);
        this.resetData(this._simulationModal);
        this._isLancer=false;
        //on ajoute l'utlisateur
        this._newUtilisateur.examen=this._serieSelect.id;
        this._serieService.addUser(this._newUtilisateur).subscribe(
            (resultat: ResultatUser) => {
                this.resultUser=resultat;
            },);
        this._nbEssai=0;
        this.nbFeed=0;
        //si mode aleatoire on reorganise aleatoirement
        if (this._serieSelect.mode==='aleatoire'){
            this._serieSelect.experience=this.shuffleArray(this._serieSelect.experience);
        }
    }
    //fonction qui change aleatoirement les experience
    shuffleArray(array: Experience[]): Experience[] {
        const shuffledArray = [];
        for (let i = shuffledArray.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
        }
        return shuffledArray;
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
    protected finSimu=false;
    protected StartTime;
    protected EndTime;

    //recuperer position du tableau
    protected tableau: HTMLTableElement | null = null;
    protected tabTop:number=0.0;
    protected tabLeft:number=0.0;

    //open simulation
    openSimu(modal: string,serie:Serie){
        this.openData(modal);
        this._serieSelect=serie;
        this._scenarioLancer=0;
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
    private genEntite(id:number){
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
                        }, 1500); // délai de x seconde entre chaque déplacement
                    } else {
                        this._finvisu = true; // Définit la variable  sur true lorsque le traitement est terminé
                    }
                };
                // démarrer le traitement avec l'indice 0
                setTimeout(() => {
                    processDepalcement(0);
                }, 1500);
            }
        );
    }

    lance_visu(){
        this._finvisu=false;
        this._nbAction=0;
        // Appel à getScenarioId pour obtenir l'objet Scenario avant de lancer la visualisation
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            //on actualise le scenario si les parametre on changer
            scenario.mode_scene=this._serieSelect.experience[this._scenarioLancer].mode_scene;
            scenario.terrain_couleur=this._serieSelect.experience[this._scenarioLancer].terrain_couleur;
            scenario.zone_nb_zone=this._serieSelect.experience[this._scenarioLancer].zone_nb_zone;
            scenario.zone_nb_couloir=this._serieSelect.experience[this._scenarioLancer].zone_nb_couloir;
            //on lance le scenario
            this.selectScenario(scenario,'tableau_terrain');
            this.playVisualisation(scenario);
        });
    }
    lance_simu() {
        this.finSimu = false;
        this._isLancer = true;
        this._nbVisualisation = 1;
        const visuFeedback = this._serieSelect.experience[this._scenarioLancer].visuFeedback;

        const launchNextVisu = () => {
            if (this._nbVisualisation <= visuFeedback) {
                if (this._finvisu) {
                    this.lance_visu();
                    this._nbVisualisation++;
                    console.log("visu",this._nbVisualisation);
                }
                setTimeout(launchNextVisu, 2000); // Attendre 2 secondes avant de vérifier si une nouvelle visualisation peut être lancée
            }else{
                this.finSimu = true;
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
        this._isLancer=false;
        //on regenere le terrain
        this.numAction=0;
        this._serieService.getScenarioId(this._serieSelect.experience[this._scenarioLancer].scenario).subscribe(scenario => {
            this.selectScenario(scenario,'tableau_terrain2');
            this._scenarioplay=scenario;
            console.log("regen :",scenario);
        });
        //on enr l'essai
        this._nbEssai++;
        //ajout essaie
        this._newEssai.num=this._nbEssai;
        this._newEssai.resultatExperience=this.resultUser.resultat_experience[this._scenarioLancer];
        this._newEssai.temps=0.0;
        this._ispostdeplacement=true;
        this.StartTime=Date.now();
    }
    selectEntite(entite:Entite){
        if (this._selectEntite===false){
            this._entiteSelect=entite;
            this._selectEntite=true;
            this._newDeplacement=new Deplacement();
            this._newDeplacement.startPosX=entite.x;
            this._newDeplacement.startPosY=entite.y;
        }else{
            this._entiteSelect=null;
            this._selectEntite=false;
        }
        event.stopPropagation();
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
            console.log("position deplacement px: ", offsetX, offsetY);

            const parentWidth = window.innerWidth;
            const parentHeight = window.innerHeight;

            let percentX = (offsetX / parentWidth) * 100;
            let percentY = (offsetY / parentHeight) * 100;

            this.numAction++;
            //recup position tableau
            this.positionPercentage = this.getPositionPercentage(this.tableau);
            console.log("tableau :",this.tableau);

            this.tabLeft=this.positionPercentage.left;
            this.tabTop=this.positionPercentage.top;


            console.log('deplacement  en pourcentage - Left:', percentX, 'Top:', percentY);

            console.log('tab finale en pourcentage - Left:', this.tabLeft, 'Top:', this.tabTop);
            percentY=percentY-this.tabTop;
            percentX=percentX-this.tabLeft;

            // Mettre à jour les coordonnées de l'entité sélectionnée
            this._entiteSelect.x = percentX;
            this._entiteSelect.y = percentY;

            console.log('deplacement finale en pourcentage - Left:', percentX, 'Top:', percentY);
            this._entiteList.forEach(entite => {
                if (entite.id == this._entiteSelect.id) {
                    this._newDeplacement.entite=entite.id;
                    this._newDeplacement.numAction=this.numAction;
                    entite.x = percentX* (65 / 100);
                    entite.y = percentY* (58 / 100);
                    this._newDeplacement.endPosX=percentX* (65 / 100);
                    console.log("deplacement percent :",percentY,"new :",this._newDeplacement.endPosX)
                    this._newDeplacement.endPosY=percentY*(58 / 100);
                    //add deplacement dans essai
                    this._newEssai.deplacements.push(this._newDeplacement);
                    console.log("add deplacement",this._newEssai);
                }
            });
            this._entiteSelect=null;
            this._selectEntite=false;
        };
        console.log("finadd",this._scenarioplay)
        this._serieService.getDepalcementList(this._scenarioplay.id).subscribe(
            res => {
                this._depalcementList = res;
            });
        console.log("num action",this.numAction,this._depalcementList.length)
        if(this.numAction>=this._depalcementList.length){
            this.lanceFeedBack();//a la fin on lance le feed back
        }
    }

    //on lance le feedBack
    protected _feedBackScoreModal: string = "_feedBackScoreModal";
    protected _feedBackStaticModal: string = "_feedBackStaticModal";
    protected _StaticModal: string = "_StaticModal";
    protected _resultat:ResultatFeedBack=new ResultatFeedBack();
    protected nbFeed :number=0.0;
    lanceFeedBack(){
        //recuper le time
        this.EndTime=Date.now();
        this._newEssai.temps=this.StartTime-this.EndTime;
        //on envoie l'essai et recupere le resultat
        this._serieService.addEssai(this._newEssai).subscribe(
            (resultat: ResultatFeedBack) => {
                this._resultat=resultat;
            });
        //on change le modal pour feedBack
        this.resetData(this._PostDeplacementModal);

        let freqfeed: number = (this._serieSelect.experience[this._scenarioLancer].freqFeedback)/100;

        //si on lance freq
        if(freqfeed === 1 || freqfeed > (this.nbFeed / this._nbEssai)){
            this.nbFeed++;
            if (this._serieSelect.experience[this._scenarioLancer].typeFeedback==='score'){
                this.openData(this._feedBackScoreModal);
            }
            if (this._serieSelect.experience[this._scenarioLancer].typeFeedback==='static'){
                this.openData(this._feedBackStaticModal);
            }
        }else{
            this.relance();
        }
    }


    //affichage static
    protected _depalcementListFeed :DeplacementFeedBack[]=[];
    playVisualisationStatic() {
        this._depalcementListFeed =this._resultat.listError;
                // fonction récursive pour parcourir la liste avec un délai entre chaque itération
                const processDepalcement = (index: number) => {
                    // vérifie si nous avons atteint la fin de la liste
                    if (index < this._depalcementListFeed.length) {

                        const deplacement = this._depalcementListFeed[index];
                        // parcourt la liste des entités
                        this._entiteList.forEach(entite => {
                            // cherche l'entité devant être déplacée
                            if (entite.id === deplacement.entite) {

                                //on déplace l'entité
                                entite.x = deplacement.endPosX;
                                entite.y = deplacement.endPosY;
                                console.log("entite trouvée");
                                // Afficher une flèche entre les entités

                                const endX = (deplacement.endPosX * (100 / 65)) ;
                                const endY = (deplacement.endPosY * (100 / 58)) ;
                                const startX = (deplacement.startPosX * (100 / 65)) ;
                                const startY = (deplacement.startPosY * (100 / 58)) ;
                                // Obtenir les dimensions actuelles de la page
                                const containerWidth = window.innerWidth;
                                const containerHeight = window.innerHeight;
                                // Convertir les coordonnées en pourcentage en coordonnées absolues
                                const absStartX = (startX / 100)* containerWidth ;
                                const absStartY = (startY / 100)* containerHeight;
                                const absEndX = (endX / 100)* containerWidth ;
                                const absEndY = (endY / 100)* containerHeight;
                                // console.log("this.tabTop",this.tabTop,"this.tabLeft",this.tabLeft)
                                // console.log("deplacement.startPosX:",deplacement.startPosX ,"startX :" ,startX);
                                // console.log("deplacement.startPosY:",deplacement.startPosY ,"startY :" ,startY);
                                // console.log("deplacement.endPosX:",deplacement.endPosX ,"endX :" ,endX);
                                // console.log("deplacement.endPosY:",deplacement.endPosY ,"endY :" ,endY);
                                console.log("deplacement reussi:",deplacement.reussi ,"this._serieSelect.contenuFeedback :" ,this._serieSelect.contenuFeedback);
                                if(entite.type===0){

                                }
                                if (deplacement.reussi===true && this._serieSelect.contenuFeedback==='global'){
                                    this.addArrow(absStartX, absStartY, absEndX, absEndY,'green');
                                }
                                if(deplacement.reussi===false){
                                    this.addArrow(absStartX, absStartY, absEndX, absEndY,'red');
                                }

                            }
                        });
                        console.log("deplacement");
                        // Appelle la fonction processDepalcement avec l'indice suivant après un délai
                        setTimeout(() => {
                            processDepalcement(index + 1);
                        }, 2000); // délai de x seconde entre chaque déplacement
                    }else {
                        this.resetData(this._StaticModal);
                         // racharge lorsque le traitement est terminé
                    }
                };
                // démarrer le traitement avec l'indice 0
                setTimeout(() => {
                    processDepalcement(0);
                }, 2500);
    }
    //permet de dessiner la fleche
    // Déclarez une variable pour stocker les informations sur les flèches
    arrows: { startX: number, startY: number, endX: number, endY: number, length: number }[] = [];
    // Fonction pour ajouter une flèche entre deux points
    addArrow(startX: number, startY: number, endX: number, endY: number,color:string): void {
        // Créer un élément de flèche
        const arrow = document.createElement('div');
        arrow.classList.add('arrow');

        // Obtenir les dimensions actuelles de la page
        const containerWidth = window.innerWidth;
        const containerHeight = window.innerHeight;

        // Calculer la longueur et l'angle de la flèche
        const length = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        const angle = Math.atan2(endY-startY,endX-startX ) * (180 / Math.PI);
        console.log("angle :",angle,"length:",length);


        //on recalcule end en %
        const endPX = (endX / containerWidth) * 100;
        const endPY = (endY / containerHeight) * 100;
        const startPY=(startY / containerHeight) * 100;
        //on verifie si il change pas d'angle
        if ((angle >= -190 && angle <= -140) || (angle >= 140 && angle <= 190)) {
            // Appliquer les styles à la flèche
            arrow.style.position = 'absolute';
            arrow.style.width = length + 'px';
            arrow.style.height = '2px'; // Épaisseur de la flèche
            arrow.style.backgroundColor = color; // Couleur de la flèche
            arrow.style.left = (endPX+this.tabLeft)+ '%';
            arrow.style.top = (endPY+this.tabTop) + '%';
            arrow.style.transform = 'rotate(' + angle + 'deg)';
        }else{
            // Appliquer les styles à la flèch
            arrow.style.position = 'absolute';
            arrow.style.width = length + 'px';
            arrow.style.height = '2px'; // Épaisseur de la flèche
            arrow.style.backgroundColor = color; // Couleur de la flèche
            arrow.style.left = (endPX)+ '%';
            arrow.style.top = (endPY) + '%';
            arrow.style.transform = 'rotate(' + angle + 'deg)';
        }

        // Ajouter la flèche au DOM
        document.querySelector('.tableau_joueur2 .colonne_terrain2').appendChild(arrow);
    }

    lanceStatic(){
        this.openData(this._StaticModal);
        this.selectScenario(this._scenarioplay,'tableau_terrain3');
        this.playVisualisationStatic();
    }

    //on relance
    relance(){
        //si il a reussi
        if(this._resultat.reussi===true){
            this._scenarioLancer++;
            //si c le dernier scenario
            if(this._scenarioLancer === this._serieSelect.experience.length){
                //on ferme les feedBack
                this.resetData(this._feedBackScoreModal);
                this.resetData(this._feedBackStaticModal);
            }else {//sinon on lance le prochain scenario
                this.openData(this._veridModal);
                this.resetData(this._feedBackScoreModal);
                this.resetData(this._feedBackStaticModal);
            }
        }else{//si il a pas reussi
            this.resetData(this._feedBackScoreModal);
            this.resetData(this._feedBackStaticModal);
            this.openData(this._veridModal);
        }
    }

}