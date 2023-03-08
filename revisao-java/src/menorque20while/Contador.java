package menorque20while;

public class Contador {
    public static void main(String[] args) {
        int contador = 1;

        while(contador <= 20){
            System.out.println(String.format("Número %d é menor ou igual 20 ainda.", contador));
            contador++;
        }

        System.out.println(String.format("%d é maior que 20.", contador));
    }
}
