import java.util.Random;
import java.util.ArrayList;

public class Tabuleiro {
    final static Random random = new Random();

    int dificuldade, qtd_bomb, tamanho;
    String[][] matrix_vizualizar;
    int[][] matrix_bombas;
    boolean explodiu;
    ArrayList<int[]> ja_foram;

    public Tabuleiro(int dificuldade){
        if(dificuldade == 1){
            this.tamanho = 8;
            this.qtd_bomb = 10;
        }else if(dificuldade == 2){
            this.tamanho = 16;
            this.qtd_bomb = 40;
        }else{
            this.tamanho = 30;
            this.qtd_bomb = 99;
        }

        this.matrix_bombas = construindoMatrix();
        this.matrix_vizualizar = new String[this.tamanho][this.tamanho];
        this.explodiu = false;
        this.ja_foram = new ArrayList<>();
    }

    public int[][] construindoMatrix(){
        int[][] matrix = new int[this.tamanho][this.tamanho];


        //colocando as bombas em posições aleatórias
        while(this.qtd_bomb > 0){
            int x_random = random.nextInt(this.tamanho);
            int y_random = random.nextInt(this.tamanho);
            if(matrix[x_random][y_random] != -1){
                matrix[x_random][y_random] = -1;

                this.qtd_bomb --;
            }
        }

        //pré verificando quantas bombas em cada posição
        for(int i = 0; i < matrix.length; i ++){
            for(int j = 0; j < matrix[0].length; j ++){
                if(matrix[i][j] != -1){
                    matrix[i][j] = verificandoRedores(i, j, matrix);
                }
            }
        }

        return matrix;
    }

    public void imprimindoMatrix(){
        System.out.print("   ");
        for(int i = 0; i < this.matrix_vizualizar.length; i ++){
            System.out.print(i + " ");
        }System.out.println();

        for(int i = 0; i < this.matrix_vizualizar.length; i ++){
            System.out.print(i + ": ");
            for(int j = 0; j < this.matrix_vizualizar[0].length; j ++){
                String valor = this.matrix_vizualizar[i][j];
                if(valor == null){
                    System.out.print("* ");
                }else{
                    System.out.print(valor + " ");
                }
            }
            System.out.println();
        }
    }

    public int verificandoRedores(int x, int y, int[][] matrix_bombas){
        int[][] coordenadasRedor = {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        int qtd_bomb = 0;

        for(int i = 0; i < coordenadasRedor.length; i ++){
            try{
                int x_verificar = coordenadasRedor[i][0] + x;
                int y_verificar = coordenadasRedor[i][1] + y;

                if(matrix_bombas[x_verificar][y_verificar] == -1){
                    qtd_bomb ++;
                }
            }catch(IndexOutOfBoundsException e){}   
        }

        return qtd_bomb;
    }

    public void estorandoRedores(int x, int y){
        int[][] coordenadasRedor = {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {-1, -1}, {0, -1}, {1, -1}};

        for(int i = 0; i < coordenadasRedor.length; i ++){
            try{
                int x_verificar = coordenadasRedor[i][0] + x;
                int y_verificar = coordenadasRedor[i][1] + y;

                if(matrix_vizualizar[x_verificar][y_verificar] != null){
                    if(!matrix_vizualizar[x_verificar][y_verificar].equals("p")){
                        if(matrix_bombas[x_verificar][y_verificar] != -1){
                            this.matrix_vizualizar[x_verificar][y_verificar] = "" + this.matrix_bombas[x_verificar][y_verificar];
                        }else{
                            this.explodiu = true;
                            this.matrix_vizualizar[x_verificar][y_verificar] = "(!)";
                        }
                    }
                }else if(matrix_bombas[x_verificar][y_verificar] != -1){
                    this.matrix_vizualizar[x_verificar][y_verificar] = "" + this.matrix_bombas[x_verificar][y_verificar];
                }else if(matrix_bombas[x_verificar][y_verificar] == -1){
                    this.explodiu = true;
                    this.matrix_vizualizar[x_verificar][y_verificar] = "(!)";
                }
                
                int[] l1 = {x, y};

                if(! this.ja_foram.contains(l1) && matrix_bombas[x_verificar][y_verificar] == 0){
                    this.matrix_vizualizar[x_verificar][y_verificar] = "" + this.matrix_bombas[x_verificar][y_verificar];
                    estorandoRedores(x_verificar, y_verificar);
                }
                
                if(!this.ja_foram.contains(l1)){
                    this.ja_foram.add(l1.clone());
                }

            }catch(IndexOutOfBoundsException e){}   
        }

    }

    public void colocandoValores(int x, int y, boolean estourar){
        if(estourar && matrix_vizualizar[x][y] == null){
            if (matrix_bombas[x][y] != -1) {
                
    
                int[] l1 = {x, y};
                if(!this.ja_foram.contains(l1)){
                    this.ja_foram.add(l1.clone());
                }
                if(matrix_bombas[x][y] == 0){
                    estorandoRedores(x, y);
                }
                this.matrix_vizualizar[x][y] = "" + matrix_bombas[x][y];
                
                
            }else{
                this.explodiu = true;
                this.matrix_vizualizar[x][y] = "(!)";
            }
        }else if(matrix_vizualizar[x][y] == null && ! estourar){
            this.matrix_vizualizar[x][y] = "p";
        }else if(!matrix_vizualizar[x][y].equals("p") && ! estourar && !Character.isDigit(matrix_vizualizar[x][y].charAt(0))){
            this.matrix_vizualizar[x][y] = "p";
        }else if(matrix_vizualizar[x][y].equals("p") && ! estourar){
            this.matrix_vizualizar[x][y] = "*";
        }else if(estourar && Character.isDigit(matrix_vizualizar[x][y].charAt(0))){
            estorandoRedores(x, y);
        }
    }


}

