
import javax.swing.ImageIcon;

// Classe représentant un post photo


public class PostPhoto extends Post {
    private String description;
    private ImageIcon image;
    private String URL;
    


    // Constructeur 1
    public PostPhoto(String contenu, Utilisateur auteur, String date, String description, String URL, int idP) {
        super(contenu, auteur, date, idP);
        this.description = description;
        this.URL = URL;
    }

    // Constructeur 2
    public PostPhoto(String contenu, Utilisateur auteur, String date, String description, String URL, String Path) {
        super(contenu, auteur, date);
        this.description = description;
        this.image = new ImageIcon(Path);
        this.URL = URL;
    }

    //Getters et Setters

    
    public ImageIcon getImage(){
        return this.image;
        
    }

    public String getURL(){
        return URL;
        
    }


    public String getDescription(){
        return this.description;
        
    }

    public void setDescription(String d){
        this.description=d;
    }

    public void setImage(String i){
        this.image=new ImageIcon(i);
    }



    // Méthode toString

    public String toString(){
        return("Titre du poste : "+this.description+". "+" Format: "+contenu+", URL : "+this.URL+", Auteur : "+(this.auteur).getnom()+" "+(this.auteur).getprenom()+". ");
    }
    }

