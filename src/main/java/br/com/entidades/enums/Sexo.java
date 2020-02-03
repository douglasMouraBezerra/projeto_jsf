package br.com.entidades.enums;

public enum Sexo {

	MASCULINO("Masculino"), FEMININO("Feminino");

	private String descricao;

	private Sexo(String desc) {
		setDescricao(desc);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
