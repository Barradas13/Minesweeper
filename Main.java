import java.util.Scanner;

public class Main {
    final static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(1);

        while(! tabuleiro.explodiu){
            boolean estourar = false;

            System.out.println("Você deseja marcar (m) ou esourar (b)?");
            String acao = input.next();
            if(acao.equals("b")){
                estourar = true;
            }else if(acao.equals("m")){
                estourar = false;
            }

            int x = input.nextInt();
            int y = input.nextInt();

            System.out.println(acao);

            tabuleiro.colocandoValores(x, y, estourar);
            tabuleiro.imprimindoMatrix();
        }

        System.out.println("estourou seu otário");

    }
}
