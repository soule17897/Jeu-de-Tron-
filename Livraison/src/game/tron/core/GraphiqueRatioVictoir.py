import json
import matplotlib.pyplot as plt

# Ouvrir et lire le fichier JSON
with open('analyses.json') as f:
    data = json.load(f)

# Initialiser des listes pour stocker les valeurs des abscisses et des ordonnées
nombre_joueurs_liste = []
profondeur_recherche_liste = []

# Parcourir les données pour extraire les valeurs nécessaires
for partie in data["Donnees_de_toutes_les_parties"]:
    donnees_partie = partie['Donnees_de_la_partie']

    compt = 0
    for donnee in donnees_partie:
        if("Solo" in donnee["Le_gagnant"]):
            compt += 1


    for donnee in donnees_partie:
        nombre_joueurs_liste.append(donnee['Nombre_de_joueurs']-1)
        profondeur_recherche_liste.append(compt / data['Nombre_de_parties'])


# Créer le graphe
plt.figure(figsize=(8, 6))
plt.plot(nombre_joueurs_liste, profondeur_recherche_liste, color='blue',marker='o', linestyle='-')
plt.title('Nombre de victoire de '+data["Nom_du_Joueur"]+' avec élagage en fonction du nombre d\'adversaires qui réfléchissent peu')
plt.xlabel('Nombre de joueurs')
plt.ylabel('Ratio de victoire')
# plt.ylim(0, 10)
# plt.xlim(1, 6)

plt.grid(True)
plt.show()
