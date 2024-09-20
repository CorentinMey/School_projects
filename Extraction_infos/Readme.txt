Commencez par installer python sur votre ordinateur
Assurez vous que le chemin d'accès de votre python est bien répertorié dans vos variables d'environnement
Afin de pouvoir utiliser convenablement notre programme Python, il faudra installer avant toute
utilisation 5 packages dans l’invite de commande, biopython, matplotlib, pandas et openpyxl.
Ouvrez l'invité de commande et effectuez les commandes suivantes:

Sur Windows:
1) pip install matplotlib
2) pip install pandas
3) pip install openpyxl
4) pip install seaborn matplotlib
4) pip install biopython

- Sur Mac : saisissez « python3.6 -m pip install [nomdupackage] » où 3.6 dans python3.6 doit être
remplacé par la version de Python installée sur votre ordinateur

Le package Biopython est une bibliothèque Python spécialisée dans le traitement et l'analyse de données
biologiques qui offre un ensemble de modules et d'outils pour travailler avec des données bioinformatiques,
tel que l’outil Numpy (bibliothèque servant au calcul numérique).
Matplotlib nous a été utile pour la création de graphiques.
La package Pandas est essentielle car c’est une bibliothèque utile pour la manipulation et l&#39;analyse de données
en Python.
Pour finir, le package openpyxl est une bibliothèque Python utilisée pour interagir avec des fichiers Excel. Il
fournit des fonctionnalités permettant de créer, lire, et modifier des fichiers Excel au format xlsx (Open
XML). Nous avons donc dû l’installer car un module de notre programme nécessite la manipulation de
données tabulaires avec Excel (pour le calcul du profil d’hydrophobicité).

Assurez vous que vous êtes connecté à internet si vous voulez chercher en ligne votre document pdb. 
Sinon pour une utilisation hors ligne assurez vous que vous avez télécharger le fichier au format pdb de votre protéine et que ce fichier 
se trouve dans le dossier de ce programme.

Le programme peut vous générer des graphiques que vous devrez enregistrer vous même.
Un fichier fasta pourra aussi être crée dans ce dossier.
Un fichier excel avec un tableau des valeurs d'hydrophobicité de la molécule pourra aussi être crée dans ce dossier. Vous pourrez à partir de celui ci créer un graphique d'hydrophobicité.
Un rapport sera généré dans ce dossier avec les données essentielles de l'analyse du fichier pdb.
Un nouveau fichier pdb sera créer où les valeurs de b_factor seront remplacés par l'hydrophobicité des résidus selon l'échelle suivante :
    
    'ALA': 16.67,     # Hydrophobe non aromatique
    'VAL': 16.67,     # Hydrophobe non aromatique
    'LEU': 16.67,     # Hydrophobe non aromatique
    'ILE': 16.67,     # Hydrophobe non aromatique
    'MET': 16.67,     # Hydrophobe non aromatique
    'PRO': 16.67,     # Hydrophobe non aromatique
    'GLY': 16.67,     # Hydrophobe non aromatique

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

    'CYS': 99.99      # Acide sulfurique

Pour visualiser la coloration de la protéine en fonction de son hydrophobicité, ouvrez le fichier pdb crée avec pymol, puis tapez spectrum b dans l'invité de commande.

Bonne utilisation !

