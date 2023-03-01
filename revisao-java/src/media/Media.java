package media;

import java.util.Scanner;

public class Media {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Digite a primeira nota: ");
        Double a = s.nextDouble();
        System.out.println("Agora, a segunda nota: ");
        Double b = s.nextDouble();

        double soma = a +b;

        double media = soma / 2;

        System.out.println("A média é: " + media);
    }
}
