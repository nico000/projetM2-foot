import {Component} from "@angular/core";
import {Scenario} from "./beans/Scenario";
import {ModalService} from "../@modal/modal.service";
import {CreationService} from "./creation.service"

@Component({
    selector: 'creation',
    templateUrl: './creation.component.html',
    styleUrls: ['./creation.component.css' ]
})

export class CreationComponent {

    protected _scenarioList: Scenario[];
    protected _newScenario:Scenario= new Scenario();

    protected _addScenarioModalName: string = "addScenarioModal";

    constructor(private _creationService: CreationService,
                private _modalService: ModalService) {}

    addScenario(modal: string):void {
        this._creationService.addScenario(this._newScenario).subscribe(
            res => {
                this._scenarioList.push(res);
            }
        )
        this.resetData(modal);
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

    private isDragging = false;
    private offsetX = 0;
    private offsetY = 0;

    // Obtenez une référence vers l'élément du DOM
    private draggableObject: HTMLDivElement | null = null;

    ngAfterViewInit() {
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
            this.add();
        }
    }

    private onMouseLeave() {
        if (this.isDragging && this.draggableObject) {
            this.isDragging = false;
            this.draggableObject.style.cursor = 'grab';
        }
    }

    private add() {
        // Ajoutez ici la logique de votre fonction add
        // Par exemple, vous pouvez enregistrer les coordonnées finales du jeton ou effectuer d'autres actions.
        console.log("Objet déplacé. Fonction 'add' appelée.");
    }
}