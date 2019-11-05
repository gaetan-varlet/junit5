# Introduction

----

## Pourquoi faire des tests

- bien écrire son code en respectant le **principe de responsabilité unique**
- s'assurer que ce qu'on développe fait bien ce qui est attendu
- s'assurer qu'il n'y a pas de régressions après une évolution

----

## JUnit 5 en quelques mots

- framework de tests pour le langage Java
- JUnit 5 a été publié en 2017, JUnit 4 en 2005
- réécriture complète du framework avec des nouveautés (tests paramètrés, utilisation des nouveautés Java 8...)
- nécessite Java 8+
- l'utilisation est similaire à JUnit 4
- possibilité de conserver ses tests JUnit 4 et d'écrire les nouveaux avec JUnit 5 grâce à la bibliothèque `junit-vintage-engine`
- **maven-surefire-plugin en version 2.22 ou plus ?**

----

## Architecture

- **JUnit Jupiter** : propose une API reposant sur des annotations pour écrire des tests unitaires JUnit 5 et un TestEngine pour les exécuter
- **JUnit Vintage** : propose un TestEngine pour exécuter des tests JUnit 3 et 4 et ainsi assurer une compatibilité ascendante
- **JUnit Platform** : propose une API permettant aux outils de découvrir et exécuter des tests. Il définit une interface entre JUnit et les clients qui souhaitent exécuter les tests (IDE ou outils de build par exemple)

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

----

## Evolutions des annotations (2)

| Ancienne              | Nouvelle       | Info  |
| :---:                 | :---:          | :---: |
| -                     | `@DisplayName` | donner un nom à une classe ou à un test |
| -                     | `@Nested`      | permet d'imbriquer une classe de test dans une classe de test (équivalent du **describe** et **it** en JS) |
| `@RunWith` et `@Rule` | `@ExtendWith`  | -     |

Autres évolutions :
- plus besoin de déclarer ses classes de test et ses tests en tant que public, package (pas de mot clé) est suffisant
- la libraire d’assertion reste assez légère, possibilité d’utiliser une librairie annexe comme **AssertJ**

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

----

### Evolution dans les assertions (1)

- possibilité d'écrire un message d'erreur quand le test échoue dans les assertions : **premier** paramètre de l'assertion dans JUnit 4, alors que dans JUnit 5, c'est le **dernier** paramètre de l'assertion

```java
// JUnit 4 assertEquals(message, expected, actual)
assertEquals("la somme de 1 et 2 doit faire 3", 3, app.sum(1, 2));
// JUnit 5 assertEquals(expected, actual, message)
assertEquals(3, app.sum(1, 2), "la somme de 1 et 2 doit faire 3");
```

Il faut donc bien penser à passer les messages en dernier paramètre si vous transformez les tests en JUnit 5, sinon :
- risque de faire échouer des tests
- risque que des tests passent alors qu'ils devraient échouer suite à une régression

----

### Evolution dans les assertions (2)

Exemple de test qui passe en JUnit 4 :
```java
public String tailleDeLaChaine(String chaine){
    if(chaine == null){
        return null;
    }
    return String.valueOf(chaine.length());
}

assertNotNull(
    "lorsque la chaîne de caractères est non null, la méthode retourne sa taille", app.tailleDeLaChaine("toto"));
```

----

### Evolution dans les assertions (3)

Passage en JUnit 5 avec oubli du passage du message en dernier paramètre de l'assertion et refactor de la méthode :
- le test de non nullité porte donc sur la chaine de caractères en premier paramètre
- le message d'erreur qui doit s'afficher si le test échoue est le retour de la fonction (null ici)
- comme la chaine de caractères n'est pas nulle, le test va passer alors que le refactor de la méthode a changé le comportement de la fonction qui ne répond plus au besoin : le test passe donc a tord
- en inversant le message et l'appel à la fonction, le test va bien échouer

```java
public String tailleDeLaChaine(String chaine){
    return null;
}

assertNotNull(
    "lorsque la chaîne de caractères est non null, la méthode retourne sa taille", app.tailleDeLaChaine("toto"));
```

----

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

----

## Gestion des exceptions et des timeouts (1)

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

----

## Gestion des exceptions et des timeouts (2)

- en JUnit 4 :

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

- en JUnit 5 :

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

----

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

----

## Les tests paramétrés (1)

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

----

## Les tests paramétrés (2)

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

----

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

----

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

----

## L'annotation @ExtendWith

```java
// JUnit 4
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)


// JUnit 5
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
```

----

## Utilisation de JUnit 5 avec Spring Boot

- pour les versions inférieures à la 2.2.0, il faut :
    - exclure la dépendance `junit` (JUnit 4) de `spring-boot-starter-test`
    - ajouter les dépendances Junit 5 nécessaires au projet
- à partir de la version 2.2.0, JUnit 5 est intégré par défaut à la place de JUnit 4 : plus besoin d'ajouter les dépendances
    - une exclusion de `junit-vintage-engine` de `spring-boot-starter-test` est fait par défaut lors de la création d'un projet sur *Spring Initializr*
    - avec l'exclusion, les tests JUnit 4 ne compilent pas. En enlevant l'exclusion, les tests JUnit 4 refonctionnent
    - il est conseillé de laisser l'exclusion quand vous n'avez pas/plus de tests JUnit 4 pour éviter d'utiliser les annotations Junit 4 à tord

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

----

## Présentation d'assertJ

TODO

<!-- -->

## Documentation

- [Documentation officielle](https://junit.org/junit5/)
- [JUnit 5 (JM Doudoux)](https://www.jmdoudoux.fr/java/dej/chap-junit5.htm)
- [JUnit : il serait temps de passer la 5ème ! (Devoxx 2019, vidéo youtube de 27 minutes)](https://www.youtube.com/watch?v=EfxwS54hdkM)
