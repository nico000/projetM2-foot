export class Scenario{
    public id:number=0.0;
    public nom:string="";
    public mode_scene:string="scene";
    public terrain_couleur:string="vert";
    public terrain_taille:string="";
    public joueur_nb:number=0.0;


    //zone
    public zone_use:boolean=false;
    public zone_display:boolean=false;
    public zone_nb_zone:number=6.0;
    public zone_nb_couloir:number=5.0;
    public zone_epaisseur:number=0.0;
}