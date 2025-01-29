# MGL7230 Énoncé du TP 1
### Hiver 2025
### Version 0.1

Dans ce suit, le genre féminin sera utilisé pour alléger le texte.

## Contexte
Je suis une entreprise de réservation de vols déjà fonctionnel mais qui voudrait ajouter aux clientes la possibilité d’accumuler des points par distance voyagé. Á chaque fois qu’une passagère voyage, elle accumule des points selon la distance du parcours de son voyage. Ces points sont cumulatifs et doivent être sauvegarder.

## Objectif technique de l’exercice
Fonctionnement actuel du code :
1)	Comme agente de l’entreprise, on vous demande d’abord dans quel vol la passagère désire y être :
      a.	En choisissant le vol elle vient aussi de choisir le type d’avion et la quantité de place disponible par type de passager (First, Business ou Economy Class)
2)	Une fois le vol identifié, on va demander les éléments nécessaires pour construire un Object Passenger :
      a.	Son passeport
      b.	Son nom
      c.	Son âge
      d.	Quel type de passagère elle est? First class, business class ou economy class
3)	Pour ajouter la passagère au vol, un service va valider la quantité de place disponible par type de passager et va proposer une logique d’ajustement de type de passager selon les disponibilités du vol, alors :
      a.	S’il s’agit d’une passagère du type First Class, mais qu’il n’y a plus de place First Class, la logique va déplacer cette passagère en Business Class
      b.	De même, s’il n’y a plus de place Business Class dans ce vol, la logique va déplacer la passagère en Economy Class
      c.	Le moment que le vol est complet, un message avisera les passagères
4)	La liste de passagère par vol sera disponible à la fin dans le fichier à la racine qui s’appelle : passengerData.csv

Ces étapes expliquent le fonctionnement du code et vous aide à en ajouter la quantité maximale de tests unitaires, pour valider sa qualité et, au maximum possible, garantir aux usagers son fonctionnement. En cas de doute, vous pouvez aussi exécuter le code et suivre les étapes demandées à la console, comme une agente de voyage le ferait.
Pour cloner le code ouvrez votre terminal et exécutez le command suivant :
##  # git clone https://gitlab.info.uqam.ca/castells_f/mgl7230_tp1.git


## Objectifs pédagogiques
Nos développeuses ont déjà fini l’implémentation du code qui permet d’ajouter les points par distance parcouru et aussi par type de passagère, mais, comme d’habitude, la pression pour qu’elles livrent à fait qu’elles n’ont pas encore finalisées les tests unitaires. Alors l’exercice sert à :

1)	Valider votre capacité d’automatisation des éléments d’Analyse Statique et, par conséquence, améliorer la qualité du code
2)	Valider votre compétence sur les tests unitaires
3)	Valider votre capacité à livrer un code qui fonctionne et qui est plus « clean » qu’avant votre intervention

## Vos tâches
Si vous les acceptez, consisteront en :
1)	Ajouter les éléments nécessaires pour garantir la qualité du code (checkstyle, sonarQube)
2)	Ajouter tous tests unitaires nécessaires à couvrir un maximum du code par de tests
3)	Améliorer le code suite aux analyses en garantissant que les tests passent
4)	Rédaction d’un rapport sur la quantité d’erreur que vous avez corrigé dans le code et le pourquoi de chaque élément corrigé
5)	Une fois terminé, vous allez créer votre branche et pusher votre code, après notifiez-moi, SVP
   
## Barème
      Critère	                                                |   Poids
      Bonne qualité du code (amélioration proposé)                  |   20%
      Du code qui passe (réussit) les tests ajouté                  |   35%
      Bonne qualité des tests ajouté + maximum de couverture        |   35%
      Qualité du rapport écrit                                      |   10%

