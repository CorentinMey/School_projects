import pandas as pd



# Définition de l'échelle d'hydrophobicité de Fauchere et Pliska (1983)
hydrophobicity_scale = {'A': 0.62, 'R': -2.53, 'N': -0.78, 'D': -0.90, 'C': 0.29,'Q': -0.85, 'E': -0.74, 'G': 0.48, 'H': -0.40, 'I': 1.38,'L': 1.06, 'K': -1.50, 'M': 0.64, 'F': 1.19, 'P': 0.12,'S': -0.18, 'T': -0.05, 'W': 0.81, 'Y': 0.26, 'V': 1.08}

def calculate_hydrophobicity(seq_fasta):
    # Calcule le profil d'hydrophobicité avec une fenêtre glissante de 9 acides aminés
    window_size = 9 #On choisit une fenetre de calcul d'hydrophobicité de 9 acides aminés 
    hydrophobicity_values = []

    for i in range(len(seq_fasta) - window_size + 1): #On fait une boucle du 1er au n-8 AA
        window = seq_fasta[i:i + window_size] #On prend les acides aminés de la fenetre que l'on stocke dans window
        hydrophobicity = sum(hydrophobicity_scale[aa] for aa in window) / window_size #On fait la moyenne d'hydrophobicité de tous les acides aminés de la fenetre grâce à un dictionnaire qui attribue à chaque AA une valeur d'hydrophobicité
        hydrophobicity_values.append(hydrophobicity) #On stocke cette moyenne dans une liste

    return hydrophobicity_values

def save_to_excel(file_name, protein_name, hydrophobicity_values):
    # Enregistre les valeurs d'hydrophobicité dans un fichier Excel

    data = {'Position': list(range(5, 5 + len(hydrophobicity_values))),'Hydrophobicity': hydrophobicity_values} #Stocke dans data un dictionnaire des valeurs d'hydrophobicté et leur position


    df = pd.DataFrame(data) #transforme ce dictionnaire en tableau
    df.to_excel(file_name, sheet_name=protein_name, index=False) #Crée un fichier excel de ce tableau
    print(f"Profil d'hydrophobicité enregistré dans {file_name} pour {protein_name}.")
    return ""


