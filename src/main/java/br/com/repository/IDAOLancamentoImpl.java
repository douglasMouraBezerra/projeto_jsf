package br.com.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

public class IDAOLancamentoImpl implements IDAOLancamento {

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLancamentosUsuario(Long codigoUsuarioLogado) {
		List<Lancamento> lancamentos = null;
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		lancamentos = entityManager.createQuery("from Lancamento l where l.pessoa = '" + codigoUsuarioLogado + "'")
				.getResultList();
		entityManager.getTransaction().commit();

		return lancamentos;
	}

}
