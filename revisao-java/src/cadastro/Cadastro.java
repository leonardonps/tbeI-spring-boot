package cadastro;

import java.time.Year;
import java.util.Scanner;

public class Cadastro {
    public static void main(String[] args) {
        String nome, endereco, curso;
        int idade;

        Scanner s = new Scanner(System.in);

        System.out.println("Digite seu nome: ");
        nome = s.nextLine();

        System.out.println("Digite seu endereço: ");
        endereco = s.nextLine();

        System.out.println("Digite seu curso: ");
        curso = s.nextLine();

        System.out.println("Digite seu ano de nascimento: ");
        int anoNascimento = s.nextInt();

        idade = Year.now().getValue() - anoNascimento;

        if (idade < 18){
            System.out.println("Aluno "+ nome + " de menor de idade, com apenas " + idade + " anos.");
            System.out.println("Por favor, se dirija para a coordenação do curso de " + curso + ".");
        } else{
            System.out.println("\n");
            System.out.println("=================================");
            System.out.println("Usuário cadastrado com sucesso!");
            System.out.println("=================================");
            System.out.println("### Seus dados ###");
            System.out.println("Nome: " + nome);
            System.out.println("Endereço: " + endereco);
            System.out.println("Curso: " + curso);
            System.out.println("Idade: " + idade);
        }
    }
}
