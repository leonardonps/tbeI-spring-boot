package expressoesaritmeticas;

import java.util.Scanner;

public class ExpressoesAritmeticas {
    public static void main(String[] args) {
        int a, b, soma, mult;

        Scanner s = new Scanner(System.in);

        System.out.println("Digite um número:");
        a = s.nextInt();
        System.out.println("Digite um outro número:");
        b = s.nextInt();

        soma = a + b;
        mult = a * b;

        System.out.println("A soma é: " + soma + " e a multiplicação: " + mult);
    }
}
