package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.jpautil.JPAUtil;

public class DAOGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(entidade);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public E salvarERetornar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entidade = entityManager.merge(entidade);
		entityManager.getTransaction().commit();
		entityManager.close();
		return entidade;

	}

	/**
	 * Remove a entidade passada como parametro
	 * 
	 * @param entidade
	 */
	public void deleteEntity(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.remove(entidade);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * Remove entidade pelo identificador
	 * 
	 * @param id
	 * @param entidade
	 */
	public void deleteEntityByIdentifier(Long id, Class<E> entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createQuery("delete from " + entidade.getSimpleName() + " where id=" + id).executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void deleteEntityByIdentifier(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		Object id = JPAUtil.getIdentifierByEntity(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getSimpleName() + " where id = " + id)
				.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public E recuperaEntidadeById(Long id, Class<E> entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		return entityManager.find(entidade, id);
	}

	@SuppressWarnings("unchecked")
	public E recuperaEntidadeById(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();

		Object id = JPAUtil.getIdentifierByEntity(entidade);

		E e = (E) entityManager.createQuery("from " + entidade.getClass().getSimpleName() + " where id= " + id)
				.getSingleResult();

		entityManager.getTransaction().commit();
		entityManager.close();

		return e;
	}

	@SuppressWarnings("unchecked")
	public List<E> listarTudo(Class<E> entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		List<E> l = entityManager.createQuery("from " + entidade.getName()).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return l;

	}

}
