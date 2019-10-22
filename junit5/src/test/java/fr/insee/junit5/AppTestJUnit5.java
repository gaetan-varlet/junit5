package fr.insee.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class AppTestJUnit5 {
    
    App app = new App();

    @Test
    void test_sum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }

    @Test
    void testSumAvecAssertAll(){
        assertAll(
            () -> assertEquals(3, app.sum(1, 2)),
            () -> assertEquals(-2, app.sum(3, -5)),
            () -> assertNotEquals(5, app.sum(3, -2))
        ); 
    }

    @Test
    void testMethodeException() {
        assertThrows(IllegalArgumentException.class, () -> app.methodeRenvoyantException());
    }

    @Test
    @DisplayName("methodeLongue doit prendre moins de 3 secondes")
    void testMethodeLongue(){
        assertTimeout(Duration.ofMillis(3000), () -> app.methodeLongue());
    }
    
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

}
