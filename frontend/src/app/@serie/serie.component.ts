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
    ngOnInit(): void {//initialisation de depart
        this._serieService.getSerieList().subscribe(
            res => {
                this._examList = res;
                this._examList.forEach(examSerie => {
                    let serie: Serie = {
                        id: examSerie.id,
                        nom: examSerie.nom,
                        sequencage: examSerie.sequencage,
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
    //fonction de trie
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
    //recuperer une experience
    getExperience(id: number, serie: Serie): void {
        this._serieService.getExperience(id).subscribe(
            res => {
                serie.experience.push(res);
                console.log("get test", res);
            }
        );
    }
    //calcul du nombre de couloir ou zone
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
        this.resetData(this._simulationModal);
        this.arrows=[];
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
        if (this._serieSelect.sequencage==='aleatoire'){
            this._serieSelect.experience=this.shuffleArray(this._serieSelect.experience);
        }
        this.openData(modal);
    }
    //fonction qui change aleatoirement les experience
    shuffleArray(array: Experience[]): Experience[] {
        const shuffledArray = array;
        for (let i = shuffledArray.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
        }
        return shuffledArray;
    }
    //update les config a partir bouton
    //update info user
    updateSex(sex: string){
        this._newUtilisateur.sex=sex;
    }

    // Update des données à selectionner du formulaire utilisateur
    updateFormUser(prop:string , etat:boolean){
        this._newUtilisateur[prop] = etat;
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
                                const endX = (deplacement.endPosX * (100 / 65)) ;
                                const endY = (deplacement.endPosY * (100 / 58)) ;
                                const startX = (deplacement.startPosX * (100 / 65)) ;
                                const startY = (deplacement.startPosY * (100 / 58)) ;
                                // Obtenir les dimensions actuelles de la page
                                const containerWidth = window.innerWidth;
                                const containerHeight = window.innerHeight*0.9;
                                // Convertir les coordonnées en pourcentage en coordonnées absolues
                                const absStartX = (startX / 100)* containerWidth ;
                                const absStartY = (startY / 100)* containerHeight;
                                const absEndX = (endX / 100)* containerWidth ;
                                const absEndY = (endY / 100)* containerHeight;
                                console.log("this.tabTop",this.tabTop,"this.tabLeft",this.tabLeft)
                                console.log("deplacement.startPosX:",deplacement.startPosX ,"startX :" ,startX);
                                console.log("deplacement.startPosY:",deplacement.startPosY ,"startY :" ,startY);
                                console.log("deplacement.endPosX:",deplacement.endPosX ,"endX :" ,endX);
                                console.log("deplacement.endPosY:",deplacement.endPosY ,"endY :" ,endY);
                                // Utiliser  pour dessiner la flèche
                                if (entite.type===0.0){
                                    //si c un ballon on ajoute une fleche pleine
                                    this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                        ,strokeDasharray: false,strokeColor: '#000'});
                                }else{
                                    //si c un joueur un ajoute une fleche pointiller
                                    this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                        ,strokeDasharray: true,strokeColor: '#000'});
                                }
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
            this.arrows=[];
            this.playVisualisation(scenario);
        });
    }

    //lancer la simualtion en fonction du nombre de visualisation
    lance_simu() {
        console.log("complexité",this._serieSelect.complexite);
        if (this._serieSelect.complexite==='global'){
            console.log("complexité global confimre");
            const launchNextVisu = (i: number) => {
                if (i < this._serieSelect.experience.length) {
                    console.log("i: ",i);
                    this.finSimu = false;
                    this._isLancer = true;
                    this._nbVisualisation = 1;
                    this._scenarioLancer=i;
                    const visuFeedback = this._serieSelect.experience[this._scenarioLancer].visuFeedback;

                    const handleNextIteration = () => {
                        i++;
                        launchNextVisu(i); // Appeler la prochaine itération
                    };

                    const launchVisu = () => {
                        if (this._nbVisualisation <= visuFeedback) {
                            if (this._finvisu) {
                                this.lance_visu();
                                this._nbVisualisation++;
                                console.log("visu", this._nbVisualisation);
                            }
                            setTimeout(launchVisu, 2000); // Attendre 2 secondes avant de vérifier si une nouvelle visualisation peut être lancée
                        } else {
                            handleNextIteration(); // Passer à l'itération suivante une fois que toutes les visualisations sont terminées
                        }
                    };

                    launchVisu(); // Lancer la première visualisation
                }else {
                    this._scenarioLancer=0.0;
                    this.finSimu = true; // Mettre finSimu à true une fois que toutes les itérations sont terminées
                }
            };
            launchNextVisu(0);
        }else {
            this.finSimu = false;
            this._isLancer = true;
            this._nbVisualisation = 1;
            const visuFeedback = this._serieSelect.experience[this._scenarioLancer].visuFeedback;

            const launchNextVisu = () => {
                if (this._nbVisualisation <= visuFeedback) {
                    if (this._finvisu) {
                        this.lance_visu();
                        this._nbVisualisation++;
                        console.log("visu", this._nbVisualisation);
                    }
                    setTimeout(launchNextVisu, 2000); // Attendre 2 secondes avant de vérifier si une nouvelle visualisation peut être lancée
                } else {
                    this.finSimu = true;
                }
            };

            launchNextVisu(); // Lancer le premier appel de la visualisation
        }
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
    //lancer l'essaie
    toTest(){
        console.log("_scenarioLancer ::: ",this._scenarioLancer);
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
            percentY=percentY;
            percentX=percentX;

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
            if (this._serieSelect.complexite==='global'){
                //si c le dernier scenario
                if(this._scenarioLancer<this._serieSelect.experience.length-1){
                    console.log("this.enregiste_global();",this._scenarioLancer,"length",this._serieSelect.experience.length)
                    this.enregiste_global();
                }else {
                    console.log("this.feedBackGlobal();",this._scenarioLancer,"length",this._serieSelect.experience.length)
                    this.feedBackGlobal();
                }
            }else {
                this.lanceFeedBack();//a la fin on lance le feed back
            }
        }
    }

    //on lance le feedBack
    protected _feedBackScoreModal: string = "_feedBackScoreModal";
    protected _feedBackStaticModal: string = "_feedBackStaticModal";
    protected _StaticModal: string = "_StaticModal";
    protected _resultat:ResultatFeedBack=new ResultatFeedBack();
    protected _resultatGlobal:ResultatFeedBack []=[];
    protected nbFeed :number=0.0;
    lanceFeedBack(){
        //recuper le time
        this.EndTime=Date.now();
        this._newEssai.temps=(this.EndTime-this.StartTime)/1000;//temps en seconde
        this._feedisStart=false;
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
                                const containerHeight = window.innerHeight*0.9;
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
                                // Utiliser  pour dessiner la flèche
                                if (entite.type===0.0){
                                    //si c un ballon on ajoute une fleche pleine
                                    if (deplacement.reussi===true && this._serieSelect.contenuFeedback==='global'){
                                        //si reussi et global
                                        this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                            ,strokeDasharray: false,strokeColor: '#32cd32'});
                                    }
                                    if(deplacement.reussi===false){
                                        //si mouvement rate
                                        this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                            ,strokeDasharray: false,strokeColor: '#b22222'});
                                    }
                                }else{
                                    //si c un joueur un ajoute une fleche pointiller
                                    if (deplacement.reussi===true && this._serieSelect.contenuFeedback==='global'){
                                        this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                            ,strokeDasharray: true,strokeColor: '#32cd32'});
                                    }
                                    if(deplacement.reussi===false){
                                        this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY
                                            ,strokeDasharray: true,strokeColor: '#b22222'});
                                    }
                                }


                            }
                        });
                        console.log("deplacement");
                        // Appelle la fonction processDepalcement avec l'indice suivant après un délai
                        setTimeout(() => {
                            processDepalcement(index + 1);
                        }, 2000); // délai de x seconde entre chaque déplacement
                    }
                };
                // démarrer le traitement avec l'indice 0
                setTimeout(() => {
                    processDepalcement(0);
                }, 2500);
    }
    //permet de dessiner la fleche
    // Déclarez une variable pour stocker les informations sur les flèches
    arrows: { startX: number, startY: number, endX: number, endY: number , strokeDasharray: boolean , strokeColor: string}[] = [];
    protected _feedisStart:boolean =false;
    // Fonction pour ajouter une flèche entre deux points

    lanceStatic(){
        this._feedisStart=true;
        this.openData(this._StaticModal);
        this.selectScenario(this._scenarioplay,'tableau_terrain3');
        this.arrows=[];
        this.playVisualisationStatic();
    }

    //on relance
    relance(){
        if (this._serieSelect.complexite==='global'){
            let reussiTotal:boolean=true;
            //si c le dernier feed
            if(this._scenarioLancer===this._serieSelect.experience.length-1) {

                for (let i =0;i<this._serieSelect.experience.length;i++){
                    console.log(" i: ",i,"resultatGlobal[i]:",this._resultatGlobal[i]);
                    if(this._resultatGlobal[i].reussi === false){
                        reussiTotal=false;
                    }
                }
                //si tout es reussi
                if(reussiTotal===true){
                    //on ferme les feedBack
                    this.resetData(this._feedBackScoreModal);
                    this.resetData(this._feedBackStaticModal);
                }else{//sinon on relance
                    this.selectScenario(this._scenarioplay, 'tableau_terrain')
                    this.arrows = [];
                    this.openData(this._veridModal);
                    this.resetData(this._feedBackScoreModal);
                    this.resetData(this._feedBackStaticModal);
                }
            }else{//sinon on lance le prochain feed
                this._scenarioLancer++;
                console.log(" feed lancer:",this._scenarioLancer);
                this._resultat=this._resultatGlobal[this._scenarioLancer];
                if (this._serieSelect.experience[this._scenarioLancer].typeFeedback === 'score') {
                    this.openData(this._feedBackScoreModal);
                }
                if (this._serieSelect.experience[this._scenarioLancer].typeFeedback === 'static') {
                    this.openData(this._feedBackStaticModal);
                }
            }
        }else {
            //si il a reussi
            if (this._resultat.reussi === true) {
                this._scenarioLancer++;
                //si c le dernier scenario
                if (this._scenarioLancer === this._serieSelect.experience.length) {
                    //on ferme les feedBack
                    this.resetData(this._feedBackScoreModal);
                    this.resetData(this._feedBackStaticModal);
                } else {//sinon on lance le prochain scenario
                    this.selectScenario(this._scenarioplay, 'tableau_terrain')
                    this.arrows = [];
                    this.openData(this._veridModal);
                    this.resetData(this._feedBackScoreModal);
                    this.resetData(this._feedBackStaticModal);
                }
            } else {//si il a pas reussi
                this.resetData(this._feedBackScoreModal);
                this.resetData(this._feedBackStaticModal);
                this.arrows = [];
                this.selectScenario(this._scenarioplay, 'tableau_terrain');
                this.openData(this._veridModal);
            }
        }
    }

    quitteFeedStatic(){
        this.resetData(this._StaticModal);
        this.openData(this._feedBackStaticModal);
    }

    //complexite = global
    enregiste_global(){
        //recuper le time
        this.EndTime = Date.now();
        this._newEssai.temps = (this.EndTime - this.StartTime) / 1000;//temps en seconde
        this._feedisStart = false;
        //on envoie l'essai et recupere le resultat
        console.log("addEssaie :",this._newEssai);
        this._serieService.addEssai(this._newEssai).subscribe(
            (resultat: ResultatFeedBack) => {
                console.log("addEssaie sub:");
                this._resultat = resultat;
                this._resultatGlobal.push(resultat);
            });
        //on change le modal pour feedBack
        this.resetData(this._PostDeplacementModal);

        this._scenarioLancer++;
        this._newEssai=new Essai();
        this.toTest();
    }
    feedBackGlobal(){
        //recuper le time
        this.EndTime = Date.now();
        this._newEssai.temps = (this.EndTime - this.StartTime) / 1000;//temps en seconde
        this._feedisStart = false;
        //on envoie l'essai et recupere le resultat
        console.log("addEssaie :",this._newEssai);
        this._serieService.addEssai(this._newEssai).subscribe(
            (resultat: ResultatFeedBack) => {
                console.log("addEssaie sub:");
                this._resultat = resultat;
                this._resultatGlobal.push(resultat);
            });
        //on change le modal pour feedBack
        this.resetData(this._PostDeplacementModal);

        this._scenarioLancer=0.0;
        let freqfeed: number = (this._serieSelect.experience[this._scenarioLancer].freqFeedback) / 100;

        //si on lance freq
        if (freqfeed === 1 || freqfeed > (this.nbFeed / this._nbEssai)) {
            this.nbFeed++;
            this._resultat=this._resultatGlobal[this._scenarioLancer];
            if (this._serieSelect.experience[this._scenarioLancer].typeFeedback === 'score') {
                this.openData(this._feedBackScoreModal);
            }
            if (this._serieSelect.experience[this._scenarioLancer].typeFeedback === 'static') {
                this.openData(this._feedBackStaticModal);
            }
        }else{
            this.relance();
        }
    }
}