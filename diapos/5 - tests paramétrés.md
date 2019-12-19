# Les tests paramétrés

----

## Exemple

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

## Configuration

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
