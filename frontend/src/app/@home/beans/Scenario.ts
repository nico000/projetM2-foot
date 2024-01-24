export class Scenario{
    public id:Number=0.0;
    public nom:string="";
    public mode_scene:string="";
    public terrain_couleur:string="";
    public terrain_taille:string="";
    public joueur_nb:Number=0.0;

    //feedBack
    public feedback_frequence:Number=0.0;
    public feedback_condition:Number=0.0;
    public feedback_type:string="";

    //zone
    public zone_use:boolean=false;
    public zone_display:boolean=false;
    public zone_nb_zone:Number=0.0;
    public zone_nb_couloir:Number=0.0;
    public zone_epaisseur:Number=0.0;
}