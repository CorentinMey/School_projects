#Module pour construire la séquence au format fasta de la protéine
import module2
import re
def rech_AA(fichier_brut): #Marche pas, je le laisse là au cas où il faut utiliser le module re
    desc=["Liste des acides aminés de la protéine:"]
    x=fichier_brut.split("\n")
    motif=re.compile("(ATOM){1}[.]{9}(CA){1}")
    for lines in x:
        if motif in lines:
            desc.append(lines[18:20])
    return desc

def rech_AA2(fichier_brut): #cherche les acides aminés de la protéine dans le fichier PDB
    desc=[]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste (permet d'avoir une ligne par élement de la liste)
    for lines in x:
        if lines.startswith("ATOM") and lines[13:15]=="CA": #Si la ligne commence par ATOM et que les 13ème et 14ème caractères sont "CA" (permet de trouver les carbones alpha de chaque AA, qui ne sont présent qu'une fois par AA)
            desc.append(lines[17:20]) #ajoutez à desc les caractères 17 à 19 de la ligne qui correspondent au code à 3 lettres de l'AA
    return desc


#Dictionnaire avec pour chaque AA un codon correspondant

acides_amines2 = {
    'ALA': 'A',  # Alanine
    'ARG': 'R',  # Arginine
    'ASN': 'N',  # Asparagine
    'ASP': 'D',  # Acide aspartique
    'CYS': 'C',  # Cystéine
    'GLU': 'E',  # Acide glutamique
    'GLN': 'Q',  # Glutamine
    'GLY': 'G',  # Glycine
    'HIS': 'H',  # Histidine
    'ILE': 'I',  # Isoleucine
    'LEU': 'L',  # Leucine
    'LYS': 'K',  # Lysine
    'MET': 'M',  # Méthionine
    'PHE': 'F',  # Phénylalanine
    'PRO': 'P',  # Proline
    'SER': 'S',  # Sérine
    'THR': 'T',  # Thréonine
    'TRP': 'W',  # Tryptophane
    'TYR': 'Y',  # Tyrosine
    'VAL': 'V'   # Valine
}

# Exemple d'utilisation du dictionnaire


def fasta(res_rech_AA): #transforme notre liste d'AA en une séquence nucléotidique
    liste=[]
    for AA in res_rech_AA: #pour chaque AA de la prot
        liste.append(acides_amines2[AA]) #utilisation du dictionnaire pour mettre dans la liste le codon correspondant à l'AA de notre protéine
    extract=[item[0] for item in liste] #utilisation de liste de comprehension pour extraire les éléments de chaque liste = la ligne d'avant donnait une liste de liste sur laquelle un join ne pouvait pas être fait, cette fonction permet de transformer la liste de liste en liste
    resf="".join(extract) #transformation en chaine de caractère
    return resf

def creation_fasta(fasta, desc, code_prot): #fonction pour créer un fichier fasta dans l'ordinateur 
    fich_fasta=[] 
    y=desc.split("\n") #crée une liste de la description du fichier PDB. Pour récupérer le header du fasta
    z=y[1].split() #garde le 2ème élements de la description (=header) et le transforme en liste
    a="|".join(z) #sépare chaque élement du header par |
    fich_fasta.append(a) #ajout du header
    fich_fasta.append(fasta) #ajout de la seq nucléotidique
    fich_fasta_2="\n".join(fich_fasta) 
    fich_fasta_3=fich_fasta_2.replace("HEADER",">") 
    fichier_fasta=open("fichier_fasta_%s.txt"%code_prot,"w") #crée un fichier txt incluant le code PDB de la protéine dans son nom
    fichier_fasta.write(fich_fasta_3) #écrit le fasta dans ce fichier
    fichier_fasta.close()
    return "Un fichier fasta a été crée dans votre dossier python"

def affich_fasta(fasta, desc): #fait la même chose que la fonction creation_fasta sauf qu'elle affiche le fasta à la place de l'enregistrer dans un fichier
    fich_fasta=[]
    y=desc.split("\n")
    z=y[1].split()
    a="|".join(z)
    fich_fasta.append(a)
    fich_fasta.append(fasta)
    fich_fasta_2="\n".join(fich_fasta)
    fich_fasta_3=fich_fasta_2.replace("HEADER",">")
    return "Voici la séquence de votre protéine au format fasta:", fich_fasta_3       
    

