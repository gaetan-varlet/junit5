# JUnit 5 avec Spring Boot

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
