import {Component} from "@angular/core";
import { Router, NavigationEnd } from '@angular/router'; // Assurez-vous d'avoir cette importation

@Component({
    selector: 'navigation-bar',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css' ]
})

/** Composant qui represente le header du site */
export class HeaderComponent {

    constructor(private router: Router) {}
    ngOnInit() {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.updateActiveLink(event.url);
            }
        });
    }

    updateActiveLink(url: string) {
        const links = document.querySelectorAll("nav.menu ul li.boutton a");

        // Supprimer la classe active de tous les liens
        links.forEach(link => {
            link.classList.remove("active");
        });

        // Ajouter la classe active au lien correspondant à l'URL actuelle
        const activeLink = document.querySelector(`nav.menu ul li.boutton a[routerLink='${url}']`);
        if (activeLink) {
            activeLink.classList.add("active");
        }
    }

}
