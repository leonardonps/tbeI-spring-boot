package linguagempreferida;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> linguagens = new ArrayList<String>();

        linguagens.add("Python");
        linguagens.add("Javascript");
        linguagens.add("Java");
        linguagens.add("C");

        for(String linguagem : linguagens){
            System.out.println(String.format("Lingugagem: %s", linguagem));
            if(linguagem.equals("Java")){
                System.out.println(String.format("Obs.: %s é minha linguagem preferida :)", linguagem));
            }
        }
    }
}
