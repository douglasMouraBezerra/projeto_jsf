package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.jpautil.JPAUtil;

@FacesConverter(forClass = Cidades.class, value = "cidadesConverter")
public class CidadesConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codCidade) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		Cidades cidade = (Cidades) manager.find(Cidades.class, Long.parseLong(codCidade));

		manager.getTransaction().commit();
		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		if (cidade == null) {
			return null;
		} else if (cidade instanceof Estados) {
			return ((Estados) cidade).getId().toString();
		} else {
			return cidade.toString();
		}
	}

}
