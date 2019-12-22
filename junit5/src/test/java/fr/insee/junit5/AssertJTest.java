package fr.insee.junit5;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertJTest {

    @Test
    void testEgaliteString() {
        String prenom = "toto";
        // assertion JUnit
        Assertions.assertEquals("toto", prenom);
        // assertion assertJ
        assertThat(prenom).isEqualTo("toto");
    }

    @Test
    void testEgaliteList() {
        List<String> liste = List.of("a", "b", "c");
        // on souhaite que la liste est de taille 3
        // qu'elle contient a et c, qu'elle ne contient pas d
        // qu'elle contient exactement dans l'ordre a, b et c

        // assertions JUnit
        Assertions.assertEquals(3, liste.size());
        Assertions.assertTrue(liste.contains("a"));
        Assertions.assertTrue(liste.contains("c"));
        Assertions.assertFalse(liste.contains("d"));
        Assertions.assertEquals(List.of("a", "b", "c"), liste);

        // assertions assertJ
        assertThat(liste).hasSize(3);
        assertThat(liste).contains("a", "c");
        assertThat(liste).doesNotContain("d");
        assertThat(liste).containsExactly("a", "b", "c");
    }

}