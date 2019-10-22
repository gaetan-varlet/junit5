# JUnit 5

## Introduction

- framework de tests pour le langage Java
- JUnit 5 a été publié en 2017, JUnit 4 en 2005
- réécriture complète du framework avec des nouveautés (tests paramètrés, utilisation des nouveautés Java 8...)
- nécessite Java 8+
- l'utilisation est similaire à JUnit 4
- possibilité de conserver ses tests JUnit 4 et d'écrire les nouveaux avec JUnit 5 grâce à la bibliothèque `junit-vintage-engine`
- **maven-surefire-plugin en version 2.22 ou plus ?**


## Architecture

- **JUnit Jupiter** : propose une API reposant sur des annotations pour écrire des tests unitaires JUnit 5 et un TestEngine pour les exécuter
- **JUnit Vintage** : propose un TestEngine pour exécuter des tests JUnit 3 et 4 et ainsi assurer une compatibilité ascendante
- **JUnit Platform** : propose une API permettant aux outils de découvrir et exécuter des tests. Il définit une interface entre JUnit et les clients qui souhaitent exécuter les tests (IDE ou outils de build par exemple)


## Annotations

- évolution des annotations :

| Ancienne       | Nouvelle       | Info |
| :---:          | :---:          | :---: |
| `@Test`        | `@Test`        | ne prend plus de paramètres (capture d'exceptions et de timeout déléguée à la librairie d'assertion) |
| `@Before     ` | `@BeforeEach`  | - |
| `@BeforeClass` | `@BeforeAll`   | - |
| `@After`       | `@AfterEach`   | - |
|  `@AfterClass` | `@AfterAll`    | - |
| `@Ignore`      | `@Disabled`    | - |
| -              | `@DisplayName` | donner un nom à une classe ou à un test |
| -              | `@Nested`      | permet d'imbriquer une classe de test dans une classe de test (équivalent du **describe** et **it** en JS) |

- plus besoin de déclarer ses classes de test et ses tests en tant que public, package (pas de mot clé) est suffisant
- la libraire d’assertion reste assez légère, possibilité d’utiliser une librairie annexe comme **AssertJ**

## Exemple de test en JUnit 4

Création d'un projet maven avec la commande `mvn archetype:generate -DgroupId=fr.insee.junit4 -DartifactId=junit4 -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false`

Mise à jour du pom.xml :

```xml
<?xml version="1.0" encoding="UTF-8" ?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>fr.insee.junit4</groupId>
  <artifactId>junit4</artifactId>
  <version>1.0-SNAPSHOT</version>
  <name>junit5</name>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
          <release>11</release>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

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

## Un premier test en JUnit 5

Création d'un nouveau projet Maven comme précédemment sans la dépendance JUnit 4, mais avev cla dépendance JUnit 5 à la place :

```xml
<!-- dépendance permettant d'écrire en JUnit 5 -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.5.2</version>
  <scope>test</scope>
</dependency>
```

Exemple de test en JUnit 5 :
- les imports ont changé
- les mots clés **public** ne sont plus nécessaires

```java
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTestJUnit5 {
    App app = new App();

    @Test
    void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }
}
```

## Migration à JUnit 5 sans réécrire les tests JUnit 4

Ajout de la dépendance pour gérer les tests JUnit 4 sans la dépendance JUnit 4 :

```xml
<!-- dépendance permettant d'utiliser les test JUnit 4 -->
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>5.5.2</version>
    <scope>test</scope>
</dependency>
```

```java
import static org.junit.Assert.*;

import org.junit.Test;

public class AppTestJUnit4 {
    App app = new App();

    @Test
    public void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }
}
```

## Gestion des exceptions et des timeouts

Avec JUnit 4, possibilité de gérer les timeouts et les exceptions attendues dans les paramètres de l'annotation `@Test`, ce qui n'est plus possible en JUnit 5. Des nouvelles méthodes d'assertions ont été créé avec JUnit 5.

Méthodes à tester :

```java
public void methodeRenvoyantException() {
    throw new IllegalArgumentException();
}

public void methodeLongue() throws InterruptedException {
    Thread.sleep(2000);
}
```

En JUnit 4 :

```java
@Test(expected = IllegalArgumentException.class)
public void testMethodeException() {
    app.methodeRenvoyantException();
}

@Test(timeout = 3000)
public void testMethodeLongue() throws InterruptedException {
    app.methodeLongue();
}
```

En JUnit 5 :

```java
import static org.junit.jupiter.api.Assertions.*;

@Test
void testMethodeException() {
    assertThrows(IllegalArgumentException.class, () -> app.methodeRenvoyantException());
}

@Test
void testMethodeLongue(){
    assertTimeout(Duration.ofMillis(3000), () -> app.methodeLongue());
}
```

## Gestion des tests avec plusieurs assertions

En JUnit 4, lorsqu'un test a plusieurs assertions, le test s'arrête sur la première erreur même s'il y a plusieurs erreurs. En JUnit 5, il est possible d'exécuter toutes les assertions et savoir lesquelles ont échoué avec l'assertion **assertAll()**

```java
@Test
void testSumAvecAssertAll(){
    assertAll(
        () -> assertEquals(3, app.sum(1, 2)),
        () -> assertEquals(-2, app.sum(3, -5)),
        () -> assertNotEquals(5, app.sum(3, -2))
    ); 
}
```

## Les tests paramétrés

Lorsqu'on veut tester plusieurs fois la même méthode avec des paramètres différents, on peut utiliser les test paramétrés avec l'annotation `@ParameterizedTest` pour éviter de copier coller des tests :

```java
// test copié collé
@Test
void testIsIdepInvalide(){
    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(null)),
        () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("")),
        () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("toto"))
    );
}

// test paramétré
@ParameterizedTest
@ValueSource(strings = {"", "toto"})
@NullSource
void parameterizedTestIsIdepInValide(String idep){
    assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(idep));
}
```

- possiblité de nommer les différents cas de test avec l'attribut `name` de `@ParameterizedTest` pour plus de lisibilité dans le rapport de tests
    - le nom par défaut est le nom des paramètres
    - possiblité d'utiliser les variables avec `{0}`, `{1}`...
    - exemple : `@ParameterizedTest(name = "idep {0} est invalide")`

- possibilité d'utiliser l'annotation `@CsvSource` à la place `@ValueSource` lorsqu'on veut utiliser plusieurs paramètres :

```java
@ParameterizedTest(name="Concatener {0} avec {1}")
@CsvSource({"abc, def", "toto, tata"})
public void testConcatenerChaine(String chaine1, String chaine2) {
    assertEquals(chaine1+chaine2, app.concatenerChaine(chaine1, chaine2));
}

@ParameterizedTest(name="Concatener {0} avec {1}")
@CsvSource({",abc", "abc,", ","})
public void testConcatenerChaineEchoue(String chaine1, String chaine2) {
    assertThrows(IllegalArgumentException.class, () -> app.concatenerChaine(chaine1, chaine2));
}
```

Tests paramétrés avec des objets
- utilisation de `@ParameterizedTest` et `@MethodSource` (cf vidéo youtube à 12')


## Exemple d'utilisation de @DisplayName

Possibilité de surcharger le nom de la méthode dans le rapport de test avec `@DisplayName`

```java
@Test
@DisplayName("methodeLongue doit prendre moins de 3 secondes")
void testMethodeLongue(){
    assertTimeout(Duration.ofMillis(3000), () -> app.methodeLongue());
}
```

- il est aussi possible d'utiliser `@DisplayNameGeneration(ReplaceUnderscores.class)` sur la classe pour que les méthodes soit renommées dans le rapport de test en mettant des blancs à la place des 'underscores', ce qui évite de renseigner 2 fois la même chose dans le nom de la place et dans l'annotation `@DisplayName`


## Exemple d'utilisation de @Nested

L'annotation `@Nested` permet d'imbriquer une classe de test dans une classe de test afin de regrouper des tests dans une sous-partie afin de faciliter la lecture du code et du rapport de test (équivalent du **describe** et **it** en JS)

```java
@Nested
class testValiditeIdep {
    @Test
    void testIsIdepInvalide(){
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(null)),
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("")),
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("toto"))
        );
    }

    @ParameterizedTest(name = "idep {0} est invalide")
    @ValueSource(strings = {"", " ", "toto"})
    @NullSource
    void parameterizedTestIsIdepInValide(String idep){
        assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(idep));
    }
}
```

## Présentation d'assertJ


## Utilisation de JUnit 5 avec Spring Boot

- à partir de la version 2.2.0, JUnit 5 est intégré : plus besoin d'ajouter les dépendances


## Documentation

- [Documentation officielle](https://junit.org/junit5/)
- [JUnit 5 (JM Doudoux)](https://www.jmdoudoux.fr/java/dej/chap-junit5.htm)
- [JUnit : il serait temps de passer la 5ème !  (Devoxx 2019, vidéo youtube de 27 minutes)](https://www.youtube.com/watch?v=EfxwS54hdkM)

