package br.com.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.entidades.Pessoa;

public interface IDAOPessoa {

	public Pessoa searchUser(String login, String senha);

	/**
	 * Metodo que retorna uma lista de estados para um combobox
	 * 
	 * @return
	 */
	public List<SelectItem> listaEstados();

}
