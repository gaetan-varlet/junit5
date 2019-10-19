# JUnit 5

## Introduction

- framework de tests pour le langage Java
- JUnit 5 est publié en 2017, JUnit daté de 2005
- réécriture complète du framework avec des nouveautés (tests paramètrés, )
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
