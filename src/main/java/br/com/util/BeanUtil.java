package br.com.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class BeanUtil {

	/**
	 * Metodo responsavel por retornar mensagens para a aplicacao
	 * 
	 * @param message
	 */
	public static void showMessage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}

}
