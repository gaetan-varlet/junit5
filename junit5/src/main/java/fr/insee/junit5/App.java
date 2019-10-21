package fr.insee.junit5;

public class App {

    public int sum(int a, int b) {
        return a + b;
    }

    public void methodeRenvoyantException() {
        throw new IllegalArgumentException();
    }

    public void methodeLongue() throws InterruptedException {
        Thread.sleep(2000);
    }
    
    public void isIdepValide(String idep){
        if(idep == null || idep.trim().length() != 6){
            throw new IllegalArgumentException();
        }
    }
}
