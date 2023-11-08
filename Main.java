import java.util.Scanner;
import java.util.Arrays;

public class Main {
    final static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(1);

        tabuleiro.imprimindoMatrix();

        while(! tabuleiro.explodiu){
            try{
                boolean estourar = false;
            
                System.out.println("Você deseja marcar (m) ou estourar (b)?");
                String acao = input.next();
                if(acao.equals("b")){
                    estourar = true;
                }else if(acao.equals("m")){
                    estourar = false;
                }

                int x = input.nextInt();
                int y = input.nextInt();

                tabuleiro.colocandoValores(x, y, estourar);
                tabuleiro.imprimindoMatrix();
            }catch(StackOverflowError e){
                for(int i = 0; i < tabuleiro.ja_foram.size(); i ++){
                    System.out.println(Arrays.toString(tabuleiro.ja_foram.get(i)));
                }
            };
            
        }

        System.out.println("estourou");

    }
}
