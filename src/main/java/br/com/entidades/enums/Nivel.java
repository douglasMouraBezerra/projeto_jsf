package br.com.entidades.enums;

public enum Nivel {

	JUNIOR("Junior"), PLENO("Pleno"), SENIOR("Senior"), ESPECIALISTA("Especialista");

	private String descricao;

	private Nivel(String desc) {
		setDescricao(desc);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
