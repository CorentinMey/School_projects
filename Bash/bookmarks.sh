#!/bin/bash
#./bookmarks.sh 192.168.37.34 julie
####
aide (){
	echo "Usage: $0 <adresse_IP> <nom_utilisateur>"
	echo "Transfert les bookmarks Firefox entre comptes distants grace à scp/ssh"
	echo "Argument 1 : adresse IP du compte distant"
	echo "Argument 2 : nom d'utilisateur du compte distant"
	exit
}

# Vérification du nombre d'arguments
if [ "$#" -ne 2 ]; then
    aide
fi

echo "Bienvenue dans l'espace de transfert de bookmarks $(whoami)"
####

##Extraction des arguments

IP="$1"
nomDistant="$2"

## Analyse des arguments


#Recherche du nom du répertoire contenant les bookmarks

loc=$(locate /.mozilla/firefox/profiles.ini)

if [ -z "$loc" ]; then
    echo "Fichier profiles.ini introuvable."
    exit 1
fi

# Recherche du code utilisateur dans profiles.ini
code=$(head -n 4 "$loc" | tail -n 1 | cut -c 6-)

if [ -z "$code" ]; then
    echo "Code utilisateur non trouvé dans profiles.ini."
    exit 1
fi

# Recherche du répertoire contenant les bookmarks
locR=$(locate "$code/places.sqlite" | head -n 1)


if [ -z "$locR" ]; then
    echo "Fichier places.sqlite introuvable pour le code utilisateur $code."
    echo "$code/places.sqlite"
    exit 1
fi

echo "$locR" "$IP" "$nomDistant"


# Connexion à l'ordinateur distant et recherche du fichier contenant les bookmarks

echo "entrez votre mdp pour permettre à $(whoami) de se connecter sur votre ordinateur"

ssh "$nomDistant@$IP" << 'EOF'
if [ $? -eq 0 ]; then
    echo "Connexion réussie."
else
    echo "Échec de la connexion."
fi


#Recherche du code utilisateur sur l'ordinateur distant

locUD=$(find / -name profiles.ini 2>/dev/null | grep "/.mozilla/firefox/profiles.ini")


if [ -z "$locUD" ]; then
    echo "Fichier profiles.ini introuvable. Avez-vous déjà lancé firefox sur votre machine ? Si non, faites le avant de procéder au transfert"
    exit 1
fi

# Recherche du code utilisateur dans profiles.ini
codeUD=$(head -n 4 "$locUD" | tail -n 1 | cut -c 6-)

if [ -z "$codeUD" ]; then
    echo "Code utilisateur non trouvé dans profiles.ini."
    exit 1
fi

# Recherche du répertoire contenant les bookmarks
locRDU=$(locate "$codeUD/places.sqlite" | head -n 1)


if [ -z "$locRDU" ]; then
    echo "Fichier places.sqlite introuvable pour le code utilisateur $codeUD."
    echo "$codeUD/places.sqlite"
    exit 1
fi

EOF
# Copie des bookmarks de l utilisateur 1 à la place des bookmarks de l utilisateur 2

echo "Si vous souhaitez réellement écraser vos bookmarks pour les remplacer par ceux de l'envoyeur tapez votre mdp"

scp "$locR" "$nomDistant@$IP:$locRDU"
if [ $? -eq 0 ]; then
    echo "Transfert réussie."
else
    echo "Échec du transfert."
fi

