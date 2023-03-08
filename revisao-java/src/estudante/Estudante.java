package estudante;

public class Estudante {
    private final String nome = "Leonardo";
    private final int idade = 21;
    private final String curso = "Sistemas para Internet";

    public String sobreMim(){
        return "Eu sou " + nome + ", tenho " + idade + " e faço " + curso + ".";
    }
}
