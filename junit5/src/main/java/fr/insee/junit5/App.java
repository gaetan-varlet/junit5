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
    
    public String concatenerChaine(String chaine1, String chaine2) {
   	    if(chaine1 != null && !chaine1.isBlank() && chaine2 != null && !chaine2.isBlank()) {
   		    return chaine1.concat(chaine2);
   	    }
   	    throw new IllegalArgumentException(); 
    }

    public String tailleDeLaChaine(String chaine){
        if(chaine == null){
            return null;
        }
        return String.valueOf(chaine.length());
    }
}
