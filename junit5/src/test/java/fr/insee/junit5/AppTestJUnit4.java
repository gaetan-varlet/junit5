package fr.insee.junit5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class AppTestJUnit4 {

    App app = new App();

    @Test
    public void testSum() {
        assertEquals(3, app.sum(1, 2));
        assertEquals(-2, app.sum(3, -5));
        assertNotEquals(5, app.sum(3, -2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMethodeException() {
        app.methodeRenvoyantException();
    }
    
    @Test(timeout = 3000)
    public void testMethodeLongue() throws InterruptedException {
        app.methodeLongue();
    }
}
