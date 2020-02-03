package br.com.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DAOGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDAOLancamento;
import br.com.repository.IDAOLancamentoImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private DAOGeneric<Lancamento> daoLancamento = new DAOGeneric<Lancamento>();
	private IDAOLancamento idaoLancamento = new IDAOLancamentoImpl();
	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> lancamentos;

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa p = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		p.addLancamento(lancamento);
		daoLancamento.salvar(lancamento);
		carregarLancamentos();
		return "";
	}

	@PostConstruct
	public void carregarLancamentos() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa p = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		this.lancamentos = idaoLancamento.consultarLancamentosUsuario(p.getIdpessoa());

	}

	public String novo() {
		lancamento = new Lancamento();
		return "";
	}

	public String remover() {
		daoLancamento.deleteEntityByIdentifier(this.lancamento);
		carregarLancamentos();
		novo();
		return "";
	}

	public String editar() {
		lancamento = daoLancamento.recuperaEntidadeById(this.lancamento);
		carregarLancamentos();
		return "";
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public DAOGeneric<Lancamento> getDaoLancamento() {
		return daoLancamento;
	}

	public void setDaoLancamento(DAOGeneric<Lancamento> daoLancamento) {
		this.daoLancamento = daoLancamento;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

}
