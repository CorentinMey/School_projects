import itertools
import math
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

def rech_AA(fichier_brut): #permet de chercher toutes les lignes ayant des AA
    rech=[]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste (permet d'avoir une ligne par élement de la liste)
    for lines in x:
        if lines.startswith("ATOM") and lines[13:15]=="CA": #Si la ligne commence par ATOM et que le 13ème caractère est "CA" (permet de trouver les carbones alpha de chaque AA)
            rech.append(lines) #ajouter la ligne à desc
    return rech

def rech_pos(fichier_brut): #Permet d'avoir les positions des AA dans une liste
    pos=[]
    x=rech_AA(fichier_brut)
    for elem in x:
        z=elem[24:28]+elem[31:54] #Sélectionne le numéro de résidu et les positions
        y=z.split() #Fais de cette sélection une liste
        pos.append(y)
    return pos
            

def calc_distance(fichier_brut):
    distance={}
    x=rech_pos(fichier_brut)
    combinaison = itertools.combinations(x, 2) #itertools.combinations génère toutes les combinaisons de 2 éléments (position atom 1 et 2 ensemble par exemple) à partir de la liste
    l_combi=list(combinaison) #transforme le résultat en liste
    for elem in l_combi:
        dist=math.sqrt((float(elem[1][1])-float(elem[0][1]))**2+(float(elem[1][2])-float(elem[0][2]))**2+(float(elem[1][3])-float(elem[0][3]))**2) #Fait le calcul de distance pour tous les sulfures entre eux
        distance[str(elem[0][0]),str(elem[1][0])]=dist #Crée un dictionnaire avec en clé les positions des 2 résidues et en valeur associée leur distance
    return distance

def creation_matrice(fichier_brut):
    x=calc_distance(fichier_brut) #appel des valeurs de distance
    y=(rech_pos(fichier_brut)) #appel de la liste des résidus et de leur position
    residus=[]
    for i in range(len(y)):
        residus.append(y[i][0]) #on rajoute à une liste les différents résidus
    # Création d'une matrice de contact initialisée à zéro
    contact_matrix = np.zeros((len(residus), len(residus)), dtype=float) #longueur et largeur de la matrice égales aux nbr de résidus, les données de la  matrice sont des float

    # Remplissage de la matrice de contact avec les distances
    for i, res1 in enumerate(residus): 
        for j, res2 in enumerate(residus): #Ces 2 boucles sont similaires à un itertools (fait des combinaisons entre tous les résidus possibles)
            if (res1, res2) in x:
                 contact_matrix[i, j] = x[(res1, res2)] #rajoute à l'axe x la valeur de distance des deux résidus concernés à partir du dictionnaire 
                 contact_matrix[j, i] = x[(res1, res2)] #rajoute à l'axe y la valeur de distance des deux résidus concernés à partir du dictionnaire 
    return contact_matrix, residus

def heatmap(fichier_brut):
    x=creation_matrice(fichier_brut)[0] #appel de la matrice
    y=creation_matrice(fichier_brut)[1] #appel de la liste des résidus
    # Création du heatmap avec seaborn
    sns.heatmap(x, annot=False, cmap="viridis", xticklabels=y, yticklabels=y) #création de la heatmap avec la matrice de contact en données et les résidus en valeur des axes
    plt.xlabel('Résidus')
    plt.ylabel('Résidus')
    plt.title('Matrice de Contact - Distances entre Résidus')
    plt.show()
    return ""
