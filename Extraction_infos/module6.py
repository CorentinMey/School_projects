####Module pour la recherche de ponts-disulfures

import itertools
import math

def rech_pondi(fichier_brut): #permet de chercher toutes les lignes ayant des sulfures 
    rech=[]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste (permet d'avoir une ligne par élement de la liste)
    for lines in x:
        if lines.startswith("ATOM") and lines[13]=="S": #Si la ligne commence par ATOM et que le 13ème caractère est "S" (permet de trouver les sulfures de chaque AA)
            rech.append(lines) #ajouter la ligne à desc
    return rech, f"Présence de {len(rech)} cystéines dans ce résidu"

def rech_pos(fichier_brut): #Permet de n'avoir que les positions des sulfures dans une liste
    pos=[]
    x=rech_pondi(fichier_brut)[0]
    for elem in x:
        z=elem[24:28]+elem[31:54]
        y=z.split()
        pos.append(y)
    return pos
            

def calc_distance(fichier_brut):
    distance=[]
    x=rech_pos(fichier_brut)
    y=rech_pos_hel(fichier_brut)
    combinaison = itertools.combinations(x, 2) #itertools.combinations génère toutes les combinaisons de 2 éléments (position atom 1 et 2 ensemble par exemple) à partir de la liste
    l_combi=list(combinaison) #transforme le résultat en liste
    for elem in l_combi:
        cpt=0
        dist=math.sqrt((float(elem[1][1])-float(elem[0][1]))**2+(float(elem[1][2])-float(elem[0][2]))**2+(float(elem[1][3])-float(elem[0][3]))**2) #Fait le calcul de distance pour tous les sulfures entre eux
        if dist<3:
            for item in y:
                if float(item[0])<float(elem[0][0])<float(item[1]) and float(item[0])<float(elem[1][0])<float(item[1]):
                    cpt+=1
            if cpt==0:
                Npondi=f"Ponts disulfures entre {elem[0][0]} et {elem[1][0]}"
                distance.append(Npondi)
                distance.append(dist)
    return distance

def rech_helice(fichier_brut):
    rech=[]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste (permet d'avoir une ligne par élement de la liste)
    for lines in x:
        if lines.startswith("HELIX"): #Si la ligne commence par HELIX (permet de trouver les informations sur les helices alpha)
            rech.append(lines) #ajouter la ligne à desc
    return rech, f"Présence de {len(rech)} hélices alpha dans cette protéine"

def rech_pos_hel(fichier_brut): #Permet de n'avoir que les positions des hélices dans une liste
    pos=[]
    x=rech_helice(fichier_brut)[0]
    for elem in x:
        z=elem[23:27]+elem[35:39]
        y=z.split()
        pos.append(y)
    return pos







