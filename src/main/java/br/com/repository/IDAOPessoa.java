package br.com.repository;

import br.com.entidades.Pessoa;

public interface IDAOPessoa {

	Pessoa searchUser(String login, String senha);

}
