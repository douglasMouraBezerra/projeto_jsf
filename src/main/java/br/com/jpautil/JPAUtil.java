package br.com.jpautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.jpautil.JPAUtil;

/**
 * Classe faz a conexao com o banco de dados.
 * 
 * @author Douglas Moura
 *
 */
public class JPAUtil {

	private static final String PERSISTENCE_UNIT = "meuprimeiroprojetojsf";
	private static ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<EntityManager>();
	private static EntityManagerFactory factory = null;

	private JPAUtil() {
	}

	/**
	 * Metodo retorna um {@link EntityManager}
	 * 
	 * @return
	 */
	public static EntityManager getEntityManager() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		}

		EntityManager entityManager = threadEntityManager.get();

		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = factory.createEntityManager();
			JPAUtil.threadEntityManager.set(entityManager);
		}

		return entityManager;
	}

	/**
	 * Metodo fecha o {@link EntityManager}
	 */
	public static void closeEntityManager() {
		EntityManager entityManager = threadEntityManager.get();

		if (entityManager != null) {
			EntityTransaction transaction = entityManager.getTransaction();

			if (transaction.isActive()) {
				transaction.commit();
			}

			entityManager.close();
			threadEntityManager.set(null);
		}
	}

	/**
	 * Metodo fecha {@link EntityManagerFactory}
	 */
	public static void closeEntityManagerFactory() {
		closeEntityManager();
		factory.close();
	}

	/**
	 * Metodo recupera o identificador a partir da entity
	 * 
	 * @param object
	 * @return
	 */
	public static Object getIdentifierByEntity(Object object) {
		return factory.getPersistenceUnitUtil().getIdentifier(object);
	}

}
