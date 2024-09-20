

// Classe représentant un post texte


public class PostTexte extends Post {
    private String description;

    // Constructeur 1
    public PostTexte(String contenu, Utilisateur auteur, String date, String description) {
        super(contenu, auteur, date);
        this.description=description;}

    // Constructeur 2
    public PostTexte(String contenu, Utilisateur auteur, String date, String description, int idP) {
        super(contenu, auteur, date, idP);
        this.description=description;}

    //Setters et Getters

    

    //Méthode de classe

    public String toString(){
        return("Titre du poste : "+this.description+". "+" Format: "+contenu+", Auteur: "+this.getauteur()+". ");
    
    }

    public String getURL(){
        return "";
        
    }

    public Object getImage(){
        return "" ;
        
    }


}

