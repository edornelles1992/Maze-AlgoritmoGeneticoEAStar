import java.util.Random;

public class ExemploAula {
	public static void main(String[] args) {
	    int [] tarefas = {5,10,15,3,10,5,2,16,9,7, 5,10,15,3,10,5,2,16,9,7};
	    int [][] populacao = new int[11][tarefas.length + 1];
	    int [][] populacaoIntermediaria = new int[11][tarefas.length + 1];
	    geraPopulacaoInicial(populacao);

	    for(int geracao = 0; geracao < 20; geracao++){
	      System.out.println("Geração: " + geracao);

	      atribuiAptidao(populacao, tarefas);
	      printPopulacao(populacao);
	      //System.out.println(identificaMelhorLinha(populacao));
	      atribuiPrimeiraLinhaPopulacaoIntermediaria(populacao, populacaoIntermediaria);
	      crossOver(populacao, populacaoIntermediaria);

	      if(geracao % 2 == 0) {
	        mutacao(populacaoIntermediaria);
	      }
	      //printPopulacao(populacaoIntermediaria);
	      populacao = populacaoIntermediaria;
	    }
	    
	  }

	  public static void mutacao(int [][]populacaoIntermediaria) {
	    Random rng = new Random();
	    int linha = rng.nextInt(10) + 1;
	    int coluna = rng.nextInt(populacaoIntermediaria[0].length - 1);

	    System.out.println("Mutacao: " + linha + " " + coluna);

	    if(populacaoIntermediaria[linha][coluna] == 0){
	        populacaoIntermediaria[linha][coluna] = 1;
	    } else {
	      populacaoIntermediaria[linha][coluna] = 0;
	    }
	   }
	  
	  public static int torneio(int[][]populacao) {
	    Random rng = new Random();
	    int linhaUm = rng.nextInt(11);
	    int linhaDois = rng.nextInt(11);

	    if(populacao[linhaUm][populacao[0].length - 1] < populacao[linhaDois][populacao[0].length - 1]) {
	      return linhaUm;
	    }
	    return linhaDois;
	  }

	  public static void crossOver(int[][]populacao, int [][]populacaoIntermediaria) {

	    int i = 1;
	    int pai;
	    int mae;

	    for(int j = 0; j < 5; j++) {

	      pai = torneio(populacao);
	      mae = torneio(populacao);

	      //System.out.println(pai);
	     // System.out.println(mae);

	      for(int coluna = 0; coluna < 10; coluna++){
	      populacaoIntermediaria[i][coluna] = populacao[pai][coluna];
	      populacaoIntermediaria[i+1][coluna] = populacao[mae][coluna];
	      }

	      for(int coluna = 10; coluna < 20; coluna++) {
	        populacaoIntermediaria[i][coluna] = populacao[mae][coluna];
	        populacaoIntermediaria[i+1][coluna] = populacao[pai][coluna];
	      }

	      i = i + 2;
	  }


	  }

	  public static void geraPopulacaoInicial(int [][] populacao) {
	    Random rng = new Random();
	    for(int i = 0; i < populacao.length; i++) {
	      for(int j = 0; j < populacao[0].length -1; j++) {
	        populacao[i][j] = rng.nextInt(2);
	      }
	    }
	  }

	  public static void printPopulacao(int [][] populacao) {
	    for(int i = 0; i < populacao.length; i++) {
	      System.out.print(i + " - ");
	      for(int j = 0; j < populacao[0].length -1; j++) {
	        System.out.print(populacao[i][j] + " ");
	      }
	      System.out.println("Aptidao: " + populacao[i][populacao[0].length -1]);
	    }
	  }

	  public static int calculaAptidao(int [] linha, int [] tarefas) {
	    int soma0 = 0;
	    int soma1 = 0;
	    for(int i = 0; i< linha.length - 1; i++) {
	      if (linha[i] == 0) {
	        soma0 = soma0 + tarefas[i];
	      } else {
	        soma1 = soma1 + tarefas[i];
	      }
	    }
	    return Math.abs(soma0 - soma1);
	  }


	  public static void atribuiAptidao(int [][] populacao, int [] tarefas) {
	    for(int i = 0; i < populacao.length; i++) {
	      populacao[i][populacao[0].length -1] = calculaAptidao(populacao[i], tarefas);
	    }
	  }

	  public static int identificaMelhorLinha(int [][] populacao) {
	    int melhorLinha = 0;
	    for(int i = 0;i < populacao.length; i++) {
	      if (populacao[i][populacao[0].length - 1] < populacao[melhorLinha][populacao[0].length - 1]) {
	        melhorLinha = i;
	      }
	    }
	    return melhorLinha;
	  }

	  public static void atribuiPrimeiraLinhaPopulacaoIntermediaria(int [][] populacao, int [][] populacaoIntermediaria) {
	    int melhorLinha = identificaMelhorLinha(populacao);
	    for(int i = 0; i < populacao[0].length; i++) {
	      populacaoIntermediaria[0][i] = populacao[melhorLinha][i];
	    }
	  }
}
