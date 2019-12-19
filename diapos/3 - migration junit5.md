# Migration à JUnit 5

----

## Migration à JUnit 5 sans réécrire les tests JUnit 4

Ajout de la dépendance JUnit 5 qui permet de gérer les tests écrit en versions 3 et 4 (et suppression de la dépendance JUnit 4) :

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

public class TestJUnit4 {
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

## Migration à JUnit 5 sans réécrire les tests JUnit 4

remplacer la dépendance JUnit 4 par la dépendance JUnit 5 (`junit-jupiter`) et corriger les tests qui sont maintenant en erreur :
- mise à jour des annotations et des imports
- reprise de l'ordre des messages dans les assertions (voir diapos suivantes)
- reprise des tests qui avaient des paramètres dans l'annotation `@Test` avec les nouvelles assertions (voir partie suivante)

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

Refactor de la méthode et passage en **JUnit 5** avec oubli du passage du message en dernier paramètre de l'assertion :

```java
public String tailleDeLaChaine(String chaine){
    return null;
}

assertNotNull(
    "lorsque la chaîne de caractères est non null, la méthode retourne sa taille", app.tailleDeLaChaine("toto"));
```

- le test de non nullité porte donc sur la chaine de caractères en premier paramètre
- le message d'erreur qui doit s'afficher si le test échoue est le retour de la fonction (null ici)
- comme la chaine de caractères n'est pas nulle, le test va passer alors que le refactor de la méthode a changé le comportement de la fonction qui ne répond plus au besoin : le test passe donc a tord
- en inversant le message et l'appel à la fonction, le test va bien échouer