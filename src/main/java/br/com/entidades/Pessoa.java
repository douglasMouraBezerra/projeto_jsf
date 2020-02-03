package br.com.entidades;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.entidades.enums.Nivel;
import br.com.entidades.enums.Sexo;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idpessoa;

	private String nome;
	private String sobrenome;
	private Integer idade;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	// manymenu
	private String[] frameworks;

	// booleancheckbox
	private Boolean ativo;
	private String login;
	private String senha;
	private String perfilUsuario;

	@Enumerated(EnumType.STRING)
	private Nivel nivel;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "pessoa")
	private List<Lancamento> lancamentos;

	private String cep;

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCep() {
		return this.cep;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}

	public Pessoa() {

	}

	public Long getIdpessoa() {
		return idpessoa;
	}

	public void setIdpessoa(Long idpessoa) {
		this.idpessoa = idpessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	/**
	 * Metodo adiciona lancamento a lista de lancamentos de pessoa
	 * 
	 * @param lancamento
	 */
	public void addLancamento(Lancamento lancamento) {
		this.lancamentos.add(lancamento);
		lancamento.setPessoa(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpessoa == null) ? 0 : idpessoa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (idpessoa == null) {
			if (other.idpessoa != null)
				return false;
		} else if (!idpessoa.equals(other.idpessoa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [idpessoa=" + idpessoa + ", nome=" + nome + ", sobrenome=" + sobrenome + ", idade=" + idade
				+ ", sexo=" + sexo + ", dataNascimento=" + dataNascimento + ", frameworks="
				+ Arrays.toString(frameworks) + ", ativo=" + ativo + ", login=" + login + ", senha=" + senha
				+ ", perfilUsuario=" + perfilUsuario + ", lancamentos=" + lancamentos + "]";
	}

}
