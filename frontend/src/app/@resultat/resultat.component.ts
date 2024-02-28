import {Component} from "@angular/core";
import {HomeService} from "../@home/home.service";
import {ModalService} from "../@modal/modal.service";
import {Utilisateur} from "./beans/Utilisateur";
import {ResultatService} from "./resultat.service";

@Component({
    selector: 'resultat',
    templateUrl: './resultat.component.html',
    styleUrls: ['./resultat.component.css' ]
})


export class ResultatComponent {

    protected _listeUser:Utilisateur[]=[];
    protected _response :ArrayBuffer;
    constructor(private _resultatService: ResultatService,
                private _modalService: ModalService) {}
    ngOnInit(): void{
        this._resultatService.getUserList().subscribe(
            res  =>
                this._listeUser = res
        )
    }

    exportExel(nom, prenom) {
        const url = `http://localhost:8080/report/export?nom=${nom}&prenom=${prenom}`;
        window.open(url, '_blank');
    }
}