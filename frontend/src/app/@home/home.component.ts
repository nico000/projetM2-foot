import {Component} from "@angular/core";
import {Scenario} from "../@creation/beans/Scenario";
import {HomeService} from "./home.service";
import {ModalService} from "../@modal/modal.service";
import {Entite} from "../@creation/beans/Entite";
import {Experience} from "./beans/Experience";
import {Examen} from "./beans/Examen";
import {Deplacement} from "./beans/Deplacement";





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
    protected _depalcementList: Deplacement[]= [];

    protected _selectScenarioModalName: string = "selectScenarioModal";

    // recuperer position du tableau
    protected tableau: HTMLTableElement | null = null;
    protected tabTop:number=0.0;
    protected tabLeft:number=0.0;

    constructor(private _homeService: HomeService,
                private _modalService: ModalService) {}

    //genere la liste des scenario
    ngOnInit(): void{
        this._homeService.getScenarioList().subscribe(
            res  =>
                this._scenarioList = res
        )
    }

    //permet de filtrer et ordonner par un type
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
    //recuperer un scenario par sont nom
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

        // Parcours de la liste _scenarioSelect
        this._scenarioSelect.forEach(scenario => {
            let exp =new Experience();
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
    updateFrequenceFeed(nom: number,experience:Experience){
        experience.freqFeedback=nom;
    }
    updateVisuFeed(nom: number,experience:Experience){
        experience.visuFeedback=nom;
    }

    resetFeedBack(modal:string){
        this._newExeperience = [];
        this._scenarioSelect= [];
        this.arrows = [];
        this.resetData(modal);

        // Supprimer toutes les flèches du DOM
        const arrowElements = document.querySelectorAll('.tableau_joueur .colonne_terrain2 .arrow');
        arrowElements.forEach(element => {
            element.remove();
        });
    }
    updateModeSequentiel(nom:string){
        this._newExam.sequencage=nom;
    }

    updateModeComplexite(nom:string){
        this._newExam.complexite=nom;
    }
    updateModeContenu(nom:string){
        this._newExam.contenuFeedback=nom;
    }
    addFeedBack(modal:string){

        this._homeService.addExamen(this._newExam).subscribe( () => {
            //ajout des exp
            this._newExeperience.forEach((experience) => {
                console.log("addExp",experience,this._newExam.nom);
                this._homeService.addExeperience(experience,this._newExam.nom).subscribe();
            });

            //reset
            this.resetFeedBack(modal);
        });

        /*
        this._homeService.addExamen(this._newExam).subscribe();
        this.resetFeedBack(modal);
        console.log("addexam");
        setTimeout(() => {
            //ajout des exp
            this._newExeperience.forEach((experience) => {
                console.log("addExp",experience,this._newExam.nom);
                this._homeService.addExeperience(experience,this._newExam.nom).subscribe();
            });

        }, 2000); // 1000 millisecondes = 1 seconde
        */
    }


    playVisualisation(scenario:Scenario) {
        //on recherche la liste des deplacement
        this._homeService.getDepalcementList(scenario.id).subscribe(
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
                                console.log("this.tabTop",this.tabTop,"this.tabLeft",this.tabLeft)
                                console.log("deplacement.startPosX:",deplacement.startPosX ,"startX :" ,startX);
                                console.log("deplacement.startPosY:",deplacement.startPosY ,"startY :" ,startY);
                                console.log("deplacement.endPosX:",deplacement.endPosX ,"endX :" ,endX);
                                console.log("deplacement.endPosY:",deplacement.endPosY ,"endY :" ,endY);
                                // Utiliser  pour dessiner la flèche
                                if (entite.type===0.0){
                                    //si c un ballon on ajoute une fleche pleine
                                    this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY ,strokeDasharray: false});
                                }else{
                                    //si c un joueur un ajoute une fleche pointiller
                                    this.arrows.push({ startX: absStartX, startY: absStartY, endX: absEndX, endY: absEndY ,strokeDasharray: true});
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
        );
        this.selectScenario(this._selectScenarioModalName,scenario);
    }
    //permet de dessiner la fleche
    // Déclarez une variable pour stocker les informations sur les flèches
    arrows: { startX: number, startY: number, endX: number, endY: number , strokeDasharray: boolean }[] = [];
    // Fonction pour ajouter une flèche entre deux points

    drawArrow(ctx: CanvasRenderingContext2D, x1: number, y1: number, x2: number, y2: number): void {
        const arrowSize: number = 25;
        const lineLength: number = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        const lineDx: number = x2 - x1;
        const lineDy: number = y2 - y1;
        const [dx, dy]: [number, number] = [Math.sign(x1 - x2), Math.sign(y1 - y2)];

        try {
            let angle: number;
            if (lineLength !== 0) {
                angle = Math.acos(lineDx / lineLength);
            }
            if (lineDy >= 0) {
                angle = (Math.PI * 2) - angle;
            }

            const arrowP0: { x: number, y: number } = { x: x2, y: y2 };
            const arrowP1: { x: number, y: number } = {
                x: arrowP0.x - (Math.sin(angle + Math.PI / 2.5) * arrowSize),
                y: arrowP0.y - (Math.cos(angle + Math.PI / 2.5) * arrowSize)
            };
            const arrowP2: { x: number, y: number } = {
                x: arrowP0.x - (Math.sin(angle + Math.PI - Math.PI / 2.5) * arrowSize),
                y: arrowP0.y - (Math.cos(angle + Math.PI - Math.PI / 2.5) * arrowSize)
            };

            ctx.beginPath();
            ctx.ellipse(x1 - 2.5, y1 - 2.5, 5, 5, 0, 0, Math.PI * 2);
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(x1, y1);
            ctx.lineTo(x2, y2);
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(arrowP0.x, arrowP0.y);
            ctx.lineTo(arrowP1.x, arrowP1.y);
            ctx.lineTo(arrowP2.x, arrowP2.y);
            ctx.closePath();
            ctx.fill();

        } catch (e) {
            console.error(e);
        }
    }
    // Méthode factice pour obtenir le contexte de rendu du canevas
    private getCanvasRenderingContext2D(): CanvasRenderingContext2D | null {
        const canvas = document.getElementById('arrow') as HTMLCanvasElement;
        if (canvas) {
            return canvas.getContext('2d');
        } else {
            console.error("Impossible de trouver l'élément canvas.");
            return null;
        }
    }


}