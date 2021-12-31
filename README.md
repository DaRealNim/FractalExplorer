# Fractales

### Exploration de Fractales en Java.

Ce projet permet de créer et visualiser les fractales des ensembles Julia et Mandelbrot. Il possède une CLI qui sauvegarde la visualisation dans une image et une GUI qui permet d'explorer la Fractale.

# Compilation

Ce projet est construit avec Gradle, un script `run.sh` est mis à dispostion.

Si le projet n'est pas encore compilé, `./run.sh` compile et éxécute la version CLI qui affiche la commande `--help`.

Si le projet est déjà compilé `run.sh` lance le programme.

# Documentation

Pour construire la javadoc, vous pouvez lancer

`./gradlew javadoc`

et ouvrir le fichier html crée dans

`app/build/docs/javadoc/index.html`

# Utilisation du CLI

`run.sh` n'a qu'un argument obligatoire `-t`, qui doit indiquer le type de la fractale "julia" ou "mandelbrot". Toutes les autres valeurs sont par défaut.

`./run.sh -t julia`

Construisons un example au fûr et à mesure.

Imaginons que vous souhaitez visualiser un ensemble Julia avec une constante complexe -0.782 + 0.12i,

`./run.sh -t julia -c -0.782,0.12`

Le carré du plan complexe que vous souhaitez visualiser a pour coordonnées (x1 + i\*y1, x2 + i\*y2), les points en haut à gauche et en bas à droite du carré respectivement. De valeur (-3 + 3i, 3 + --3i)

`./run.sh -t julia -c -0.782,0.12 -r -3,3,3,-3`

Et vous voulez que l'image résultat soit de résolution 3000x3000

`./run.sh -t julia -c -0.782,0.12 -r -3,3,3,-3 -p 3000`

Par défaut, les images sont sauvegardées dans
`app/<nom_densemble>.png` mais un chemin et un nom de fichier peuvent être spécifiés avec l'option `-f`, l'ajout de l'extension .png est automatique.

`./run.sh -t julia -c -0.782,0.12 -r -3,3,3,-3 -p 3000 -f ~/julia1`

D'autres customisations de la visualisation existent, référez-vous au `--help`

# Utilisation du GUI

Il est possible d'accéder au GUI avec l'option `-G` ou `--graphical`

Toutes les options pour générer une fractale ci-dessus sont disponibles et libelées.

Une fois que vous avez généré votre fractale avec le bouton `Generate`, vous pouvez l'explorer, pour se déplacer sur les axes du plan, utilisez les flèches à diposition, si vous voulez "zoomer" à un endroit précis, cliquez sur le point de l'image que vous voulez voir et appuyez sur `Zoom +`.

La valeur par défaut de 50 itérations fournit très peu de détails, il est récommandable de zoomer là où on veut voir, et générer à nouveau la fractale sur le rectangle du plan avec une valeur d'itérations plus grande.

# Architecture

- Nous avons essayé de programmer en suivant les principes SOLID, dans le but de diminuer le plus possible la répétition du code.

- La bibliothèque Apache a été utilisée pour les nombres complexes.

- Les classes et fonctions sont segmentées, les calculs des couleurs des pixels sont faits en parallèle.

- Utilisation de l'hérédité pour les types de Fractales,

- Des fonctions du premier ordre pour le ColorTheme.

- drawFractal est une fonction d'ordre supérieur.

- Finalement des fonctions lambda pour alléger le très lourd (mais finalement comme la plupart des interfaces graphiques en Java) GraphicalApp.

# Performance

Nous avons fait une étude de la performance avant et après la parallelisation de la classe Renderer, à l'aide du script `test.sh` et du module `timeit` python.
Si l'utilisateur souhaite faire un tour de tests, il peut lancer `./manual_test.sh` et avoir des résultats individuels affichés pour chaque cas.

La commande utilisée était
`python3 -m timeit "__import__('os').system('./test.sh')"`

Elle renvoie la moyenne du temps d'éxécution de 5 répétitions de ./test.sh:

*Avant parallelisation: ~ 222s*

*Après parallelisation: ~ 92.2s*

Ce qui répresente une amélioration d'environ 240% du temps d'éxécution après la parallelisation de Renderer.
