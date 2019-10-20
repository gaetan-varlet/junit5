# JUnit 5

## Introduction

- framework de tests pour le langage Java
- JUnit 5 a été publié en 2017, JUnit 4 en 2005
- réécriture complète du framework avec des nouveautés (tests paramètrés, utilisation des nouveautés Java 8...)
- nécessite Java 8+
- l'utilisation est similaire à JUnit 4
- possibilité de conserver ses tests JUnit 4 et d'écrire les nouveaux avec JUnit 5 grâce à la bibliothèque `junit-vintage-engine`


## Architecture

- **JUnit Jupiter** : propose une API reposant sur des annotations pour écrire des tests unitaires JUnit 5 et un TestEngine pour les exécuter
- **JUnit Vintage** : propose un TestEngine pour exécuter des tests JUnit 3 et 4 et ainsi assurer une compatibilité ascendante
- **JUnit Platform** : propose une API permettant aux outils de découvrir et exécuter des tests. Il définit une interface entre JUnit et les clients qui souhaitent exécuter les tests (IDE ou outils de build par exemple)


## Annotations

- évolution des annotations :

| Ancienne | Nouvelle | Info |
| :---:    | :---: | :---: |
| `@Test`  | `@Test` | ne prend plus de paramètres (capture d'exceptions et de timeout déléguée à la librairie d'assertion) |
| `@Before` | `@BeforeEach` | - |
| `@BeforeClass` | `@BeforeAll` | - |
| `@After` | `@AfterEach` | - |
|  `@AfterClass` | `@AfterAll` | - |
| `@Ignore` | `@Disabled` | - |
| - | `@DisplayName` | donner un nom à une classe ou à un test |
| - | `@Nested` | permet d'imbriquer une classe de test dans une classe de test (équivalent du **describe** et **it** en JS) |

- plus besoin de déclarer ses classes de test et ses tests en tant que public, package (pas de mot clé) est suffisant
- la libraire d’assertion reste assez légère, possibilité d’utiliser une librairie annexe comme **AssertJ**

## Exemple de test en JUnit 4

création d'un projet maven avec la commande `mvn ...`

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

```java
// création d'une méthode addition que l'on va tester
public int sum(int a, int b) {
    return a + b;
}


// classe de test
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTest {

    App app = new App();

    @Test
    public void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
    }
}
```

## Un premier test en JUnit 5

- simple projet maven, ajout de la dépendance pour faire un test JUnit 5
- ajout de la dépendance pour gérer les tests JUnit 4 sans la dépendance JUnit 4


## Utilisation de JUnit 5 avec Spring Boot 2.2

- intégration de JUnit 5 dans Spring Boot 2.2


## Présentation d'assertJ


## Gestion des exceptions et timeout dans les assertions


## Exemple d'utilisation de @DisplayName


## Exemple d'utilisation de @Nested


## Documentation

- [Exemple de base en français](https://blog.zenika.com/2017/12/13/quoi-de-neuf-avec-junit-5/)
- [JUnit 5 (JM Doudoux)](https://www.jmdoudoux.fr/java/dej/chap-junit5.htm)
- [JUnit : il serait temps de passer la 5ème !  (Devoxx 2019, vidéo youtube de 27 minutes)](https://www.youtube.com/watch?v=EfxwS54hdkM)

