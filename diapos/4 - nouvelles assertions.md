# Nouvelles assertions

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
