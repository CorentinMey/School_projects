def rech_desc(fichier_brut):
    desc=["Description du fichier PDB:"]
    x=fichier_brut.split("\n") #crée une liste du fichier PDB. Chaque retour à la ligne (\n) déclenche la création d'un nouvel élément de la liste
    for lines in x:
        if lines.startswith("TITLE"): #si la ligne commence par TITLE
            desc.append(" ".join(lines.replace("TITLE","").split())) #Rajoute dans la liste desc la ligne qui contient Title mais sans espace entre les caractères et sans Title
        elif lines.startswith("HEADER"):
            desc.append(" ".join(lines.replace("HEADER","").split()))
        elif lines.startswith("EXPDTA"):
            desc.append(" ".join(lines.replace("EXPDTA","").split()))
        elif "RESOLUTION" in lines and "ANGSTROMS" in lines and  "RANGE" not in lines:
            desc.append(" ".join(lines.split()))
    descf="\n".join(desc)
    return descf

#ou bien (la 1ère enlève le premier mot de chaque ligne)

def rech_desc2(fichier_brut):
    desc=["Description du fichier PDB:"]
    x=fichier_brut.split("\n")
    for lines in x:
        if lines.startswith("TITLE"):
            desc.append(lines)
        elif lines.startswith("HEADER"):
            desc.append(lines)
        elif lines.startswith("EXPDTA"):
            desc.append(lines)
        elif lines.startswith("SEQRES"):
            desc.append(lines)
        elif lines.startswith("HELIX"):
            desc.append(lines)
        elif lines.startswith("SHEET"):
            desc.append(lines)
        elif lines.startswith("SSBOND"):
            desc.append(lines)
        elif "RESOLUTION" in lines and "ANGSTROMS" in lines and  "RANGE" not in lines:
            desc.append(lines)
    descf="\n".join(desc)
    return descf


