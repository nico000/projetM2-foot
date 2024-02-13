import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./@home/home.component";
import {CreationComponent} from "./@creation/creation.component";
import {ResultatComponent} from "./@resultat/resultat.component";
import {NgModule} from "@angular/core";
import {SerieComponent} from "./@serie/serie.component";





const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'creation', component: CreationComponent},
  {path: 'resultat', component: ResultatComponent},
  {path: 'serie', component: SerieComponent},
  ];


@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})

export class AppRoutingModule {}
