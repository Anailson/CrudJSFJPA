package br.com.entidades;

import java.io.Serializable;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;



@Entity //TRANSFORMA EM UMA TABELA NO BANCO DE DADOS
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	
	/*ASSIM OBRIGANDO O USUARIO INFORMA O SOBRENOME*/
	@NotEmpty(message = "Sobrenome deve ser informado")
	@NotNull(message ="Sobrenome deve ser informado" )
	private String sobrenome;
	
	/*USANDO O BEAN VALIDATOR*/
	@CPF(message="Cpf Inválido")//VALIDANDO O CPF
	private String cpf;
	
	private String login;
	
	private String senha;
	
	//CRIANDO VARIAVEL PARA O PERFIL DO USUARIO
	private String perfilUser;

	
	@DecimalMax(value="50", message = "Idade de ser menor que 50")
	@DecimalMin(value="10",message = "Idade deve ser maior que 10")
	private Integer idade;

	
	private String registroProfissional;

	// CONSTRUTOR É PADRÃO DA CLASSE
	public Pessoa() {

	}
	
	private String sexo;
	
	
	private String [] frameworks; //RETORNA EM FORMA DE ARRAY
	
	private Boolean ativo;
	
	private String nivelProgramador;
	
	
	//É UM ARRAY POIS O COMPONETE PODE SELECIONAR VARIOS
	private Integer [] linguagens;
	
	//DATA NASCIMENTO
	@Temporal(TemporalType.DATE)
	private Date dataNascimento = new Date();

	//ATRIBUTOS PARA O VIACEP
	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String localidade;

	private String uf;

	private String unidade;

	private String ibge;

	private String gia;

	//CRIANDO OBJETO EM MEMORIA E NAO GRAVAR NO BANCO E NAO CRIAR REFGISTR
	@Transient /*não fica presistente e nao grava no banco..pra auxilia no desenvol.*/
	private Estados estados;
	
	 //muitas pessoas tem cidades
	@ManyToOne
	private Cidades cidades;
	
	//tipo de text grava na base64
	@Column(columnDefinition = "text")  //aplicado conforme o banco postgres
	private String fotoIconBase64;//imagem em formato de texto
	
	//extensoa jpg, png etc
	private String extensao;
	
	@Lob  //gravar arquivos no banco de dados
	@Basic(fetch =  FetchType.LAZY)
	private byte[] fotoIconeBase64Original;
	
	
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFotoIconBase64() {
		return fotoIconBase64;
	}

	public void setFotoIconBase64(String fotoIconBase64) {
		this.fotoIconBase64 = fotoIconBase64;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public byte[] getFotoIconeBase64Original() {
		return fotoIconeBase64Original;
	}

	public void setFotoIconeBase64Original(byte[] fotoIconeBase64Original) {
		this.fotoIconeBase64Original = fotoIconeBase64Original;
	}

	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}
	
	public Cidades getCidades() {
		return cidades;
	}
	
	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}
	
	public Integer[] getLinguagens() {
		return linguagens;
	}
	
	
	public void setNivelProgramador(String nivelProgramador) {
		this.nivelProgramador = nivelProgramador;
	}
	
	public String getNivelProgramador() {
		return nivelProgramador;
	}
	

	public void setRegistroProfissional(String registroProfissional) {
		this.registroProfissional = registroProfissional;
	}
	
	public String getRegistroProfissional() {
		return registroProfissional;
	}
	
	
	public String getPerfilUser() {
		return perfilUser;
	}

	public void setPerfilUser(String perfilUser) {
		this.perfilUser = perfilUser;
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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}
	
	public String[] getFrameworks() {
		return frameworks;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	// HASCODE E EQUALS APENAS DO ID
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
