import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Labirinto {

	static int[] E = { 0, 0 };
	static int[] S = { 11, 11 };
	static int numMovimentos = 35;

	public static void main(String[] args) {

		int[][] labirinto = montarLabirinto();
		printLabirinto(labirinto);
		Movimento[] movimentos = geraPopulacaoInicial();
		System.out.println(Arrays.toString(movimentos));
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

	public static Movimento[] geraPopulacaoInicial() {
		Random rng = new Random();
		Movimento[] movimentos = new Movimento[numMovimentos];
		for (int i = 0; i < movimentos.length; i++) {
			int randomNum = rng.nextInt((4 - 1) + 1) + 1;
			movimentos[i] = Movimento.getMovimentoByValue(randomNum); 
		}
		return movimentos;
	}
}
