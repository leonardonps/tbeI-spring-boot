package cadastronome;

import java.util.Scanner;

public class CadastroNome {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[] alunos = new String[7];

        for(int i = 0; i < alunos.length; i++){
            System.out.println(String.format("[%d] Digite o nome: ", i+1));
            alunos[i] = s.nextLine();
        }

        System.out.println("--- Alunos cadastrados ---");

        for(int i = 0; i < alunos.length; i++){
            System.out.println(String.format("Aluno: %s", alunos[i]));
        }
    }
}
