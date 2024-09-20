

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

// Classe abstraite représentant un post
abstract class Post {
    private static int compteurId = 0;

    protected String contenu;
    protected String date;
    protected int likes;
    protected Utilisateur auteur;
    protected List<String> likeur;
    protected List<Post> Reponse;
    protected int idP;
    public static Map<Integer, Post> objetsParId = new HashMap<>(); // Map pour stocker les objets par leur ID

    // Constructeur 1
    public Post(String contenu, Utilisateur auteur, String date) {
        this.contenu = contenu;
        this.date = date;
        this.likes = 0;
        this.auteur = auteur;
        this.likeur = new ArrayList<>();
        this.Reponse = new ArrayList<>();
        this.idP = ++compteurId;
        objetsParId.put(idP, this); //Map qui relie l'id d'un poste à son poste
    }

    // Constructeur 2

    public Post(String contenu, Utilisateur auteur, String date, int idP) {
        this.contenu = contenu;
        this.date = date;
        this.likes = 0;
        this.auteur = auteur;
        this.likeur = new ArrayList<>();
        this.Reponse = new ArrayList<>();
        this.idP = idP;
        objetsParId.put(idP, this);
    }

    //Setters et Getters

    public String getauteur(){
        return (this.auteur.getnom()+" "+this.auteur.getprenom());
    }
    public String getcontenu(){
        return this.contenu;
    }
    public String getlikes(){
        int x=this.likes;
        return (x+" personnes ont liké ce post");
    }

    public String getdate(){
        String x=this.date;
        return ("Ce post a été crée le "+x+". ");
    }

    public int getid(){
        return idP;
    }

    public void setauteur(Utilisateur u){
        this.auteur=u;
    }

    public void setcontenu(String c){
        this.contenu=c;
    }


    // Méthodes pour liker et disliker
    private void liker() {
        likes++;
    }

    private void disliker() {
        likes--;
    }

      //Méthode pour ajouter un utilisateur à une liste de like d'un poste

      public void liker(String u){
        if (this.likeur.contains(u)){
            System.out.println("Cet utilisateur a déjà liker ce poste");
        }
        else{
        liker();
        (this.likeur).add(u);
    }
    }

    public void disliker(String u){
        if (this.likeur.contains(u)){
            disliker();
            (this.likeur).remove(u);
        }
        else{
            System.out.println("Cet utilisateur n'a pas liker ce poste");
        }
    }

    public static Post recupererObjetParId(int id) { //Méthode pour obtenir le post depuis so id
        return objetsParId.get(id);
    }

    //Méthodes abstraites

    abstract Object getImage(); //Methode abstraite pour video ou image

    abstract String getURL(); //Methode abstraite pour l'URL

    //Méthode pour visualiser la liste de like d'un poste

    public String voirLikes(){
        if ((this.likeur).size()==0){ 
            return "Pas de likes pour ce post";
        }
        else{
        String x="";
        for (int i=0; i<(this.likeur).size();i++){//Parcourt de la liste des likeurs
        x=x+((this.likeur).get(i))+", ";} //Ajout à une chaine de caractère de chaque likeur
        return (x+" sont les likeurs de ce poste");}
    }
    
    //Méthode pour ajouter ou enlever une réponse à un poste

    public void reponse(Post poste){
        Reponse.add(poste);
    }
    
    public void SuppReponse(Post poste){
        Reponse.remove(poste);
    }

    //Méthode pour visualiser les réponses à un poste
    
    public String voirReponse(){
        if ((this.Reponse).size()==0){ 
            return "Pas de réponse pour ce post";
        }
        else{
        String x="";
        for (int i=0; i<(this.Reponse).size();i++){ //Parcourt de la liste des réponses
        x=x+"\n"+((this.Reponse).get(i));} //Ajout à une chaine de caractère de chaque reponse
        return ("Les réponses à ce poste sont : "+x);}
    }

}