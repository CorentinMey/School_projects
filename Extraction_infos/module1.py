#Importation des dépendances
import urllib.request
import ssl
import sys
import platform

#====================================================================================================================

                                        # #Récupération contenu fichier PDB #

#====================================================================================================================

def importation_online(code):
    """Fonction pour récupérer la fiche PDB en ligne"""
    liste_fich = []
    try:
        context = ssl._create_unverified_context()
        u=urllib.request.urlopen("https://files.rcsb.org/view/"+code.upper()+".pdb", context=context)
        pdblines=u.readlines()
        u.close()
    except:
        return("Problème lors de la lecture du fichier: \n" + "https://files.rcsb.org/view/"+code.upper()+".pdb\nVeuillez fermer le programme et réessayer")
    else:
        for ligne in pdblines:
            liste_fich.append(ligne.decode("utf8").strip() + "\n")
            fichier = "".join(liste_fich)
        return fichier


def importation_intern(code):
    """Fonction pour récupérer la fiche PDB en ligne"""
    liste_fich = []
    codem=code.lower()
    try:
        u=open(codem+".pdb","r")
        pdblines=u.readlines()
        u.close()
    except:
        return("Problème lors de la lecture du fichier: \n" + "https://files.rcsb.org/view/"+code.upper()+".pdb\n Veuillez fermer le programme et réessayer")
    else:
        for ligne in pdblines:
            liste_fich.append(ligne.strip() + "\n")
            fichier = "".join(liste_fich)
        return fichier
