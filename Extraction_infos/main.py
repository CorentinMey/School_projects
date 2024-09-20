#Importation des modules

import module1
import module2
import module3
import module4
import module5
import module6
import module7
import module8
import pandas as pd
import numpy as np

###########MAIN###########

#Appel du code de la protéine

interface=input("Veuillez taper le code PDB de la protéine à analyser: ")
code_prot=interface.upper()
#code_prot="1crn".upper()

#Module 1: Importation du fichier PDB

i=0
while i<1:
    interface0=input("Pour ouvrir votre fichier à partir d'un code en ligne tapez 1 \nPour ouvrir votre fichier à partir d'un fichier en interne tapez 2\nTapez votre numéro et appuyez sur entrée pour continuer: ")
    if interface0=="1":
        fichier_brut=module1.importation_online(code_prot)
        i+=1
        if "HEADER" not in fichier_brut: #si le fichier ne contient pas header donc qu'il est vide 
            print(fichier_brut)
            i=2
    elif interface0=="2":
        fichier_brut=module1.importation_intern(code_prot)
        i+=1
        if "HEADER" not in fichier_brut: #si le fichier ne contient pas header donc qu'il est vide 
            print("\nLe fichier PDB de cette protéine n'a pas été trouvé sur votre ordinateur\n")
            i=0
    else:
        print("numéro choisi non valide")

if i==2:
    input("Assurez vous que le code PDB tapé est correct") #Pour laisser le temps à l'utilisateur de fermer le programme

numero=0
while numero not in range(1,9):
    print("Menu utilisateur: \nVeuillez taper le numéro du module que vous voulez lancer.")
    print("1-Description du fichier PDB \n2-Recherche des acides aminés de la protéine et création d'un fichier fasta \n3-Fréquence en acides aminés \n4-Profil d'hydrophobicité \n5-Ponts disulfures \n6-Création d'un nouveau fichier PDB avec un B-factor dépendant de l'hydrophobicité des acides aminés \n7-Création du matrice de contact \n8-Lancer tout le programme")
    try:
        numero=int(input("Votre numéro ?"))
    except:
        print("Votre numéro n'est pas valide, veuillez réessayer")
    if numero not in range(1,9):
        print("Votre numéro n'est pas valide, veuillez réessayer")

#Module 2: Description du fichier PDB
res_desc=module2.rech_desc(fichier_brut)
if numero==1 or numero==8:
    print(res_desc)

#Module 3: Recherche des acides aminés de la protéine et création d'un fichier fasta
res_rech_AA2=module3.rech_AA2(fichier_brut)
seq_fasta=module3.fasta(res_rech_AA2)
if numero==2 or numero==8:
    print(f"Liste des acides aminés de la protéine:\n{res_rech_AA2}")
    interface1=input("Pour enregistrer la séquence au format fasta de votre protéine tapez 1 \nPour visualiser la séquence au format fasta de votre protéine tapez 2\nPour faire les 2 tapez 3\nTapez votre numéro et appuyez sur entrée pour continuer: ")
    if interface1=="1":
        print(module3.creation_fasta(seq_fasta, res_desc, code_prot))
    elif interface1=="2":
        print(module3.affich_fasta(seq_fasta, res_desc)[0])
        print(module3.affich_fasta(seq_fasta, res_desc)[1])
    elif interface1=="3":
        print(module3.creation_fasta(seq_fasta, res_desc, code_prot))
        print(module3.affich_fasta(seq_fasta, res_desc)[0])
        print(module3.affich_fasta(seq_fasta, res_desc)[1])
    else:
        print("Vous n'avez pas choisi un numéro valide")

#Module 4: Fréquence en acides aminés
res_freq=module4.freq_AA(res_rech_AA2)
tableau_freq=pd.DataFrame(list(res_freq.items()), columns=['Acide Aminé', 'Fréquence'])
if numero==3 or numero==8:
    print(tableau_freq)
    print(module4.graph_frq(res_freq))

#Module 5: Profil d'hydrophobicité
hydrophobicity_values =(module5.calculate_hydrophobicity(seq_fasta))
tableau_hydro=pd.DataFrame({'Position': list(range(5, 5 + len(hydrophobicity_values))),'Hydrophobicity': hydrophobicity_values})
if numero==4 or numero==8: 
    print(tableau_hydro)
    print(module5.save_to_excel(f"hydrophobicity_profile_{code_prot}.xlsx", code_prot, hydrophobicity_values))

#Module 6: Ponts disulfures
pos_sulfure=module6.rech_pos(fichier_brut)
tableau_sulfure=pd.DataFrame({'Pos CYS': [i[0] for i in pos_sulfure],'x': [i[1] for i in pos_sulfure],'y': [i[2] for i in pos_sulfure],'z': [i[3] for i in pos_sulfure]})
distance_pondi=module6.calc_distance(fichier_brut)
distance_pondi2="\n".join(map(str, distance_pondi))
pos_hel=module6.rech_pos_hel(fichier_brut)
if numero==5 or numero==8:
    print(module6.rech_pondi(fichier_brut)[1])
    print(tableau_sulfure.to_string(justify='center', index=False)) #Pour centrer les colonnes
    print(distance_pondi2)
    print(module6.rech_helice(fichier_brut)[1])

#Module 7: Création d'un nouveau fichier PDB

if numero==6 or numero==8:
    new_file=module7.rech_bfactor(fichier_brut)
    print(module7.create_pdb(new_file, code_prot))

#Puis ouvrir le fichier sur pymol et taper spectrum b dans l'invité de commande pour avoir la coloration voulue

#Module 8: Création du matrice de contact

if numero==7 or numero==8:
    matrice=module8.heatmap(fichier_brut)
    print(matrice)

# Création du rapport dans une variable
rapport = f"""
Rapport d'Analyse de Protéine - Code PDB: {code_prot}

1. {module2.rech_desc(fichier_brut)}

2. Liste des Acides Aminés :
   {" ".join(res_rech_AA2)}

3. Séquence Fasta :
   {module3.affich_fasta(seq_fasta, res_desc)[1]}

4. Fréquence en Acides Aminés :
    {tableau_freq}

5. Profil d'Hydrophobicité :
    {tableau_hydro}

6. Ponts Disulfures :
   {module6.rech_pondi(fichier_brut)[1]}
   
   {tableau_sulfure.to_string(justify='center', index=False)}
   
   {distance_pondi2}
   
   {module6.rech_helice(fichier_brut)[1]}

"""

# Écriture du rapport dans un fichier
with open(f"rapport_proteine_{code_prot}.txt", "w") as fichier_rapport:
    fichier_rapport.write(rapport)

print("Rapport généré avec succès.")

input("appuyez sur entrée pour terminer")
