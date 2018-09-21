# SecurityPro
Gestion de la sécurité dans une application web (JEE)
- Gestion des permissions
- Gestion des utilisateurs
- Analyse de l'application (Mouchard) 
# INSTALLATION
Après avoir charger le projet dans votre workspace, vous devrez configurer Glassfish comme suit : 
1. Integration de Glassfish (Name...4.1.2)
2. Connection vers la base de données
- Database --> securitypro_db
- Password --> password postgres
- Schema --> public
- Input connection name --> [postgres on public]
Note : JNDI Name : jdbc/securitypro_db
3. Integration du pilote "securitypro EJB" (lien relatif)
4. Glassflish ressources/ Persistences.xml --> Allez dans configuration pools
5. Creation d'un pool de connexion (Glassfish)
   ** JDBC Connection pools
      . New
      . PoolName (copier le fichier config de glassfish
      . Additionnal properties (23)
--> User = posgres
--> Database Name = securitypro_db
--> Url = delete url
--> PortNumber = 5432
--> Password = ?
6. JDBC Ressources
     . New
     . jndi-name : copier le chemin dans le fichier de configuration de Glassfish (Glassfish.ressources.xml)
     . PoolName : "précédent"
     . OK
# INTEGRATION DU FAMEWORK
7. Clicque droit sur .war/puis Frameworks/Jsf/Composant (PrimeFaces)
