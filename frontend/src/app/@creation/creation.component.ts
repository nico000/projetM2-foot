import {Component, ElementRef} from "@angular/core";
import {Scenario} from "./beans/Scenario";
import {ModalService} from "../@modal/modal.service";
import {CreationService} from "./creation.service"
import {tap} from "rxjs/operators";
import {Entite} from "./beans/Entite";
import {Deplacement} from "./beans/Deplacement";



@Component({
    selector: 'creation',
    templateUrl: './creation.component.html',
    styleUrls: ['./creation.component.css' ]
})

export class CreationComponent {

    protected _scenarioList: Scenario[];
    protected _newScenario: Scenario = new Scenario();

    protected _entiteList: Entite[];
    protected _newEntite: Entite = new Entite();
    protected numero: number = 0;

    protected _addScenarioModalName: string = "addScenarioModal";

    protected LastScenario: Scenario = new Scenario();
    protected lastNom: string;
    protected lastCouloir: Number;
    protected lastZone: Number;
    protected lastCouleur:String;

    protected is_placement : boolean =false;
    protected is_deplacement : boolean=false;
    protected _selectEntite:boolean=false;
    protected _entiteSelect:Entite=null;

    protected _newDeplacement: Deplacement = new Deplacement();
    protected numAction:number = 0;
    protected _deplacementList: Deplacement[];

    constructor(private _creationService: CreationService,
                private _modalService: ModalService) {
    }

    addScenario(modal: string): void {
        this.numero = 0;
        this.lastNom = this._newScenario.nom;
        this.lastCouleur = this._newScenario.terrain_couleur;
        console.log(this.lastCouleur);
        this.lastCouloir = this._newScenario.zone_nb_couloir;
        this.lastZone = this._newScenario.zone_nb_zone;
        this._newScenario.joueur_nb=0;

        this._creationService.addScenario(this._newScenario).subscribe(
            res => {
                this._scenarioList.push(res);
            }
        )
        this.resetData(modal);
        this.objectInit();
        this.is_placement=true;
        this.is_deplacement=false;
        this.numAction=0.0;
    }

    isBlanc(){
        if (this.lastCouleur="blanc"){
            return true
        }
        return false;
    }


    openModal(modal: string): void {
        this._modalService.open(modal);
    }

    resetData(modal: string): void {
        this._modalService.close(modal);
        this._newScenario = new Scenario();
    }

    updateModeScenario(nom: string) {
        this._newScenario.mode_scene = nom;
    }

    updateCouleur(nom: string) {
        this._newScenario.terrain_couleur = nom;
    }

    updateterrainScenario(nom: string) {
        this._newScenario.terrain_taille = nom;
    }

    updateUseZone(nom: string) {
        if (nom == 'true') {
            this._newScenario.zone_use = true;
        } else {
            this._newScenario.zone_use = false;
        }
    }

    updateAfficheZone(nom: string) {
        if (nom == 'true') {
            this._newScenario.zone_display = true;
        } else {
            this._newScenario.zone_display = false;
        }
    }

    /*terrain*/
    nb_couloir(nombreDeCouloirs: number): number[] {
        return Array.from({length: nombreDeCouloirs}, (_, index) => index + 1);
    }

    //deplacemnt joueur
    private isDragging = false;
    private offsetX = 0;
    private offsetY = 0;

    // variables pour stocker la position de départ du joueur
    private initialLeft = -1;
    private initialTop = -1;

    //deplacemnt ballon
    private ballon_isDragging = false;
    private ballon_offsetX = 0;
    private ballon_offsetY = 0;


    private ballon_initialLeft = -1;
    private ballon_initialTop = -1;

    //objet
    private draggableObject: HTMLDivElement | null = null;
    private ballon_draggableObject: HTMLDivElement | null = null;

    private scroll:number=0;

    // recuperer position du tableau
    protected tableau: HTMLTableElement | null = null;
    protected tabTop:number=0.0;
    protected tabLeft:number=0.0;


    getPositionPercentage(element: HTMLTableElement | null): { top: number, left: number } {
        console.log("fonction get");
        if (!element) return { top: 0, left: 0 };

        const rect = element.getBoundingClientRect();
        const windowWidth = window.innerWidth;
        const windowHeight = window.innerHeight;

        console.log("fonction get top :",(rect.top / windowHeight) * 100,"left :",(rect.left / windowWidth) * 100);
        return {
            top: (rect.top / windowHeight) * 100,
            left: (rect.left / windowWidth) * 100
        };
    }

    protected positionPercentage = this.getPositionPercentage(this.tableau);


    objectInit() {
        // daplacement joueur
        this.draggableObject = document.getElementById('deplacableObject') as HTMLDivElement;
        this.ballon_draggableObject = document.getElementById('ballondeplacableObject') as HTMLDivElement;
        this.tableau =document.getElementById('tableau_terrain')as HTMLTableElement;

        this.initialLeft = -1;
        this.initialTop = -1;

        this.ballon_initialLeft = -1;
        this.ballon_initialTop = -1;

        //this.addEventListeners();
        //this.ballon_addEventListeners();
        this.addEventListeners(this.draggableObject, this.onMouseDown.bind(this),
            this.onMouseMove.bind(this), this.onMouseUp.bind(this), this.onMouseLeave.bind(this),
            this.onTouchStart.bind(this), this.onTouchMove.bind(this), this.onTouchEnd.bind(this),
            this.onTouchCancel.bind(this));
        this.addEventListeners(this.ballon_draggableObject, this.ballon_onMouseDown.bind(this),
            this.ballon_onMouseMove.bind(this), this.ballon_onMouseUp.bind(this), this.ballon_onMouseLeave.bind(this),
            this.ballon_onTouchStart.bind(this), this.ballon_onTouchMove.bind(this), this.ballon_onTouchEnd.bind(this),
            this.ballon_onTouchCancel.bind(this));
    }


    private addEventListeners(
        element: HTMLDivElement | null,
        onMouseDown: (e: MouseEvent) => void,
        onMouseMove: (e: MouseEvent) => void,
        onMouseUp: (e: MouseEvent) => void,
        onMouseLeave: (e: MouseEvent) => void,
        onTouchStart: (e: TouchEvent) => void,
        onTouchMove: (e: TouchEvent) => void,
        onTouchEnd: (e: TouchEvent) => void,
        onTouchCancel: (e: TouchEvent) => void
    ) {
        if (element) {
            element.addEventListener('mousedown', onMouseDown);
            element.addEventListener('touchstart', onTouchStart);

            document.addEventListener('mousemove', onMouseMove);
            document.addEventListener('touchmove', onTouchMove);

            document.addEventListener('mouseup', onMouseUp);
            document.addEventListener('touchend', onTouchEnd);

            document.addEventListener('mouseleave', onMouseLeave);
            document.addEventListener('touchleave', onMouseLeave);

            document.addEventListener('touchcancel', onTouchCancel);
        }
    }

    private onMouseDown(event: MouseEvent) {

        if (this.draggableObject) {
            this.isDragging = true;

            this.offsetX = event.clientX - this.draggableObject.getBoundingClientRect().left;
            this.offsetY = event.clientY - this.draggableObject.getBoundingClientRect().top;

            // Stocker la position de départ en pixels
            if (this.initialLeft == -1) {
                this.initialLeft = this.draggableObject.offsetLeft;
                this.initialTop = this.draggableObject.offsetTop;
            }

            this.draggableObject.style.cursor = 'grabbing';
        }
    }

    private ballon_onMouseDown(event: MouseEvent) {

        if (this.ballon_draggableObject) {
            this.ballon_isDragging = true;

            this.ballon_offsetX = event.clientX - this.ballon_draggableObject.getBoundingClientRect().left;
            this.ballon_offsetY = event.clientY - this.ballon_draggableObject.getBoundingClientRect().top;

            if (this.ballon_initialLeft == -1) {
                this.ballon_initialLeft = this.ballon_draggableObject.offsetLeft;
                this.ballon_initialTop = this.ballon_draggableObject.offsetTop;
            }

            this.ballon_draggableObject.style.cursor = 'grabbing';
        }
    }
    //fonction tablette
    private onTouchStart(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];
        if (this.draggableObject) {
            this.isDragging = true;

            this.offsetX = touch.clientX - this.draggableObject.getBoundingClientRect().left;
            this.offsetY = touch.clientY - this.draggableObject.getBoundingClientRect().top;

            // Stocker la position de départ en pixels
            if (this.initialLeft == -1) {
                this.initialLeft = this.draggableObject.offsetLeft;
                this.initialTop = this.draggableObject.offsetTop;
            }

            this.draggableObject.style.cursor = 'grabbing';
        }
    }
    private ballon_onTouchStart(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];
        if (this.ballon_draggableObject) {
            this.ballon_isDragging = true;

            this.ballon_offsetX = touch.clientX - this.ballon_draggableObject.getBoundingClientRect().left;
            this.ballon_offsetY = touch.clientY - this.ballon_draggableObject.getBoundingClientRect().top;

            if (this.ballon_initialLeft == -1) {
                this.ballon_initialLeft = this.ballon_draggableObject.offsetLeft;
                this.ballon_initialTop = this.ballon_draggableObject.offsetTop;
            }

            this.ballon_draggableObject.style.cursor = 'grabbing';
        }
    }

    private onMouseMove(event: MouseEvent ) {


        if (this.isDragging && this.draggableObject) {
            const x = event.clientX - this.offsetX;
            const y = event.clientY - this.offsetY;

            this.draggableObject.style.left = `${x}px`;
            this.draggableObject.style.top = `${y}px`;
        }
    }

    private ballon_onMouseMove(event: MouseEvent) {

        if (this.ballon_isDragging && this.ballon_draggableObject) {
            const x = event.clientX - this.ballon_offsetX;
            const y = event.clientY - this.ballon_offsetY ;

            this.ballon_draggableObject.style.left = `${x}px`;
            this.ballon_draggableObject.style.top = `${y}px`;
        }
    }

    private onTouchMove(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];

        if (this.isDragging && this.draggableObject) {
            const x = touch.clientX - this.offsetX;
            const y = touch.clientY - this.offsetY;

            this.draggableObject.style.left = `${x}px`;
            this.draggableObject.style.top = `${y}px`;
        }
    }
    private ballon_onTouchMove(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];

        if (this.ballon_isDragging && this.ballon_draggableObject) {
            const x = touch.clientX - this.ballon_offsetX;
            const y = touch.clientY - this.ballon_offsetY ;

            this.ballon_draggableObject.style.left = `${x}px`;
            this.ballon_draggableObject.style.top = `${y}px`;
        }
    }

    private onMouseUp(event: MouseEvent ) {

        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }

    }

    private ballon_onMouseUp(event: MouseEvent) {

        if (this.ballon_isDragging && this.ballon_draggableObject) {
            this.ballon_isDragging = false;
            this.ballon_draggableObject.style.cursor = 'grab';

        }
    }

    private onTouchEnd(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];

        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }
    }
    private ballon_onTouchEnd(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];
        if (this.ballon_isDragging && this.ballon_draggableObject) {
            this.ballon_isDragging = false;
            this.ballon_draggableObject.style.cursor = 'grab';
        }
    }
    private onMouseLeave(event: MouseEvent) {


        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }
    }

    private onTouchCancel(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];

        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }
    }
    private ballon_onTouchCancel(event: TouchEvent) {
        // Récupérer les coordonnées du toucher
        const touch = event.touches[0];
        if (this.ballon_isDragging && this.ballon_draggableObject) {
            this.ballon_isDragging = false;
            this.ballon_draggableObject.style.cursor = 'grab';
        }
    }

    private ballon_onMouseLeave(event: MouseEvent) {

        if (this.ballon_isDragging && this.ballon_draggableObject) {
            this.ballon_isDragging = false;
            this.ballon_draggableObject.style.cursor = 'grab';
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
                        res => {
                            this._entiteList = res;
                        }
                    )
                    resolve();
                },
                error => {
                    reject(error);
                }
            );
        });
    }

    //ajout joueur
    addJoueur() {
        console.log("Objet déplacé. Fonction 'add' appelée.");

        //recuperation position jeton
        if (this.draggableObject) {
            // Récupérer les positions finales du jeton
            const rect = this.draggableObject.getBoundingClientRect();
            const finalLeft = this.draggableObject.offsetLeft;
            const finalTop = this.draggableObject.offsetTop;

            // Afficher les positions finales dans la console
            console.log('joueur Position finale - Left:', finalLeft, 'Top:', finalTop);

            // Récupérer les dimensions de la page
            const pageWidth = window.innerWidth;
            const pageHeight = window.innerHeight;

            // Calculer les positions finales en pourcentage par rapport à la page
            let leftPercentage = (finalLeft / pageWidth) * 100;
            let topPercentage = (finalTop / pageHeight) * 100;

            //recup position tableau
            this.positionPercentage = this.getPositionPercentage(this.tableau);

            this.tabLeft=this.positionPercentage.left;
            this.tabTop=this.positionPercentage.top;
            leftPercentage=leftPercentage-this.tabLeft;
            topPercentage=topPercentage-this.tabTop;

            // Afficher les positions finales en pourcentage dans la console
            console.log('Position finale en pourcentage - Left:', leftPercentage, 'Top:', topPercentage);


            // Ajouter joueur dans la bdd
            this.numero += 1;
            this.getScenarioName(this.lastNom).then(() => {
                this._newEntite.scenario = this.LastScenario.id;
                this._newEntite.numero = this.numero;
                this._newEntite.type = 1;
                this._newEntite.x = leftPercentage;
                this._newEntite.y = topPercentage;
                this._creationService.addEntite(this._newEntite).subscribe(
                    res => {
                        this._entiteList.push(res);
                    }
                )

            }).catch(error => {
                console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
                // Gérer l'erreur ici si nécessaire
            });

            // Réinitialiser la position du joueur à sa place initiale (0%)
            this.draggableObject.style.left = `${this.initialLeft}px`;
            this.draggableObject.style.top = `${this.initialTop}px`;
        }
    }

    //ajout ballon
    addBallon() {
        console.log("Objet déplacé. Fonction 'add' appelée.");

        //recuperation position jeton
        if (this.ballon_draggableObject) {
            // Récupérer les positions finales du jeton
            const rect = this.ballon_draggableObject.getBoundingClientRect();
            const finalLeft = this.ballon_draggableObject.offsetLeft;
            const finalTop = this.ballon_draggableObject.offsetTop;

            // Afficher les positions finales dans la console
            console.log('ballon Position finale - Left:', finalLeft, 'Top:', finalTop);

            // Récupérer les dimensions de la page
            const pageWidth = window.innerWidth;
            const pageHeight = window.innerHeight;

            // Calculer les positions finales en pourcentage par rapport à la page
            let leftPercentage = (finalLeft / pageWidth) * 100;
            let topPercentage = (finalTop / pageHeight) * 100;

            //recup position tableau
            this.positionPercentage = this.getPositionPercentage(this.tableau);

            this.tabLeft=this.positionPercentage.left;
            this.tabTop=this.positionPercentage.top;
            leftPercentage=leftPercentage-this.tabLeft;
            topPercentage=topPercentage-this.tabTop;

            // Afficher les positions finales en pourcentage dans la console
            console.log('Position finale en pourcentage - Left:', leftPercentage, 'Top:', topPercentage);

            // Ajouter joueur dans la bdd
            this.getScenarioName(this.lastNom).then(() => {
                this._newEntite.scenario = this.LastScenario.id;
                this._newEntite.numero = 0;
                this._newEntite.type = 0;
                this._newEntite.x = leftPercentage;
                this._newEntite.y = topPercentage;
                this._creationService.addEntite(this._newEntite).subscribe(
                    res => {
                        this._entiteList.push(res);
                    }
                )

            }).catch(error => {
                console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
                // Gérer l'erreur ici si nécessaire
            });

            // Réinitialiser la position du ballon à sa place initiale (0%)
            this.ballon_draggableObject.style.left = `${this.ballon_initialLeft}px`;
            this.ballon_draggableObject.style.top = `${this.ballon_initialTop}px`;
        }

    }

    passEntite() {
        this.is_deplacement=true;
        this.is_placement=false;
    }

    selectEntite(entite:Entite){
        if (this._selectEntite===false){
            this._entiteSelect=entite;
            this._selectEntite=true;
            this._newDeplacement.startPosX=entite.x;
            this._newDeplacement.startPosY=entite.y;
        }else{
            this._entiteSelect=null;
            this._selectEntite=false;
        }
        console.log("select ",this._selectEntite,this._entiteSelect.id)
        event.stopPropagation();
    }

    isEntiteSelect(entite:Entite):boolean{
        if(this._selectEntite===true && entite.id==this._entiteSelect.id){
            return true;
        }
        return false;
    }

    addDeplacement(event: MouseEvent) {
        // Ajouter un délai de 500 millisecondes (par exemple)
            if (this._selectEntite == true && this.is_deplacement) {
                // Votre code existant ici
                const offsetX = event.clientX;
                const offsetY = event.clientY;
                console.log("position deplacement px: ", offsetX, offsetY);

                const parentWidth = window.innerWidth;
                const parentHeight = window.innerHeight;

                let percentX = (offsetX / parentWidth) * 100;
                let percentY = (offsetY / parentHeight) * 100;

                this.numAction += 1;
                //recup position tableau
                this.positionPercentage = this.getPositionPercentage(this.tableau);

                this.tabLeft = this.positionPercentage.left;
                this.tabTop = this.positionPercentage.top;
                console.log('tab finale en pourcentage - Left:', this.tabLeft, 'Top:', this.tabTop);
                percentY = percentY - this.tabTop;
                percentX = percentX - this.tabLeft;

                // Mettre à jour les coordonnées de l'entité sélectionnée
                this._entiteSelect.y = percentY;
                this._entiteSelect.x = percentX;
                console.log('deplacement finale en pourcentage - Left:', percentX, 'Top:', percentY);
                this._entiteList.forEach(entite => {
                    if (entite.id == this._entiteSelect.id) {
                        this._newDeplacement.scenario = entite.scenario;
                        this._newDeplacement.entite = entite.id;
                        this._newDeplacement.numAction = this.numAction;
                        entite.y = percentY;
                        entite.x = percentX;
                        this._newDeplacement.endPosX = percentX;
                        this._newDeplacement.endPosY = percentY;
                        this._creationService.addDeplacement(this._newDeplacement).subscribe();
                    }
                });
                this._entiteSelect = null;
                this._selectEntite = false;
            }
    }

    passDeplacement(){
        window.location.reload();
        this.is_deplacement=false;
    }

    delEntite(){
        this.getScenarioName(this.lastNom).then(() => {
            this._creationService.DelEntite(this.LastScenario.id).subscribe();
            this.numero -= 1;
            this._creationService.getEntiteList(this.LastScenario.id).subscribe(
                res => {
                    this._entiteList = res;
                }
            )

        }).catch(error => {
            console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
            // Gérer l'erreur ici si nécessaire
        });
    }

    delDeplacement(){
        this.getScenarioName(this.lastNom).then(() => {
            this._creationService.DelDeplacement(this.LastScenario.id).subscribe();
            this.numAction -= 1;
        }).catch(error => {
            console.error('Une erreur s\'est produite lors de la récupération du scénario :', error);
            // Gérer l'erreur ici si nécessaire
        });
    }

}