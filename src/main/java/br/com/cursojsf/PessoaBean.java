package br.com.cursojsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	//RESPONSAVEL POR CARREGA A LISTA DE DADOS
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	public String salvar() {
		pessoa =	daoGeneric.merge(pessoa);  //INFORMANDO O MERGE DO JPA E INJECTA OS DADOS NO OBJETO
		carregarPessoas();// QD REMOVE O METODO É CHAMADO
		return "";
	}
	//INSTANCIANDO NOVO OBJETO PRA CRIAR UM CAMOO BOTAO NOVO
	public String novo() {
		pessoa= new Pessoa();
		return "";
	}
	//REMOVE  - criando apos a criação do método na DaoGeneric
	public String remove() {
		
		daoGeneric.deletePorId(pessoa); //remove
		pessoa= new Pessoa(); //cria novo objeto
		carregarPessoas();// QD REMOVE O METODO É CHAMADO
		return "";
	}
	
	//METODO PARA A LISTA
	@PostConstruct //SEMPRE QD ABRIR A TELA OS DADOS SERÁ CARREGADOS
	public void carregarPessoas() {
		
		pessoas = daoGeneric.getListEntity(Pessoa.class);
		
	}
	
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}


	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	
	

}
