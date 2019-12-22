# AssertJ

----

## Présentation d'assertJ

- bibliothèque qui permet d'écrire des assertions plus expressives que les assertions standards proposées par JUnit
- meilleure lisibilité des tests et des erreurs lorsqu'elles se produisent

```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.14.0</version>
    <scope>test</scope>
</dependency>
```

----

## Exemple avec une chaîne de caractères

```java
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Test
void testEgaliteString() {
    String prenom = "toto";
    // assertion JUnit
    Assertions.assertEquals("toto", prenom);
    // assertion assertJ
    assertThat(prenom).isEqualTo("toto");
}
```

----

## Exemple avec une liste

```java
@Test
void testEgaliteList() {
    List<String> liste = List.of("a", "b", "c");
    // on souhaite vérifier que la liste est de taille 3
    // qu'elle contient a et c, qu'elle ne contient pas d
    // qu'elle contient exactement dans l'ordre a, b et c

    // assertions JUnit
    Assertions.assertEquals(3, liste.size());
    Assertions.assertTrue(liste.contains("a"));
    Assertions.assertTrue(liste.contains("c"));
    Assertions.assertFalse(liste.contains("d"));
    Assertions.assertEquals(List.of("a", "b", "c"), liste);

    // assertions assertJ
    assertThat(liste).hasSize(3);
    assertThat(liste).contains("a", "c");
    assertThat(liste).doesNotContain("d");
    assertThat(liste).containsExactly("a", "b", "c");

    // possibilité de chaîner les tests
    assertThat(liste).hasSize(3).contains("a", "b").doesNotContain("d").containsExactly("a", "b", "c");
}
```

----

## Exemple de test en erreur

```java
// avec une assertions JUnit
Assertions.assertFalse(liste.contains("c"));

org.opentest4j.AssertionFailedError: expected: <false> but was: <true>

// avec une assertion assertJ
assertThat(liste).doesNotContain("c");

java.lang.AssertionError: 
Expecting
 <["a", "b", "c"]>
not to contain
 <["c"]>
but found
 <["c"]>
```