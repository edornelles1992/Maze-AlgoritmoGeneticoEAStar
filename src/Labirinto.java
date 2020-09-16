import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Labirinto {

	static int[] E = { 0, 0 };
	static int[] S = { 11, 11 };
	static int geracoes = 20;
	static int numMovimentos = 40;
	static int melhorValor = 22;
	static int piorValor = 0;
	static int parede = 1;
	static int tamanho = 12;

	public static void main(String[] args) {

		int[][] labirinto = montarLabirinto();
		Movimento[][] populacao = new Movimento[numMovimentos][numMovimentos];
		int[] aptidoes = new int[populacao.length];
		Movimento[][] populacaoIntermediaria = new Movimento[numMovimentos][numMovimentos];
		int[] aptidoesIntermediarias = new int[populacao.length];

		geraPopulacaoInicial(populacao);
		atribuiAptidao(populacao, labirinto, aptidoes);
		atribuiPrimeiraLinhaPopulacaoIntermediaria(populacao, populacaoIntermediaria, aptidoes, aptidoesIntermediarias);
		crossOver(populacao, populacaoIntermediaria, aptidoes);
	}

	private static void crossOver(Movimento[][] populacao, Movimento[][] populacaoIntermediaria, int[] aptidoes) {

		int i = 0;
		int pai;
		int mae;

		for (int j = 0; j < numMovimentos/2; j++) {

			pai = torneio(populacao, aptidoes);
			mae = torneio(populacao, aptidoes);

			// System.out.println(pai);
			// System.out.println(mae);

			for (int coluna = 0; coluna < numMovimentos/2; coluna++) {
				populacaoIntermediaria[i][coluna] = populacao[pai][coluna];
				populacaoIntermediaria[i + 1][coluna] = populacao[mae][coluna];
			}

			for (int coluna = numMovimentos/2; coluna < numMovimentos; coluna++) {
				populacaoIntermediaria[i][coluna] = populacao[mae][coluna];
				populacaoIntermediaria[i + 1][coluna] = populacao[pai][coluna];
			}

			i = i + 2;
		}
		System.out.println();
		System.out.println("População intermediaria");
		printPopulacao(populacaoIntermediaria);
	}

	/**
	 * A linha gerada randomicamente é selecionada com base na que teve melhor aptidão
	 */
	public static int torneio(Movimento[][] populacao, int[] aptidoes) {
		Random rng = new Random();
		int linhaUm = rng.nextInt(numMovimentos);
		int linhaDois = rng.nextInt(numMovimentos);

		if (aptidoes[linhaUm] > aptidoes[linhaDois]) {
			return linhaUm;
		}
		return linhaDois;
	}

	private static void atribuiPrimeiraLinhaPopulacaoIntermediaria(Movimento[][] populacao,
			Movimento[][] populacaoIntermediaria, int[] aptidoes, int[] aptidoesIntermediarias) {
		int melhorLinha = identificaMelhorLinha(aptidoes);
		aptidoesIntermediarias[0] = melhorLinha;
		for (int i = 0; i < populacao[0].length; i++) {
			populacaoIntermediaria[0][i] = populacao[melhorLinha][i];
		}
//		System.out.println();
//		System.out.println("Iniciado População intermediaria");
//		printPopulacao(populacaoIntermediaria, aptidoesIntermediarias);
	}

	private static int identificaMelhorLinha(int[] aptidoes) {
		int melhorLinha = 0;
		for (int aptidao : aptidoes) {
			if (aptidao > melhorLinha)
				melhorLinha = aptidao;
		}
		return melhorLinha;
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
			System.out.println(i + " " + Arrays.toString(populacao[i]) + " Aptidão = " + aptidoes[i]);

		}
	}
	
	public static void printPopulacao(Movimento[][] populacao) {
		System.out.println("População gerada:");
		for (int i = 0; i < populacao.length; i++) {
			System.out.println(i + " " + Arrays.toString(populacao[i]));
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
