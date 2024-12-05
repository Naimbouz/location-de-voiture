# Location de Voiture

Ce projet est une application de gestion de location de voitures utilisant Java et MongoDB.

## Prérequis

- Java 8 ou supérieur
- Maven
- MongoDB

## Installation

1. Clonez le dépôt :
   ```bash
   git clone <URL_DU_DEPOT>
   ```

2. Accédez au répertoire du projet :
   ```bash
   cd location-de-voiture
   ```

3. Compilez le projet avec Maven :
   ```bash
   mvn clean install
   ```

## Utilisation

1. Assurez-vous que MongoDB est en cours d'exécution sur `localhost:27017`.

2. Exécutez l'application :
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.locationdevoiture.Main"
   ```

## Fonctionnalités

- Ajouter une voiture
- Obtenir une voiture par ID
- Obtenir toutes les voitures
- Mettre à jour une voiture
- Supprimer une voiture

## Structure du Projet

- `Car.java`: Classe représentant une voiture.
- `CarService.java`: Service pour les opérations CRUD sur les voitures.
- `pom.xml`: Fichier de configuration Maven.

## Contribuer

Les contributions sont les bienvenues. Veuillez soumettre une pull request pour toute amélioration ou correction de bug.

## Licence

Ce projet est sous licence attari shahed.