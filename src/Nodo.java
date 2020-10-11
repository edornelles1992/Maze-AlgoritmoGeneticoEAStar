
public class Nodo {

	private int peso;
	public int linha;
	public int coluna;
	private Nodo ant;
	private boolean visitado;

	public Nodo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		peso = 0;
		ant = null;
		visitado = false;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public void setAnt(Nodo n) {
		ant = n;
	}

	public Nodo getAnt() {
		return ant;
	}

	public int getPeso() {
		return peso;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

}
