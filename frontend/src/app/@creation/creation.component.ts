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
        this._newScenario.joueur_nb=0.0;
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




}