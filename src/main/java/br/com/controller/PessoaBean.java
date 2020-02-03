package br.com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.dao.DAOGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDAOPessoa;
import br.com.repository.IDAOPessoaImpl;
import br.com.util.BeanUtil;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa = new Pessoa();
	private DAOGeneric<Pessoa> daoGeneric = new DAOGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDAOPessoa idaoPessoa = new IDAOPessoaImpl();

	public String salvar() {
		pessoa = daoGeneric.salvarERetornar(pessoa);
		novo();
		listarTudo();
		BeanUtil.showMessage("Salvo com sucesso");
		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String limpar() {
		/**/
		this.pessoa = new Pessoa();
		return "";
	}

	public String editar() {
		pessoa = daoGeneric.recuperaEntidadeById(pessoa);
		listarTudo();
		return "";
	}

	public String remover() {
		daoGeneric.deleteEntityByIdentifier(pessoa);
		listarTudo();
		return novo();
	}

	public String logar() {
		Pessoa pessoaUser = this.idaoPessoa.searchUser(pessoa.getLogin(), pessoa.getSenha());
		if (pessoaUser != null) {
			// colocando valor que sera retornado para o filtro
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			return "/primeirapagina.xhtml";
		} else {
			return "login.jsf";
		}
	}

	@PostConstruct
	public void listarTudo() {
		pessoas = daoGeneric.listarTudo(Pessoa.class);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DAOGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DAOGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	/**
	 * Metodo verifica perfil, afim de conceder ou nao acesso a partes da pagina
	 * 
	 * @param a
	 * @return
	 */
	public boolean permiteAcesso(String perfil) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa p = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

		return p.getPerfilUsuario().equals(perfil);
	}

	/**/
	public void pesquisaCep(AjaxBehaviorEvent event) {
		System.out.println("Cep com ajax :" + pessoa.getCep());
	}
}
