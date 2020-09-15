import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Labirinto {

	static int[] E = { 0, 0 };
	static int[] S = { 11, 11 };
	static int geracoes = 20;
	static int numMovimentos = 35;
	static int melhorValor = 24;
	static int piorValor = 0;
	static int parede = 1;
	static int tamanho = 12;

	public static void main(String[] args) {

		int[][] labirinto = montarLabirinto();
		Movimento[][] populacao = new Movimento[numMovimentos][numMovimentos]; 
		int[] aptidoes = new int[populacao.length];
		Movimento[][] populacaoIntermediaria = new Movimento[numMovimentos][numMovimentos];

		geraPopulacaoInicial(populacao);
		atribuiAptidao(populacao, labirinto, aptidoes);

	}

	public static int[][] montarLabirinto() {
		int[][] labirinto = new int[12][12];
		try {
			Scanner in = new Scanner(new FileReader("labirinto.txt"));
			int linha = 0;
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] valores = line.split(" ");
				for (int i = 0; i < labirinto[0].length; i++) {
					labirinto[linha][i] = Integer.parseInt(valores[i]);
				}
				linha++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Não Encontrado!");
			return null;
		}
		printLabirinto(labirinto);
		return labirinto;
	}

	public static void printLabirinto(int[][] labirinto) {
		System.out.println("Labirinto Carregado:");
		for (int i = 0; i < labirinto.length; i++) {
//		      System.out.print(i + " - ");
			for (int j = 0; j < labirinto[0].length; j++) {
				System.out.print(labirinto[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void printPopulacao(Movimento[][] populacao, int[] aptidoes) {
		System.out.println("População gerada:");
		for (int i = 0; i < populacao.length; i++) {
//		      System.out.print(i + " - ");
			System.out.println(i + " " + Arrays.toString(populacao[i]) + " Aptidão = " + aptidoes[i]);
//			for (int j = 0; j < populacao[0].length; j++) {
//				System.out.print(populacao[i][j] + " ");
//			}
		}
	}

	public static Movimento[][] geraPopulacaoInicial(Movimento[][] populacao) {
		System.out.println();
		Random rng = new Random();
		for (int i = 0; i < populacao.length; i++) {
			for (int j = 0; j < populacao.length; j++) {
				int randomNum = rng.nextInt((4 - 1) + 1) + 1;
				populacao[i][j] = Movimento.getMovimentoByValue(randomNum);
			}
		}
		return populacao;
	}

	public static void atribuiAptidao(Movimento[][] populacao, int[][] labirinto, int[] aptidoes) {
		int[] posicaoAtual = { 0, 0 };// inicio labirinto
		for (int i = 0; i < populacao.length; i++) {
			for (int j = 0; j < populacao.length; j++) {
				realizaMovimento(posicaoAtual, populacao[i][j], labirinto);			
			}
			aptidoes[i] = posicaoAtual[0] + posicaoAtual[1];
			posicaoAtual[0] = 0;
			posicaoAtual[1] = 0;
		}
		
		
		printPopulacao(populacao, aptidoes);		
	}

	private static void realizaMovimento(int[] posicaoAtual, Movimento movimento, int[][] labirinto) {
		switch (movimento) {
		case C: {
			if (posicaoAtual[0] - 1 >= 0 && labirinto[posicaoAtual[0] - 1][posicaoAtual[1]] != parede) {
				posicaoAtual[0] = posicaoAtual[0] - 1;
			}
		}
			break;
		case B: {
			if (posicaoAtual[0] + 1 < tamanho && labirinto[posicaoAtual[0] + 1][posicaoAtual[1]] != parede) {
				posicaoAtual[0] = posicaoAtual[0] + 1;
			}
		}
			break;
		case E: {
			if (posicaoAtual[1] - 1 >= 0 && labirinto[posicaoAtual[0]][posicaoAtual[1] - 1] != parede) {
				posicaoAtual[1] = posicaoAtual[1] - 1;
			}
		}
			break;
		case D: {
			if (posicaoAtual[1] + 1 < tamanho && labirinto[posicaoAtual[0]][posicaoAtual[1] + 1] != parede) {
				posicaoAtual[1] = posicaoAtual[1] + 1;
			}
		}
			break;
		default:
		}
	}
}
