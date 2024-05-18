# Ensias Notes: Facilitation de la Gestion des Notes

## Description
Ensias Notes est une application conçue pour faciliter la gestion des notes. Elle est développée avec Next.js pour le frontend et Spring Boot pour le backend.

## Fonctionnement

### Backend

1. **Configuration de la base de données :**
   - Créez une base de données MySQL nommée `ensias-notes`.

2. **Exécution du code backend :**
   - Assurez-vous que votre base de données MySQL est en cours d'exécution.
   - Exécutez le code backend.

3. **Création d'un compte administrateur :**
   - Postez les informations suivantes à l'URL `http://localhost:8080/api/auth/sign-up` pour créer un compte administrateur :

     ```json
     {
         "nom": "Alaoui",
         "prenom": "Amine",
         "email": "amine.alaoui@ensias.um5.ac.ma",
         "role": "admin",
         "password": "abcd1234"
     }
     ```

   - Ce compte administrateur sera utilisé pour ajouter de nouveaux étudiants et enseignants.

### Frontend

1. **Installation des dépendances :**
   - Exécutez `npm install` pour installer toutes les dépendances nécessaires.

2. **Exécution du frontend :**
   - Exécutez `npm run dev` pour démarrer le serveur de développement.

3. **Accès à l'application :**
   - Rendez-vous sur `http://localhost:3000/`.

4. **Connexion :**
   - Connectez-vous en tant qu'administrateur.
   - Vous pouvez ensuite ajouter de nouveaux étudiants et enseignants, et voir la liste des étudiants et des enseignants.

## Notes supplémentaires

- Assurez-vous que le backend est en cours d'exécution avant d'accéder au frontend.
- Utilisez le compte administrateur pour gérer les utilisateurs de l'application.

Nous espérons que cette application simplifiera grandement la gestion des notes à l'ENSIAS. Bonne utilisation !
