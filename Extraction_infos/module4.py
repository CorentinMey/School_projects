#Module pour chercher la fréquence des AA de la protéine

import matplotlib.pyplot as plt

dico_AA= {
    'ALA': 0,   
    'ARG': 0,   
    'ASN': 0,   
    'ASP': 0,   
    'CYS': 0,   
    'GLN': 0,   
    'GLU': 0,   
    'GLY': 0,   
    'HIS': 0,   
    'ILE': 0,   
    'LEU': 0,   
    'LYS': 0,   
    'MET': 0,   
    'PHE': 0,  
    'PRO': 0,  
    'SER': 0,  
    'THR': 0,  
    'TRP': 0,  
    'TYR': 0,  
    'VAL': 0   }

dicoref_AA = {
    'ALA': 0.0902,
    'GLN': 0.0380,
    'LEU': 0.0985,
    'SER': 0.083,
    'ARG': 0.0584,
    'GLU': 0.0624,
    'LYS': 0.0493,
    'THR': 0.0555,
    'ASN': 0.0380,
    'GLY': 0.0726,
    'MET': 0.0233,
    'TRP': 0.0130,
    'ASP': 0.0547,
    'HIS': 0.0222,
    'PHE': 0.0388,
    'TYR': 0.0288,
    'CYS': 0.0129,
    'ILE': 0.0553,
    'PRO': 0.0500,
    'VAL': 0.0686
}


dico_AA3= {
    'ALA': [],   
    'ARG': [],   
    'ASN': [],   
    'ASP': [],   
    'CYS': [],   
    'GLN': [],   
    'GLU': [],   
    'GLY': [],   
    'HIS': [],   
    'ILE': [],   
    'LEU': [],   
    'LYS': [],   
    'MET': [],   
    'PHE': [],  
    'PRO': [],  
    'SER': [],  
    'THR': [],  
    'TRP': [],  
    'TYR': [],  
    'VAL': []   }

# Les fréquences des acides aminés de référence


def cpt_AA(seq_AA): #fonction qui compte le nbr de chaque AA et le met dans un dictionnaire
    for cara in seq_AA:
        dico_AA[cara]+=1
    return dico_AA

def freq_AA(seq_AA): #fonction qui utilise la précédente pour remplacer le comptage par une fréquence
    dico_AA=cpt_AA(seq_AA)
    for key in dico_AA:
        dico_AA[key]=round(dico_AA[key]/len(seq_AA),2)
    return dico_AA

def graph_frq(dico_AA):
    
    dico_AAf=dico_AA
    for key in dico_AA3:
        dico_AA3[key].append(dico_AAf[key])
    for key in dico_AA:
        dico_AA3[key].append(dicoref_AA[key])

    # Séparation des données en clés et valeurs
    categories = list(dico_AA3.keys())
    valeurs1 = [x[0] for x in dico_AA3.values()]
    valeurs2 = [x[1] for x in dico_AA3.values()]

    # Largeur des barres dans l'histogramme
    largeur_barre = 0.35

    # Position des barres sur l'axe des x
    indices = range(len(categories))

    # Création de l'histogramme
    plt.bar(indices, valeurs1, width=largeur_barre, label='Protéine testée')
    plt.bar([i + largeur_barre for i in indices], valeurs2, width=largeur_barre, label='Protéine de référence')

    # Ajout des étiquettes sur l'axe des x et légende
    plt.xlabel('Acides aminés')
    plt.ylabel('Fréquence')
    plt.xticks([i + largeur_barre / 2 for i in indices], categories)
    plt.legend()

    # Affichage de l'histogramme
    plt.show()
    return ""


