package br.com.repository;

import java.util.List;

import br.com.entidades.Lancamento;

public interface IDAOLancamento {

	List<Lancamento> consultarLancamentosUsuario(Long condigoUsuarioLogado);

}
