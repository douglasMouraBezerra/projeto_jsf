package br.com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import br.com.entidades.Estados;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {

		// Lista de selectItem pra ser exibida no combobox
		List<SelectItem> items = new ArrayList<SelectItem>();

		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		List<Estados> carregaEstados = manager.createQuery("from Estados").getResultList();

		if (carregaEstados != null) {
			// pega cada estado e passa pra uma lista de selectItem
			for (Estados estados : carregaEstados) {
				items.add(new SelectItem(estados.getId(), estados.getNome()));
			}
		}

		manager.getTransaction().commit();
		return items;
	}

}
