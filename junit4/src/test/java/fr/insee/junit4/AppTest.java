package fr.insee.junit4;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

    App app = new App();

    @Test
    public void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }
}
