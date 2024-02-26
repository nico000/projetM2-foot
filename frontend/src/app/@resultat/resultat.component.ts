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
        this._resultatService.exportExel(nom, prenom).subscribe(
            (res) => {
                // Créer un blob à partir de la réponse
                let blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

                // Créer un objet URL à partir du blob
                var url = window.URL.createObjectURL(blob);

                // Créer un lien d'ancrage pour le téléchargement
                var a = document.createElement('a');
                a.href = url;
                a.download = 'donnees.xlsx'; // Nom du fichier téléchargé
                document.body.appendChild(a);

                // Cliquer sur le lien d'ancrage pour déclencher le téléchargement
                a.click();

                // Nettoyer l'URL de l'objet après le téléchargement
                window.URL.revokeObjectURL(url);
            },
            (error) => {
                // Gérer les erreurs
                console.error("Une erreur s'est produite lors de la récupération des données:", error);
            }
        );
    }
}