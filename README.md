
---

# ShootCraft

**ShootCraft** est un plugin Minecraft pour Spigot 1.8.8 qui propose un mini-jeu de tir compétitif. Les joueurs utilisent un bâton personnalisé ("Lanceur de Feu") pour tirer des flèches explosives, avec une arène définie, un système de kills, et une boucle de jeu synchronisée. Le but est d’éliminer les autres joueurs jusqu’à ce qu’un seul reste ou que le temps soit écoulé.

---

## Fonctionnalités

- **Lanceur de Feu** : Un bâton enchanté pour lancer des flèches explosives.
- **Arène Dynamique** : Configurable via `config.yml` avec des limites (min/max) et des points de téléportation.
- **Boucle de Jeu** :
    - **Attente** : Lobby en attente de joueurs.
    - **Compte à Rebours** : 30 secondes avant le début (réductible).
    - **Partie** : Jusqu’à 5 minutes ou un seul survivant.
    - **Fin** : Classement et réinitialisation.
- **Collisions** : Flèches réagissant aux joueurs (kill).

---

## Prérequis

- **Minecraft** : Version 1.8.8.
- **Serveur** : Spigot 1.8.8 (disponible via [BuildTools](https://www.spigotmc.org/wiki/buildtools/)).
- **Java** : JDK 8 (pour compilation).

---

## Installation

1. **Compiler le Plugin** :
    - Clone ce dépôt :
      ```bash
      git clone https://github.com/TheOriginDeveloppement/ShootCraftSpigot.git
      cd ShootCraftSpigot
      ```
    - Avec Maven (recommandé) :
      ```bash
      mvn clean package
      ```
      Le fichier `ShootCraft-1.0.0-SNAPSHOT.jar` sera généré dans `target/`.
    - Sans Maven : Configure ton IDE (IntelliJ/Eclipse) avec `spigot-1.8.8.jar` et exporte manuellement en `.jar`.

2. **Ajouter au Serveur** :
    - Place `ShootCraft-1.0.0-SNAPSHOT.jar` dans le dossier `plugins/` de ton serveur Spigot.
    - Démarre le serveur :
      ```bash
      java -jar spigot-1.8.8.jar
      ```

3. **Configurer** :
    - Un fichier `config.yml` sera généré dans `plugins/ShootCraft/`. Modifie-le selon tes besoins (voir section Configuration).

---

## Utilisation

1. **Rejoindre le Lobby** :
    - Les joueurs se connectent au serveur et sont ajoutés au `PlayerManager`.
2. **Lancer une Partie** :
    - Attends que le nombre minimum de joueurs soit atteint (défini dans `config.yml`)
3. **Jouer** :
    - Utilise le "Lanceur de Feu" (bâton) pour tirer des flèches en cliquant droit.
    - Élimine les autres joueurs ou attends la fin du temps.

---

## Configuration (`config.yml`)

Le fichier `config.yml` est généré automatiquement avec des valeurs par défaut. Voici un exemple :

```yaml
world: "world"
spawn-name: "ShootCraftSpawn"
players:
  min: 2
  max: 8
arena:
  min:
    x: -25
    y: 90
    z: -25
  max:
    x: 25
    y: 110
    z: 25
teleport-locations:
  - x: 0
    y: 100
    z: 0
  - x: 10
    y: 100
    z: 0
  # ... (8 emplacements au total pour max: 8)
```

- **`world`** : Monde où se déroule la partie.
- **`spawn-name`** : Nom symbolique du spawn.
- **`players.min/max`** : Nombre minimum et maximum de joueurs.
- **`arena.min/max`** : Limites de l’arène (x, y, z).
- **`teleport-locations`** : Liste de coordonnées de téléportation (doit correspondre à `players.max`).

---

## Structure du Projet

```
ShootCraftSpigot/
├── pom.xml              
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── fr/theorigindev/shootcraft/
│   │   │       ├── Loader.java              # Classe principale
│   │   │       ├── entities/                # Entités personnalisées 
│   │   │       ├── game/                    # Logique du jeu 
│   │   │       ├── listeners/               # Écouteurs d’événements
│   │   │       └── utils/                   # Utilitaires 
│   │   └── resources/
│   │       ├── plugin.yml                 
│   │       └── config.yml                   # Configuration par défaut
```

---

## Dépendances

- **Spigot-API 1.8.8** : Incluse via Maven ou ajoutée manuellement.

```xml
<dependency>
    <groupId>org.spigotmc</groupId>
    <artifactId>spigot-api</artifactId>
    <version>1.8.8-R0.1-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

---

## Contribution

1. Fork ce dépôt.
2. Crée une branche pour ta fonctionnalité :
   ```bash
   git checkout -b feature/nouvelle-fonction
   ```
3. Soumets une Pull Request avec une description claire.

---

## Problèmes Connus

- Les flèches peuvent parfois disparaître sans effet si elles touchent un bloc trop rapidement (limitation NMS 1.8.8).
- À signaler via les [Issues](https://github.com/TheOriginDeveloppement/ShootCraftSpigot/issues).


---

*Réaliser avec ❤️ par TheOriginDeveloppement.*

---
