package fr.insee.junit5;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;

class AppTestJUnit5 {
    
    App app = new App();

    @Test
    void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }

    @Test
    void testMethodeException() {
        assertThrows(IllegalArgumentException.class, () -> app.methodeRenvoyantException());
    }

    @Test
    void testMethodeLongue(){
        assertTimeout(Duration.ofMillis(3000), () -> app.methodeLongue());
    }
}
