package br.com.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import br.com.dao.DAOGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDAOPessoa;
import br.com.repository.IDAOPessoaImpl;
import br.com.util.BeanUtil;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa = new Pessoa();
	private List<SelectItem> estados = new ArrayList<SelectItem>();
	private List<SelectItem> cidades = new ArrayList<SelectItem>();
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

	/**
	 * Metodo responsavel por remover o usuario logado na sessão e em seguida
	 * invalidar a sessão
	 * 
	 * @return
	 */
	public String deslogar() {
		// recuperando usuario logado na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		// removendo usuario logado na sessao
		externalContext.getSessionMap().remove("usuarioLogado");

		// recuperando um objeto request para invalidar a sessão
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();

		httpServletRequest.getSession().invalidate();

		// retornando para a tela de login
		return "login.jsf";
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
		try {
			// recebendo o cep do form via ajax
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");

			// fazendo a conexao
			URLConnection connection = url.openConnection();

			// recebe os valores processados como retorno
			InputStream is = connection.getInputStream();

			// criamos um objeto buffer para fazer a leitura dos dados recebidos -retorno
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			// pegando os valores RETORNADOS e joga para as strings
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = bufferedReader.readLine()) != null) {
				jsonCep.append(cep);
			}

			// settando os valores recebidos via json para o objeto pessoa
			// usando a classe GSON do google para separa cada cada recebido para setar em
			// pessoa
			// objeto auxialiado para conseguirmos controlar dados em tela
			Pessoa gsonAuxiliar = new Gson().fromJson(jsonCep.toString(), Pessoa.class);

			pessoa.setCep(gsonAuxiliar.getCep());
			pessoa.setLogradouro(gsonAuxiliar.getLogradouro());
			pessoa.setComplemento(gsonAuxiliar.getComplemento());
			pessoa.setBairro(gsonAuxiliar.getBairro());
			pessoa.setLocalidade(gsonAuxiliar.getLocalidade());
			pessoa.setUf(gsonAuxiliar.getUf());

		} catch (Exception e) {
			e.printStackTrace();
			BeanUtil.showMessage("Erro ao consultar o cep");
		}

	}

	// metodo retorna uma lista de selectItem, esse metodo sera referenciado em tela
	public List<SelectItem> getEstados() {
		estados = idaoPessoa.listaEstados();
		return estados;
	}

	/**
	 * Metodo que recebe chamada ajax e retorna as cidades de um estado que foi
	 * selecionado em tela
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		String codEstado = (String) event.getComponent().getAttributes().get("submittedValue");

		if (codEstado != null) {
			Estados estado = JPAUtil.getEntityManager().find(Estados.class, Long.parseLong(codEstado));
			if (estado != null) {
				pessoa.setEstados(estado);

				List<Cidades> cidades = JPAUtil.getEntityManager()
						.createQuery("FROM Cidades WHERE estados.id= " + codEstado).getResultList();

				List<SelectItem> selectItems = new ArrayList<SelectItem>();

				for (Cidades c : cidades) {
					selectItems.add(new SelectItem(c.getId(), c.getNome()));
				}

				// depois de add cidades
				setCidades(selectItems);
			}
		}
	}

	//metodo carrega as cidades de acordo com as cidades
	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

}
