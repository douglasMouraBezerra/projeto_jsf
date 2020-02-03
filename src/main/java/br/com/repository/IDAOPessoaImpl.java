package br.com.repository;

import javax.persistence.EntityManager;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDAOPessoaImpl implements IDAOPessoa {

	@Override
	public Pessoa searchUser(String login, String senha) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Pessoa p = (Pessoa) manager
				.createQuery("from Pessoa a where a.login= '" + login + "' and a.senha= '" + senha + "'")
				.getSingleResult();
		manager.getTransaction().commit();
		manager.close();
		return p;
	}

}
