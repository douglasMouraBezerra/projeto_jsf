package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Estados;
import br.com.jpautil.JPAUtil;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Metodo pega o codigo da tela que vem em string e converte ele pro objeto.
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codEstado) {

		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Estados e = manager.find(Estados.class, Long.parseLong(codEstado));

		manager.getTransaction().commit();
		return e;
	}

	/**
	 * Metodo retorna o codigo em string pra tela
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {

		if (estado == null) {
			return null;
		} else if (estado instanceof Estados) {
			return ((Estados) estado).getId().toString();
		} else {
			return estado.toString();
		}

	}

}
