
// Classe représentant un post vidéo

public class PostVideo extends Post {
    private String description;
    private String URL;

    // Constructeur 1
    public PostVideo(String contenu, Utilisateur auteur, String date, String description, String URL) {
        super(contenu, auteur, date);
        this.description = description;
        this.URL = URL;
    }

    // Constructeur 2
    public PostVideo(String contenu, Utilisateur auteur, String date, String description, String URL, int idP) {
        super(contenu, auteur, date, idP);
        this.description = description;
        this.URL = URL;
    }

    //Setters et Getters
    
    public String getURL(){
        return this.URL;
        
    }

    public Object getImage(){
        return "";
        
    }
    
    
    //Méthode de classe

    public String toString(){
        return("Titre du poste : "+this.description+". "+" Format: "+contenu+", Auteur: "+this.getauteur()+". Lien de la vidéo: "+URL);
    
    }
    
}

