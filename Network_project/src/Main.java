import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;



public class Main {


    // Méthode pour ouvrir une image
    public static void ouvrirImage(ImageIcon image1){
        if (image1 != null) {
            Image image = image1.getImage(); // Convertit ImageIcon en Image
            ImageIcon scaledImageIcon = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Redimensionne l'image
            JLabel imageLabel = new JLabel(scaledImageIcon); // Crée un JLabel pour afficher l'image
            JFrame frame = new JFrame(); // Crée une JFrame pour afficher l'image
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(imageLabel); // Ajoute le JLabel contenant l'image à la JFrame
            frame.pack(); // Ajuste la taille de la fenêtre pour s'adapter à l'image
            frame.setVisible(true); // Rend la JFrame visible
        } else {
            System.out.println("L'image est nulle.");
        }
    }

    // Méthode pour ouvrir un mur
    public static void ouvrirMur(Utilisateur utilisateur) {
        List<Object> mur = utilisateur.getMur(); //on recupère la liste de poste de l'utilisateur

        System.out.println();
        System.out.println("Voici le mur de "+utilisateur.getprenom()+" "+utilisateur.getnom()+" :");
    
        for (int i = 0; i < mur.size(); i++) { //On parcourt tous les objets de la liste
            Object contenu = mur.get(i);
    
                if (contenu instanceof Post) { //si l'objet lu est un poste
                    Post contenu2 = (Post) contenu; 
                    System.out.println();
                    System.out.println(contenu); //On affiche le toString du poste
                    System.out.println(contenu2.voirReponse()); //On affiche les reponses au poste
                    System.out.println(" Nombre de likes: "); 
                    System.out.println(contenu2.voirLikes()); //On affiche les likes
                    System.out.println();
            } else if (contenu instanceof ImageIcon) { //si l'objet lu est une image
                ouvrirImage((ImageIcon) contenu); // on l'ouvre avec ouvrirImage
            } 
        }
    }

    //Méthode pour importer un utilisateur depuis la BDD

    public static Utilisateur importerU(){
        Scanner clavier=new Scanner (System.in);
        System.out.println("Tapez le prénom de l'utilisateur à rechercher"); //On stocke le nom et prenom de l'utilisateur à rechercher
        String prenom=clavier.nextLine();
        System.out.println("Tapez le nom de l'utilisateur à rechercher");
        String nom=clavier.nextLine();

        try {
            Class.forName("org.sqlite.JDBC"); 
            Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
            Statement st = connex.createStatement();
            ResultSet rs = st.executeQuery("SELECT nom,prenom,dateNaiss, idU FROM USERS WHERE nom='"+nom+"' AND prenom='"+prenom+"';"); //on cherche dans la bdd les informations sur l'utilisateur ayant ce nom et prenom

            String idU=rs.getString(4); //On stocke son idU dans une variable pour pouvoir l"appeler plus tard
            
            Utilisateur u5=new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3)); //On crée un nouvel utilisateur java avec ces données
                
            ResultSet rs2 = st.executeQuery("SELECT idW FROM USERS JOIN WALLS ON USERS.'idU'=WALLS.'#idU' WHERE USERS.'idU'='"+idU+"' " ); //On cherche le mur correspondant à l'utilisateur cherché

            String idW=rs2.getString(1); //On stocke l'id du mur dans une variable
            

        importerF(u5, idU); //On importe les followers de l'utilisateur


        List<Post> postList = new ArrayList<>(); //On crée une liste de poste
        ResultSet rs3 = st.executeQuery("SELECT format, dateC, texte, urlIMG, urlVID, idP FROM POSTS WHERE POSTS.'#idW'='"+idW+"' "); //On cherche les informations des postes sur le mur de l'utilisateur
            
            while (rs3.next()) {//On parcourt les postes 1 à 1
                System.out.println();
                if(rs3.getString(4)!=null){ //Si il y a une URLimage
                PostPhoto post=new PostPhoto(rs3.getString(1), u5 , rs3.getString(2),rs3.getString(3),rs3.getString(4), rs3.getInt(6)); //On crée un poste photo avec les informations de la bdd 
                importerL(post, rs3.getInt(6)); //On importe les likes avec l'idP du poste
                postList.add(post); //On ajoute ce poste à la liste
                }
                if(rs3.getString(5)!=null){//Si il y a une URLvideo, on fait pareil mais on crée un post video
                    PostVideo post=new PostVideo(rs3.getString(1), u5 , rs3.getString(2),rs3.getString(3),rs3.getString(5), rs3.getInt(6));
                    importerL(post, rs3.getInt(6));
                    postList.add(post);
                    }
                else{//Sinon on fait pareil mais on fait un post texte
                    PostTexte post=new PostTexte(rs3.getString(1), u5 , rs3.getString(2),rs3.getString(3), rs3.getInt(6));
                    importerL(post, rs3.getInt(6));
                    postList.add(post);
                }
                
            }
            
            for (int i = 0; i < postList.size(); i++) {//On parcourt la liste de poste
                Post p1=postList.get(i);
                int idrep = postList.get(i).getid();//On récupère l'id du poste
                ResultSet rs4 = st.executeQuery("SELECT `#idP_Init` FROM COMMENTS WHERE COMMENTS.`#idP_Rep`='" + idrep + "'"); //On cherche l'id du post d'origine
                if (rs4.next()) { // Vérifie si la requête renvoie un résultat (donc si notre poste à un poste d'origine et donc que notre poste est une réponse)
                    int idPInit = rs4.getInt(1);//On récupère l'id du poste d'origine
                    Post Initial = Post.recupererObjetParId(idPInit); //on récupère grâce à une Hashmap le poste à partir de son ID
                    Initial.reponse(p1);//On ajoute notre poste à la liste des réponses du poste initial 
            }
            else{
                u5.ajouterPost(p1); //On ajoute le poste en tant que poste indépendant
            }

        }
                
                ouvrirMur(u5); //On affiche le mur de l'utilisateur qu'on vient de récupérer
                connex.close(); // Fermeture de la connexion à la base de données
                return u5; //On renvoit l'utilisateur
            
        }catch (Exception e) {
            e.printStackTrace();
            Utilisateur u=new Utilisateur(); ////On renvoit l'utilisateur par défaut en cas d'echec
            return u;
        }
    }

    public static void importerF(Utilisateur u5, String idU){//Methode pour importer les followers et bloqués des utilisateurs
        try {
        Class.forName("org.sqlite.JDBC");
        Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
        Statement st = connex.createStatement();
        ResultSet rs2 = st.executeQuery("SELECT FOLLOWERS.'#idU', blocked FROM USERS JOIN WALLS on USERS.idU=WALLS.'#idU' JOIN FOLLOWERS on FOLLOWERS.'#idW'=WALLS.idW WHERE USERS.idU='"+idU+"' "); //On récupère les id des utilisateurs et leur statut quand ils ont une relation avec l'idU de notre utilisateur

        List followers=new ArrayList<String>(); //On crée une liste pour les followers
        List blocked=new ArrayList<String>(); //On crée une liste pour les bloqués
        while (rs2.next()) {//On parcourt les résultats
            if (rs2.getInt(2)==1){//si le statut est de 1=bloqué
                blocked.add(rs2.getString(1));//On ajoute à la liste de bloqué
            }
            else{
                followers.add(rs2.getString(1)); //On ajoute à la liste de followers
            }
        
    }


    for (int i = 0; i < blocked.size(); i++) {//On parcourt la liste de bloqués
        ResultSet rs3 = st.executeQuery("SELECT prenom, nom FROM USERS WHERE USERS.idU='"+blocked.get(i)+"' "); //On sélectionne le nom et prénom des bloqués
        String x=rs3.getString(1)+" "+rs3.getString(2);
        u5.bloquer(x); //On les ajoute aux bloqués
    }
    for (int i = 0; i < followers.size(); i++) {//Pareil pour la liste de followers
        ResultSet rs3 = st.executeQuery("SELECT prenom, nom FROM USERS WHERE USERS.idU='"+followers.get(i)+"' ");
        String x=rs3.getString(1)+" "+rs3.getString(2);
        u5.ajouterFollower(x);
    }
    connex.close(); // Fermeture de la connexion à la base de données
    } catch (Exception e) {
    e.printStackTrace();
    }
}

public static void importerL(Post poste, int idP){//méthode pour importer les likes de chaque post
    try {
    Class.forName("org.sqlite.JDBC");
    Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
    Statement st = connex.createStatement();
    ResultSet rs2 = st.executeQuery("SELECT prenom, nom FROM LIKES JOIN USERS ON USERS.'idU'=LIKES.'#idU' WHERE LIKES.'#idP'='"+idP+"' "); //On prend les nom et prenom des personnes qui ont liké le post

    while (rs2.next()) {//On parcourt les résultats
    poste.liker(rs2.getString(1)+" "+rs2.getString(2)); //On ajoute à la liste de like du poste les infos
    }

connex.close(); // Fermeture de la connexion à la base de données
} catch (Exception e) {
e.printStackTrace();
}
}

public static void nouveauPost(String idU, String idW ){//méthode pour ajouter un nouveau poste avec notre idU et notre idW
    Scanner clavier=new Scanner (System.in);
    System.out.println("Tapez le texte du poste");
    String texte=clavier.nextLine();
    System.out.println("Tapez le format du poste");
    String format=clavier.nextLine();
    System.out.println("Tapez l'URL image du poste");
    String URLimage=clavier.nextLine();
    System.out.println("Tapez l'URL video du poste");
    String URLvideo=clavier.nextLine();
    System.out.println("Tapez la date du poste");
    String date=clavier.nextLine();
    try {
    Class.forName("org.sqlite.JDBC");
    Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
    PreparedStatement St = connex.prepareStatement("INSERT INTO POSTS (texte, format, urlIMG, urlVID, dateC, `#idU`,`#idW`) VALUES ('"+texte+"', '"+format+"', '"+URLimage+"', '"+URLvideo+"', '"+date+"', '"+idU+"', '"+idW+"');");//On insère une nouvelle ligne dans la table post avec toutes les données du poste
    St.executeUpdate();
    St.close();
    connex.close(); // Fermeture de la connexion à la base de données
    } catch (Exception e) {
    e.printStackTrace();
    }
}

public static void supprimerPost(String idU){//méthode pour supprimer un poste
    Scanner clavier=new Scanner (System.in);
    System.out.println("Tapez l'ID du post à supprimer");
    String idP=clavier.nextLine();
    try {
        Class.forName("org.sqlite.JDBC");
        Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
        Statement st = connex.createStatement();
        ResultSet rs = st.executeQuery("SELECT `#idU` FROM POSTS WHERE POSTS.idP='"+idP+"' "); //On prend l'id du créateur du poste
        String idVerif=rs.getString(1);
        if (idVerif.equals(idU)){//On verifie si le poste nous appartient
            PreparedStatement St = connex.prepareStatement("DELETE FROM POSTS WHERE idP = '"+idP+"';"); //supression du poste
            St.executeUpdate();
            St.close();
            connex.close(); // Fermeture de la connexion à la base de données
            System.out.println("Post "+idP+" bien supprimé"); 
        }
        else{
            System.out.println("Ce post ne vous appartient pas, impossible de le supprimer");
        }
    } catch (Exception e) {
    e.printStackTrace();
    }
}

public static void modifierPost(String idU ){
    Scanner clavier=new Scanner (System.in);
        System.out.println("Tapez l'ID du post à modifier");
        String idP=clavier.nextLine();
        System.out.println("Tapez le texte du poste");
        String texte=clavier.nextLine();
        System.out.println("Tapez le format du poste");
        String format=clavier.nextLine();
        System.out.println("Tapez l'URL image du poste");
        String URLimage=clavier.nextLine();
        System.out.println("Tapez l'URL video du poste");
        String URLvideo=clavier.nextLine();
        System.out.println("Tapez la date du poste");
        String date=clavier.nextLine();
    try {
        Class.forName("org.sqlite.JDBC");
        Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
        Statement st = connex.createStatement();
        ResultSet rs = st.executeQuery("SELECT `#idU` FROM POSTS WHERE POSTS.idP='"+idP+"' "); //On prend l'id du créateur du poste
        String idVerif=rs.getString(1);
        if (idVerif.equals(idU)){//On verifie si le poste nous appartient
        PreparedStatement St = connex.prepareStatement("UPDATE POSTS SET texte ='"+texte+"', format = '"+format+"', urlIMG = '"+URLimage+"', urlVID = '"+URLvideo+"', dateC = '"+date+"' WHERE idP = '"+idP+"';"); //modif du poste
        St.executeUpdate();
        St.close();
        connex.close(); // Fermeture de la connexion à la base de données
        System.out.println("Post "+idP+" bien modifié"); 
    } 
    else{
        System.out.println("Ce post ne vous appartient pas, impossible de le modifier");
    }
    }catch (Exception e) {
    e.printStackTrace();
}}

public static String[] interfaceU(){//Methode pour se connecter
    Scanner clavier=new Scanner (System.in);
        System.out.println("Veuillez vous connecter :");
        System.out.println("Votre login ?");
        String login=clavier.nextLine();
        System.out.println("Votre mot de passe ?");
        String mdp=clavier.nextLine();
    try {
        Class.forName("org.sqlite.JDBC");
        Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
        Statement st = connex.createStatement();
        ResultSet rs = st.executeQuery("SELECT mdp, idU FROM USERS WHERE login='"+login+"'"); //On cherche le mdp qui équivaut à notre login dans la bdd
        if ((rs.getString(1)).equals(mdp)){ //Si le mdp correspond à celui qu'on a tapé
            String idU=rs.getString(2);
            ResultSet rs2 = st.executeQuery("SELECT idW FROM USERS JOIN WALLS ON USERS.'idU'=WALLS.'#idU' WHERE USERS.'idU'='"+idU+"' " ); //On cherche l'id du mur
            String idW=rs2.getString(1);
            connex.close(); // Fermeture de la connexion à la base de données
            String[] resultats = {idU, idW, login}; //On retourne l'id utilisateur, du mur et le login
            return resultats;
        }
        else{//si le mdp n'est pas le bon
            connex.close(); // Fermeture de la connexion à la base de données  
            System.out.println("Echec d'authentification, veuillez réessayer.");
            interfaceU();
            String[] resultats = {null, null};
            return resultats;
        }
    } catch (Exception e) {
    e.printStackTrace();
    return null;
}}
 
    

    public static void main(String[] args) throws Exception{

        //Accès Logiciel

        /* //Test sur les utilisateurs

        Utilisateur u1=new Utilisateur("Meyvaert", "Corentin", "01/12/2002");
        Utilisateur u2=new Utilisateur("TENA", "Elise", "30/09/2002");
        Utilisateur u3=new Utilisateur("ROUGET", "Simon", "14/03/2002");
        u1.ajouterFollower("Elise Tena");
        u1.ajouterFollower("Elise Tena");
        u1.enleverFollower("Simon Rouget");
        u1.enleverFollower("Elise Tena");
        u1.ajouterFollower("Simon Rouget");
        u1.bloquer("Simon Rouget");
        u1.bloquer("Simon Rouget");
        u1.ajouterFollower("Simon Rouget");
        u1.debloquer("Simon Rouget");
        u1.debloquer("Simon Rouget");
        System.out.println(u1);
        System.out.println(u3);

        //Test sur les posts

        PostPhoto p1=new PostPhoto("test", u1, "30/03/2024", "Photo test de Mimie", "www.firefox.com", "C:\\Users\\coren\\Documents\\Polytech\\S8\\Java\\Projet_Reseau\\Mimie.jpg");
        PostPhoto p2=new PostPhoto("test", u1, "30/03/2024", "Photo test de Pat", "www.firefox.com", "C:\\Users\\coren\\Documents\\Polytech\\S8\\Java\\Projet_Reseau\\Patrick.jpg");
        
        ImageIcon imageIcon = p1.getImage(); // Récupère l'objet ImageIcon
        ImageIcon imageIcon2 = p2.getImage(); // Récupère l'objet ImageIcon
        ouvrirImage(imageIcon2);
        ouvrirImage(imageIcon);

        //Vidéo et photo
        PostTexte t1=new PostTexte("Mon premier post", u3, "15/01/2022","J'aime les voitures lol");
        System.out.println(t1);

        PostVideo v1=new PostVideo("Ma première vidéo", u3, "15/01/2022", "vidéo de Squezzie", "https://www.youtube.com/watch?v=LSI-tcAuk90");
        System.out.println(v1);

        v1.liker("Corentin MEYVAERT"); v1.liker("Elise Tena"); v1.liker("Elise Tena"); v1.disliker("Rouget Simon"); v1.disliker("Corentin Meyvaert");
        System.out.println(v1.voirLikes());
        System.out.println(v1.getlikes());

        //Test sur les murs

        u1.ajouterPost(p1);
        u1.ajouterPost(p2);
        u3.ajouterPost(t1);
        u3.ajouterPost(v1);
        ouvrirMur(u3);

        //Test sur les réponses

        v1.reponse(t1);
        System.out.println(v1.voirReponse());

        //Test sur les modifications de post

        p1.setauteur(u2);
        p1.setDescription("blabla");
        p1.setImage(("C:\\Users\\coren\\Documents\\Polytech\\S8\\Java\\Projet_Reseau\\Patrick.jpg"));
        System.out.println(p1);
        ImageIcon imageIcon3 = p1.getImage();
        ouvrirImage(imageIcon3);

        

        //Test sur les dates

        System.out.println( p1.getdate()); */

        //Accès BDD


        System.out.println("Bonjour et bienvenue sur votre réseau social préféré !");
        String[] resultats1=interfaceU();
        String idU = resultats1[0];
        String idW = resultats1[1];
        System.out.println("Authentification réussie");
        String boucle="0";
        while (boucle.equals("0")){
        System.out.println("Que désirez-vous faire ? Taper le numéro de votre choix puis appuyez sur entrée.");
        System.out.println("1-Visualiser le compte d'un utilisateur \n2-Ajouter un post \n3-Modifier un post \n4-Supprimer un poste \n5-Se déconnecter");
        Scanner clavier=new Scanner (System.in);
        String choix=clavier.nextLine();
        if (choix.equals("1")){
            Utilisateur u=importerU();
            System.out.println("Que désirez-vous faire ? Taper le numéro de votre choix puis appuyez sur entrée.");
            System.out.println("1-Voir les followers \n2-Voir les bloqués \n3-Liker un post \n4-Ne rien faire");
            String choix2=clavier.nextLine();
            if (choix2.equals("1")){
                System.out.println(u.getFollower());
        }
            else if (choix2.equals("2")){
                System.out.println(u.getBloquer());
        }
            else if (choix2.equals("3")){
                
                System.out.println("idP du Post que vous voulez liker ?");
                String choixIdP=clavier.nextLine();
                try {
                    Class.forName("org.sqlite.JDBC");
                    Connection connex = DriverManager.getConnection("jdbc:sqlite:src/myLink.db");
                        PreparedStatement St = connex.prepareStatement("INSERT INTO LIKES (`#idU`,`#idP`) VALUES ( '"+idU+"', '"+choixIdP+"');");
                        St.executeUpdate();
                        St.close();
                    connex.close(); // Fermeture de la connexion à la base de données
                    } catch (Exception e) {
                    e.printStackTrace();
                    }}
                else{
                     System.out.println("Numéro choisi non valide. Veuillez réessayer");
                    }
        }
        else if(choix.equals("2")){
            nouveauPost( idU, idW);
        }
        else if(choix.equals("3")){
            modifierPost(idU);
        }
        else if(choix.equals("4")){
            supprimerPost(idU);
        }
        else if(choix.equals("5")){
            System.out.println("Vous êtes bien déconnecté");
            boucle="1";
        }
        else{
            System.out.println("Numéro choisi non valide. Veuillez réessayer");
        }
    }}
}

