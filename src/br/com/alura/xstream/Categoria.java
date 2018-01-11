package br.com.alura.xstream;

public class Categoria {

	private Categoria pai;
	private String nome;
	private Categoria geral;

	public Categoria(Categoria pai, String nome) {
		this.pai = pai;
		this.nome = nome;
	}

	public void setPai(Categoria geral) {
		this.geral = geral;
	}

}
