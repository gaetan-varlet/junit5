package fr.insee.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class AppTestJUnit5 {
    
    App app = new App();

    @Test
    void testSum() {
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
    void testMethodeLongue(){
        assertTimeout(Duration.ofMillis(3000), () -> app.methodeLongue());
    }

    @Test
    void testIsIdepInvalide(){
        assertAll(
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(null)),
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("")),
            () -> assertThrows(IllegalArgumentException.class, () -> app.isIdepValide("toto"))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "toto"})
    @NullSource
    void parameterizedTestIsIdepInValide(String idep){
        assertThrows(IllegalArgumentException.class, () -> app.isIdepValide(idep));
    }

}
