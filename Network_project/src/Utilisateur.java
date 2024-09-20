
import java.util.ArrayList;
import java.util.List;

//Classe répresantant un utilisateur 

public class Utilisateur {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private List<String> estBloque;
    private List<String> followers;
    private List<Object> mur;

    //Constructeur par défaut
    public Utilisateur(){
        this.nom="";
        this.prenom = "";
        this.dateNaissance = new String();
        this.estBloque = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.mur = new ArrayList<>();}
        //System.out.println("Nouvel utilisateur par défaut crée. ");}

    // Constructeur
    public Utilisateur(String nom, String prenom, String dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.estBloque = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.mur = new ArrayList<>();
        //System.out.println("Nouvel utilisateur crée. Nom: "+nom+", Prénom: "+prenom+", Date de naissance: "+dateNaissance+", Liste de personnes bloquées : vide. "+" Followers: n'est suivi par personne");
    }

    //Getters et setters

    public String getnom(){
        return this.nom;
    }
    public String getprenom(){
        return this.prenom;
    }
    public String getdate(){
        return this.dateNaissance;
    }

    public void setnom(String nom){
        this.nom=nom;}
    public void setprenom(String prenom){
        this.prenom=prenom;}
    public void setdate(String date){
        this.dateNaissance=date;}


    //Méthode pour visualiser les followers de l'utilisateur

    public String getFollower(){
        if ((this.followers).size()==0){ 
            return "Pas de followers pour cet utilisateur";
        }
        else{
            String x="";
            for (int i=0; i<(this.followers).size();i++){//Parcourt de la liste des followers
                x=x+((this.followers).get(i))+", ";} //Ajout à une chaine de caractère de chaque followers
            return (x+" sont les followers de "+this.nom+" "+this.prenom);}
    }

    //Méthode pour visualiser les posts de l'utilisateur

    public List getMur(){
        if ((this.mur).size()==0){ 
            System.out.println("Pas de posts pour cet utilisateur");
            return this.mur;
        }
        else{
            return this.mur;
        }
    }

    //Méthode pour visualiser les utilisateurs bloqués
    
    public String getBloquer(){
        String x="";
        if ((this.estBloque).size()==0){ 
            return "Pas de personne bloqué pour cet utilisateur";
        }
        else{
            for (int i=0; i<(this.estBloque).size();i++){ //Parcourt de la liste des bloqués
                x=x+((this.estBloque).get(i))+", ";} //Ajout à une chaine de caractère de chaque bloqué
            return (x+" sont les personnes bloquées de "+this.nom+" "+this.prenom);}}

    
    // Méthodes pour bloquer/débloquer un utilisateur
    public void bloquer(String bloque) {
        if (this.estBloque.contains(bloque)){//Si l'utilisateur est déjà bloqué
            System.out.println(bloque+" fait déjà parti des bloqués de  "+this.nom+" "+this.prenom);
        }
        else{
            (this.estBloque).add(bloque);
            enleverFollower(bloque); //Si un utilisateur est bloqué il ne peut plus suivre la personne
            System.out.println(bloque+" bien ajouté en tant que bloqué de "+this.nom+" "+this.prenom);
    }
    }

    public void debloquer(String bloque) {
        if (this.estBloque.contains(bloque)){
            (this.estBloque).remove(bloque);
            System.out.println(bloque+" bien retiré des bloqués de "+this.nom+" "+this.prenom);
    }
        else{
            System.out.println(bloque+" ne fait pas parti des bloqués de  "+this.nom+" "+this.prenom);
    }
    }

    // Méthode pour ajouter un follower
    public void ajouterFollower(String follower) {
        if (this.followers.contains(follower)){
            System.out.println(follower+" suit déjà "+this.nom+" "+this.prenom);
        }
        else if(this.estBloque.contains(follower)){//On vérifie que la personne n'est pas bloquée
            System.out.println(follower+" est bloqué par "+this.nom+" "+this.prenom+". Impossible de l'ajouter en tant que follower");
        }
        else{
            (this.followers).add(follower);
            System.out.println(follower+" bien ajouté en tant que follower de "+this.nom+" "+this.prenom);
    }
    }

    // Méthode pour enlever un follower
    public void enleverFollower(String follower) {
        if (this.followers.contains(follower)){
            followers.remove(follower);
            System.out.println(follower+" bien retiré des followers de "+this.nom+" "+this.prenom);
        }
        else{
            System.out.println(follower+" ne suit pas "+this.nom+" "+this.prenom);
        }
    }

    //Méthode pour visualiser les followers

    public void afficherFollower(Utilisateur u){
        for (int i=0; i<(u.followers).size();i++) //Parcourt de la liste des followers
        System.out.println((u.followers).get(i)); //On affiche chaque followers
    }

    // Méthode pour ajouter un post au mur
    public void ajouterPost(Post Poste) {
        (this.mur).add(Poste);
        if(Poste instanceof PostPhoto){
            (this.mur).add(Poste.getImage()); //On ajoute successivement l'image après son poste pour quelle s'affiche en dessous de son poste dans le mur
        }

        System.out.println(Poste.getid()+" bien ajouté en tant que poste de "+this.nom+" "+this.prenom);
    }

    // Méthode pour enlever un post
    public void enleverPost(Post Poste) {
        (this.mur).remove(Poste);
        System.out.println(Poste.getid()+" bien retiré des postes de "+this.nom+" "+this.prenom);
    }


    // Méthode toString

    public String toString(){
        return("Données sur l'utilisateur : Nom: "+this.nom+", Prénom: "+this.prenom+", Date de naissance: "+this.dateNaissance+". "+this.getBloquer()+" Followers: "+this.getFollower());
    }
}

