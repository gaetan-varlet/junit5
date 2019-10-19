package varlet.gaetan;

import java.util.Set;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Set<String> a = Set.of("toto", "titi");
        System.out.println(a);
    }

    public int sum(int a, int b) {
        return a + b;
    }
}
