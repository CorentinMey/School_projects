#Création d'un nouveau fichier PDB qui remplace le B factor par une valeur qui décrit le caractère physico-chimique de l'AA

#Regroupement des acides aminés en classe (6 classes donc 6 valeurs différentes entre 0 et 100, 100/6= 16.67) (pas 1000 car crée des problèmes, nbr trop grand et donc fusionne après les valeurs d'avant dans le doc pdb)
#Hydrophobe non aromatique = 0
#Hydrophobe aromatique = 16,67
#Polaires neutres = 33,34
#Polaires acides = 50,01
#Polaires basiques = 66,67
#Acide sulfurique= 83,34

dico_class = {
    'ALA': 16.67,          # Hydrophobe non aromatique
    'VAL': 16.67,          # Hydrophobe non aromatique
    'LEU': 16.67,          # Hydrophobe non aromatique
    'ILE': 16.67,          # Hydrophobe non aromatique
    'MET': 16.67,          # Hydrophobe non aromatique
    'PRO': 16.67,          # Hydrophobe non aromatique
    'GLY': 16.67,          # Hydrophobe non aromatique

    'PHE': 33.34,     # Hydrophobe aromatique
    'TYR': 33.34,     # Hydrophobe aromatique
    'TRP': 33.34,     # Hydrophobe aromatique

    'SER': 50.01,     # Polaires neutres
    'THR': 50.01,     # Polaires neutres
    'ASN': 50.01,     # Polaires neutres
    'GLN': 50.01,     # Polaires neutres
    'CYS': 50.01,     # Polaires neutres

    'ASP': 66.67,     # Polaires acides
    'GLU': 66.67,     # Polaires acides

    'LYS': 83.34,     # Polaires basiques
    'ARG': 83.34,     # Polaires basiques
    'HIS': 83.34,     # Polaires basiques

    'CYS': 99.99  # Acide sulfurique
}

def rech_bfactor(fichier_brut):
    new_file=[]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste (permet d'avoir une ligne par élement de la liste)
    for lines in x:
        if lines.startswith("ATOM"): #Si la ligne commence par ATOM (permet de trouver les lignes ATOM)
            new_file.append(lines.replace(str(lines[61:66]),str(dico_class[lines[17:20]]))) #Remplace la valeur du B factor par la valeur associé à la classe de l'AA correspondant
        else:
            new_file.append(lines)
    return new_file

def create_pdb(new_file, code_prot): #Crée un nouveau fichier PDB avec la nouvelle valeur de B factor
    f=open(f"PDBv2_{code_prot}.pdb","w")
    for lines in new_file:
        f.write(lines)
        f.write("\n")
    f.close()
    return "Nouveau fichier PDB crée avec succès."


