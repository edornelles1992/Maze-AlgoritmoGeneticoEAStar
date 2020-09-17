import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Labirinto {

	static int[] E = { 0, 0 };
	static int[] S = { 11, 11 };
	static int numGeracoes = 10;
	static int numMovimentos = 120;
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

		for (int geracao = 0; geracao < numGeracoes; geracao++) {
			System.out.println("Geração: " + geracao);

			atribuiAptidao(populacao, labirinto, aptidoes);
			atribuiPrimeiraLinhaPopulacaoIntermediaria(populacao, populacaoIntermediaria, aptidoes,
					aptidoesIntermediarias);
			crossOver(populacao, populacaoIntermediaria, aptidoes, aptidoesIntermediarias);

			if (geracao % 5 == 0) {
				mutacao(populacaoIntermediaria);
			}

			populacao = populacaoIntermediaria;
			aptidoes = aptidoesIntermediarias;
		}
	}

	private static void mutacao(Movimento[][] populacaoIntermediaria) {
		Random rng = new Random();
		int linha = rng.nextInt(numMovimentos);
		int coluna = rng.nextInt(numMovimentos);

		System.out.println("Mutacao: " + linha + " " + coluna);
		Movimento sorteado = Movimento.getMovimentoByValue(rng.nextInt((4 - 1) + 1) + 1);
		if (populacaoIntermediaria[linha][coluna] == Movimento.B) {
			while (sorteado == Movimento.B) {
				sorteado = Movimento.getMovimentoByValue(rng.nextInt((4 - 1) + 1) + 1);
			}
			populacaoIntermediaria[linha][coluna] = sorteado;
		}
		if (populacaoIntermediaria[linha][coluna] == Movimento.C) {
			while (sorteado == Movimento.C) {
				sorteado = Movimento.getMovimentoByValue(rng.nextInt((4 - 1) + 1) + 1);
			}
			populacaoIntermediaria[linha][coluna] = sorteado;
		}
		if (populacaoIntermediaria[linha][coluna] == Movimento.E) {
			while (sorteado == Movimento.E) {
				sorteado = Movimento.getMovimentoByValue(rng.nextInt((4 - 1) + 1) + 1);
			}
			populacaoIntermediaria[linha][coluna] = sorteado;
		}
		if (populacaoIntermediaria[linha][coluna] == Movimento.D) {
			while (sorteado == Movimento.D) {
				sorteado = Movimento.getMovimentoByValue(rng.nextInt((4 - 1) + 1) + 1);
			}
			populacaoIntermediaria[linha][coluna] = sorteado;
		}
	}

	private static void crossOver(Movimento[][] populacao, Movimento[][] populacaoIntermediaria, int[] aptidoes,
			int[] aptidoesIntermediarias) {

		int i = 1; // pula primeira linha
		int pai;
		int mae;

		for (int j = 0; j < (numMovimentos / 2); j++) { // numMovimentos/10 avaliar

			pai = torneio(populacao, aptidoes);
			mae = torneio(populacao, aptidoes);

			// System.out.println(pai);
			// System.out.println(mae);

			for (int coluna = 0; coluna < (numMovimentos / 2); coluna++) {
				populacaoIntermediaria[i][coluna] = populacao[pai][coluna];
				if (i != numMovimentos - 1)
					populacaoIntermediaria[i + 1][coluna] = populacao[mae][coluna];
			}

			for (int coluna = numMovimentos / 2; coluna < numMovimentos; coluna++) {
				populacaoIntermediaria[i][coluna] = populacao[mae][coluna];
				if (i != numMovimentos - 1)
					populacaoIntermediaria[i + 1][coluna] = populacao[pai][coluna];
			}
			// aptidoesIntermediarias[i] = aptidoes[i]; TODO: ver como repassar novas
			// aptidoes
			i = i + 2;
		}
		System.out.println();
//		System.out.println("População intermediaria");
//		printPopulacao(populacaoIntermediaria);
	}

	/**
	 * A linha gerada randomicamente é selecionada com base na que teve melhor
	 * aptidão
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
		aptidoes[0] = aptidoes[melhorLinha];
//		System.out.println();
//		System.out.println("Iniciado População intermediaria");
//		printPopulacao(populacaoIntermediaria, aptidoesIntermediarias);
	}

	private static int identificaMelhorLinha(int[] aptidoes) {
		int melhorLinha = 0;
		for (int i = 1; i < aptidoes.length; i++) {
			if (aptidoes[i] > aptidoes[melhorLinha])
				melhorLinha = i;
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

		ArrayList<int[]> movimentacao = new ArrayList<>();
		movimentacao.add(posicaoAtual);
		for (int i = 0; i < populacao.length; i++) {
			for (int j = 0; j < populacao.length; j++) {
				boolean ehValido = realizaMovimento(posicaoAtual, populacao[i][j], labirinto);
				if (ehValido) {
					movimentacao.add(new int[] { posicaoAtual[0], posicaoAtual[1] });
					validaResultado(posicaoAtual, movimentacao);
				}
			}
			aptidoes[i] = posicaoAtual[0] + posicaoAtual[1];

			posicaoAtual[0] = 0;
			posicaoAtual[1] = 0;
		}
		printPopulacao(populacao, aptidoes);
	}

	private static void validaResultado(int[] posicaoAtual, ArrayList<int[]> movimentacao) {
		if (posicaoAtual[0] == 11 && posicaoAtual[1] == 11) {
			System.out.println("Encontrou a saída do labirinto!");

			for (int i = 1; i < movimentacao.size(); i++) {
				if (i % 20 == 0)
					System.out.println(Arrays.toString(movimentacao.get(i)));
				else
					System.out.print(Arrays.toString(movimentacao.get(i)));
			}
			System.exit(1);
		}

	}

	private static boolean realizaMovimento(int[] posicaoAtual, Movimento movimento, int[][] labirinto) {
		switch (movimento) {
		case C: {
			if (posicaoAtual[0] - 1 >= 0 && labirinto[posicaoAtual[0] - 1][posicaoAtual[1]] != parede) {
				posicaoAtual[0] = posicaoAtual[0] - 1;
				return true;
			}
		}
			break;
		case B: {
			if (posicaoAtual[0] + 1 < tamanho && labirinto[posicaoAtual[0] + 1][posicaoAtual[1]] != parede) {
				posicaoAtual[0] = posicaoAtual[0] + 1;
				return true;
			}
		}
			break;
		case E: {
			if (posicaoAtual[1] - 1 >= 0 && labirinto[posicaoAtual[0]][posicaoAtual[1] - 1] != parede) {
				posicaoAtual[1] = posicaoAtual[1] - 1;
				return true;
			}
		}
			break;
		case D: {
			if (posicaoAtual[1] + 1 < tamanho && labirinto[posicaoAtual[0]][posicaoAtual[1] + 1] != parede) {
				posicaoAtual[1] = posicaoAtual[1] + 1;
				return true;
			}
		}
			break;
		default:
			return false;
		}
		return false;
	}
}
