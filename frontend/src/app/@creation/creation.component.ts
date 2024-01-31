import {Component, ElementRef} from "@angular/core";
import {Scenario} from "./beans/Scenario";
import {ModalService} from "../@modal/modal.service";
import {CreationService} from "./creation.service"
import {tap} from "rxjs/operators";
import {Entite} from "./beans/Entite";

@Component({
    selector: 'creation',
    templateUrl: './creation.component.html',
    styleUrls: ['./creation.component.css' ]
})

export class CreationComponent {

    protected _scenarioList: Scenario[];
    protected _newScenario:Scenario= new Scenario();

    protected _entiteList: Entite[];
    protected _newEntite:Entite= new Entite();
    protected numero:number=0;

    protected _addScenarioModalName: string = "addScenarioModal";

    protected LastScenario : Scenario= new Scenario();
    protected lastNom : string;
    protected lastCouloir : Number;
    protected lastZone : Number;

    constructor(private _creationService: CreationService,
                private _modalService: ModalService) {}

    addScenario(modal: string):void {
        this.numero=0;
        this.lastNom=this._newScenario.nom;
        this.lastCouloir = this._newScenario.zone_nb_couloir;
        this.lastZone=this._newScenario.zone_nb_zone;

        this._creationService.addScenario(this._newScenario).subscribe(
            res => {
                this._scenarioList.push(res);
            }
        )
        this.resetData(modal);
        this.objectInit();
    }


    openModal(modal: string):void {
        this._modalService.open(modal);
    }

    resetData(modal: string):void {
        this._modalService.close(modal);
        this._newScenario = new Scenario();
    }

    updateModeScenario (nom: string){
        this._newScenario.mode_scene=nom;
    }

    updateterrainScenario (nom: string){
        this._newScenario.terrain_taille=nom;
    }
    updateUseZone (nom: string){
        if (nom=='true'){
            this._newScenario.zone_use=true;
        }else{
            this._newScenario.zone_use=false;
        }
    }
    updateAfficheZone (nom: string){
        if (nom=='true'){
            this._newScenario.zone_display=true;
        }else{
            this._newScenario.zone_display=false;
        }
    }

    /*terrain*/
    nb_couloir(nombreDeCouloirs: number): number[] {
        return Array.from({ length: nombreDeCouloirs }, (_, index) => index + 1);
    }

    //deplacemnt joueur
    private isDragging = false;
    private offsetX = 0;
    private offsetY = 0;

    // Obtenez une référence vers l'élément du DOM
    private draggableObject: HTMLDivElement | null = null;
    // variables pour stocker la position de départ du joueur
    private initialLeft ;
    private initialTop ;


    objectInit() {
        // Après l'initialisation de la vue, obtenez une référence vers l'élément du DOM
        this.draggableObject = document.getElementById('deplacableObject') as HTMLDivElement;
        this.addEventListeners();
    }

    private addEventListeners() {
        if (this.draggableObject) {
            this.draggableObject.addEventListener('mousedown', (e: MouseEvent) => this.onMouseDown(e));
            document.addEventListener('mousemove', (e: MouseEvent) => this.onMouseMove(e));
            document.addEventListener('mouseup', () => this.onMouseUp());
            document.addEventListener('mouseleave', () => this.onMouseLeave());
        }
    }

    private onMouseDown(event: MouseEvent) {
        if (this.draggableObject) {
            this.isDragging = true;

            this.offsetX = event.clientX - this.draggableObject.getBoundingClientRect().left;
            this.offsetY = event.clientY - this.draggableObject.getBoundingClientRect().top;

            // Stocker la position de départ en pixels
            this.initialLeft = this.draggableObject.offsetLeft;
            this.initialTop = this.draggableObject.offsetTop;
            this.draggableObject.style.cursor = 'grabbing';
        }
    }

    private onMouseMove(event: MouseEvent) {
        if (this.isDragging && this.draggableObject) {
            const x = event.clientX - this.offsetX;
            const y = event.clientY - this.offsetY;

            this.draggableObject.style.left = `${x}px`;
            this.draggableObject.style.top = `${y}px`;
        }
    }

    private onMouseUp() {
        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';

            // Appeler la fonction add lorsque le déplacement est terminé
            //this.addJoueur();
        }
    }

    private onMouseLeave() {
        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }
    }


    //recupere l'id du dernier scenario ajouter
    private getScenarioName(nom: string): Promise<any> {
        return new Promise((resolve, reject) => {
            this._creationService.getScenarioNom(nom).subscribe(
                res => {
                    this.LastScenario = res;
                    //console.log("last scenario nom:", this.lastNom, " id :", this.LastScenario.id);
                    this._creationService.getEntiteList(this.LastScenario.id).subscribe(
                        res  =>
                            this._entiteList = res

                    )
                    resolve();
                },
                error => {
                    reject(error);
                }
            );
        });
    }

    addJoueur() {
        console.log("Objet déplacé. Fonction 'add' appelée.");

        //recuperation position jeton
        if (this.draggableObject) {
            // Récupérer les positions finales du jeton
            const rect = this.draggableObject.getBoundingClientRect();
            const finalLeft = this.draggableObject.offsetLeft;
            const finalTop = this.draggableObject.offsetTop;

            // Afficher les positions finales dans la console
            console.log('Position finale - Left:', finalLeft, 'Top:', finalTop);

            // Récupérer les dimensions de la page
            const pageWidth = window.innerWidth;
            const pageHeight = window.innerHeight;

            // Calculer les positions finales en pourcentage par rapport à la page
            const leftPercentage = (finalLeft / pageWidth) * 100;
            const topPercentage = (finalTop / pageHeight) * 100;

            // Afficher les positions finales en pourcentage dans la console
            console.log('Position finale en pourcentage - Left:', leftPercentage, 'Top:', topPercentage);

            // Ajouter joueur dans la bdd
            this.numero += 1;
            this.getScenarioName(this.lastNom).then(() => {
                this._newEntite.scenario=this.LastScenario.id;
                this._newEntite.numero=this.numero;
                this._newEntite.type=1;
                this._newEntite.y=leftPercentage;
                this._newEntite.x=topPercentage;
                this._creationService.addEntite(this._newEntite).subscribe(
                    res => {
                        this._entiteList.push(res);
                    }
                )

            }).catch(error => {
                console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
                // Gérer l'erreur ici si nécessaire
            });

            // Réinitialiser la position du jeton à sa place initiale (0%)
            this.draggableObject.style.left = `${this.initialLeft}px`;
            this.draggableObject.style.top = `${this.initialTop}px`;
        }
    }


}