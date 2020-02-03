package br.com.cursojsf;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.dao.DAOGeneric;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class TesteJPA {

	@Test
	public void init() {
		JPAUtil.getEntityManager();
	}

	@Test
	public void initTesteDelete() {
		DAOGeneric<Pessoa> dao = new DAOGeneric<Pessoa>();
		Pessoa p = dao.recuperaEntidadeById(19L, Pessoa.class);
		System.out.println(p.toString());
	}

	@Test
	public void init1() {
		DAOGeneric<Pessoa> dao = new DAOGeneric<Pessoa>();
		Pessoa p = dao.recuperaEntidadeById(21L, Pessoa.class);
		System.out.println(p.toString());
	}

	@Test
	public void initLoginSenha() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		Pessoa pessoa = entityManager.find(Pessoa.class, 19L);
		System.out.println(pessoa.toString());
	}

}
