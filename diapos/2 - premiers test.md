# Premiers tests

----

## Evolutions des annotations (1)

| Ancienne              | Nouvelle       | Info  |
| :---:                 | :---:          | :---: |
| `@Test`               | `@Test`        | ne prend plus de paramètres (capture d'exceptions et de timeout déléguée à la librairie d'assertion) |
| `@Before     `        | `@BeforeEach`  | -     |
| `@BeforeClass`        | `@BeforeAll`   | -     |
| `@After`              | `@AfterEach`   | -     |
|  `@AfterClass`        | `@AfterAll`    | -     |
| `@Ignore`             | `@Disabled`    | -     |
| -                     | `@DisplayName` | donner un nom à une classe ou à un test |

----

## Evolutions des annotations (2)

| Ancienne              | Nouvelle       | Info  |
| :---:                 | :---:          | :---: |
| -                     | `@Nested`      | permet d'imbriquer une classe de test dans une classe de test (équivalent du **describe** et **it** en JS) |
| `@Category`           | `@Tag`         | créer des groupes de tests à exécuter spécifiquement |
| `@RunWith` et `@Rule` | `@ExtendWith`  | -     |

Autre évolution : plus besoin de déclarer ses classes de test et ses tests en tant que public, package (pas de mot clé) est suffisant.

----

## Exemple de test en JUnit 4 (1)

Création d'un projet maven
- avec la commande `mvn archetype:generate -DgroupId=fr.insee.junit4 -DartifactId=junit4 -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false`
- mise à jour du POM avec junit en version 4.12 et java 11

----

## Exemple de test en JUnit 4 (2)

```java
// création d'une méthode addition que l'on va tester
public class App {
    public int sum(int a, int b) {
        return a + b;
    }
}

// classe de test
import static org.junit.Assert.*;
import org.junit.Test;

public class AppTest {
    App app = new App();

    @Test
    public void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }
}
```

----

## Un premier test en JUnit 5 (1)

Création d'un nouveau projet Maven comme précédemment sans la dépendance JUnit 4, mais avec la dépendance JUnit 5 à la place :

```xml
<!-- dépendance permettant d'écrire en JUnit 5 -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.5.2</version>
  <scope>test</scope>
</dependency>
```

----

## Un premier test en JUnit 5 (2)

- Maven Surefire Plugin (lanceur de test) doit être en version 2.22 au mininmum, sinon les tests en JUnit 5 ne sont pas reconnus et exécutés par Maven

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
</plugin>
```

- par défaut (en JUnit 4 et 5), les noms des classes de tests doivent commencer ou finir par *Test*, sinon elles ne sont pas détectées et aucun test ne sera lancé

----

## Un premier test en JUnit 5 (3)

Exemple de test en JUnit 5 :
- les imports ont changé
- les mots clés **public** ne sont plus nécessaires

```java
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestJUnit5 {
    App app = new App();

    @Test
    void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }
}
```
