# Introduction

----

## Pourquoi faire des tests

- bien écrire son code en respectant le **principe de responsabilité unique**
- s'assurer que ce qu'on développe fait bien ce qui est attendu
- s'assurer qu'il n'y a pas de régressions après une évolution

----

## JUnit 5 en quelques mots

- framework de tests pour le langage Java
- JUnit 4 a été publié en 2005 (dernières versions : 4.12 de décembre 2014 et 4.13 en janvier 2020), JUnit 5 a été publié en 2017
- réécriture complète du framework avec des nouveautés (tests paramètrés, utilisation des nouveautés Java 8...)
- nécessite Java 8+
- l'utilisation est similaire à JUnit 4
- possibilité de conserver ses tests JUnit 4 et d'écrire les nouveaux avec JUnit 5 grâce à la bibliothèque `junit-vintage-engine`

----

## Architecture

- **JUnit Jupiter** : propose une API reposant sur des annotations pour écrire des tests unitaires JUnit 5 et un TestEngine pour les exécuter
- **JUnit Vintage** : propose un TestEngine pour exécuter des tests JUnit 3 et 4 et ainsi assurer une compatibilité ascendante
- **JUnit Platform** : propose une API permettant aux outils de découvrir et exécuter des tests. Il définit une interface entre JUnit et les clients qui souhaitent exécuter les tests (IDE ou outils de build par exemple)
