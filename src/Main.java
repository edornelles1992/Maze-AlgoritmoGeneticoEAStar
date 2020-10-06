public class Main {

    public static void main(String[] args) {

        Genetico genetico = new Genetico(50000, 90, 1 ,12);

        int[][] labirinto = genetico.montarLabirinto();
        Movimento[][] populacao = new Movimento[genetico.numMovimentos][genetico.numMovimentos];
        int[] aptidoes = new int[populacao.length];
        Movimento[][] populacaoIntermediaria = new Movimento[genetico.numMovimentos][genetico.numMovimentos];
        int[] aptidoesIntermediarias = new int[populacao.length];
        genetico.geraPopulacaoInicial(populacao);

        for (int geracao = 0; geracao < genetico.numGeracoes; geracao++) {
            System.out.println("Geração: " + geracao);

            genetico.atribuiAptidao(populacao, labirinto, aptidoes);
            genetico.atribuiPrimeiraLinhaPopulacaoIntermediaria(populacao, populacaoIntermediaria, aptidoes,
                    aptidoesIntermediarias);
            genetico.crossOver(populacao, populacaoIntermediaria, aptidoes, aptidoesIntermediarias);

            genetico.mutacao(populacaoIntermediaria, labirinto);

            populacao = populacaoIntermediaria;
            aptidoes = aptidoesIntermediarias;
        }

        System.out.println("Solução final não encontrada para " + genetico.numGeracoes + " gerações e " + genetico.numMovimentos
                + " movimentos por cromossomo");
    }
}
