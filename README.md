# projetM2-foot
Projet M2 TNSID réalisation d'une application sur la reproduction de déplacement sur un terrain de foot 

---
## Installation sur android

Avant de commencer ,  être sur de pouvoir transférer des fichier via câble USB

Télécharger l'apk termux via ce lien : [Termux apk download](https://f-droid.org/repo/com.termux_118.apk).

Si le lien ne fonctionne pas aller directement sur le [site web](https://f-droid.org/en/packages/com.termux)

Après avoir télécharger termux transférer le fichier dans le périphérique connecter en USB (le glisser par exemple dans le répertoire download de l'appareil Android)

Ouvrir le fichier depuis le téléphone il demandera une autorisation pour executer l'application.

Une fois l'application installé on l'a démarre , cela affichera un terminal.

Ensuite on doit donner les droits d'utilisation du stockage interne du périphérique à Termux , pour cela on rentre cette commande 
```
# termux-setup-storage
```

Puis on installe java : 
```
# pkg install jdk-17
```

Après avoir insallé correctement java , on transfère le fichier .jar qui se trouve dans le projet dans "/backend/target/projet.jar"

Puis avec termux on se dirige vers le répertoire où on a glisser le .jar
```
java -jar projet.jar
```

Le projet se lance puis on peut accéder à dernier à l'adresse [localhost:8080](http://localhost:8080)


---