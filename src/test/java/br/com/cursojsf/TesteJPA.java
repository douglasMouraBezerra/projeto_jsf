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

	@Test
	public void initTesteTamStringBuilder() {
		String retorno = "\"cep\": \"53431-070\",  \"logradouro\": \"Rua Marrocos\",  \"complemento\": \"\",  \"bairro\": \"Pau Amarelo\",  \"localidade\": \"Paulista\",  \"uf\": \"PE\",  \"unidade\": \"\",  \"ibge\": \"2610707\",  \"gia\": \"\"";
		System.out.println(retorno.length());
	}

}
