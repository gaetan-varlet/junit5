# Les nouvelles annotations

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

- il est aussi possible d'utiliser `@DisplayNameGeneration(ReplaceUnderscores.class)` sur la classe pour que les méthodes soit renommées dans le rapport de test en mettant des blancs à la place des 'underscores', ce qui évite de renseigner 2 fois la même chose dans le nom de la méthode et dans l'annotation `@DisplayName`

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
