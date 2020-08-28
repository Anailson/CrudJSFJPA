package br.com.cursojsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	//RESPONSAVEL POR CARREGA A LISTA DE DADOS
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	//instanciando o Impl
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
	
	//lista de estados
	private List<SelectItem> estados;
	
	//lista de cidades
	private List<SelectItem> cidades;
		
	//criando para obter a foto
	private Part arquivofoto;
	
	
public String salvar() {
			
		
		/*Metodo que salvar sem a foto*/
		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();/*Lista pra carregar as pessoas ao realizar qql ação*/
		mostrarMsg("Cadastrado com sucesso!");
		return "";
	}

    /* CHAMANDO NA ACÃO DE SALVAR NA PAGINA XHTML É CHAMADO ANTES DO SALVAR*/
		public void registraLog() {
			System.out.println("método registraLog");
			/* Criar a rotina de gravação de log */
		}	
	
	
	//A MSG É MOSTRAR APOS AS AÇÕES NA APLICAÇÃO
	private void mostrarMsg(String msg) {//CRIANDO METODO PARA A MSG
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
		
	}
	//INSTANCIANDO NOVO OBJETO PRA CRIAR UM CAMOO BOTAO NOVO
	public String novo() {
		pessoa= new Pessoa();
		return "";
	}
	
	//METODO PARA AÇÃO LIMPAR
	public String limpar() {
		/*Executa algum processo antes de limpar*/
		pessoa = new Pessoa();
		return "";
		
	}
	
	
	//REMOVE  - criando apos a criação do método na DaoGeneric
	public String remove() {
		
		daoGeneric.deletePorId(pessoa); //remove
		pessoa= new Pessoa(); //cria novo objeto
		carregarPessoas();// QD REMOVE O METODO É CHAMADO
		mostrarMsg("Removido com sucesso!");
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
	
	
	
	//metodo de logar
	public  String logar() {
		
		//consultando oque vem da teala
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null) { //achou o usuario
			//add usuario na sessao usuarioLogado
			FacesContext context = FacesContext.getCurrentInstance(); //pegsando info do JSF
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser); //pessoaUser pega o perfil
				
			
			return "primeirapagina.jsf";
		}
		
		return "index.jsf"; //ser nao loga com sucesso retorna para a index
	}
	
	/*-------DESLOGAR DO SISTEMA------*/
	public String deslogar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");//remove o usuarioLogado no sistema
		
		//controle da sessao do usuario
		HttpServletRequest httpServletRequest = (HttpServletRequest) context.getCurrentInstance().
				getExternalContext().getRequest();
		
		httpServletRequest.getSession().invalidate();
		
		return "index.jsf"; //AO DESLOGAR JOGA NA TELA DE LOGIN
	}
	
	
	
	/*--------*/
	/*Metodo pra esconder o perfil do usuario*/
	/*rendered="#{pessoaBean.permiteAcesso('ADMINISTRADOR')}"*/
		public boolean permiteAcesso(String acesso) {
	
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaUser.getPerfilUser().equals(acesso);
				
	}
		
	
		
		//PESQUISANDO CEP
		public void pesquisaCep(AjaxBehaviorEvent  event) {
			
			try {
				//PEGANDO A URL DO VIACEP WEBSERVICE
				URL url = new URL ("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				
				//pegando os dados do viaCep e retornando na variavel jsoncep
				String cep = " ";
				StringBuilder jsonCep = new StringBuilder();
					
					while ((cep = br.readLine()) != null) {
					jsonCep.append(cep);
					
				}
				/*pega os atributos do cep BAIXAND O GSON PELO MAVEN*/
					/*pega os atributos do cep*/
					//INICIANDO UM OBJETO gsonAux
				    Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
				    pessoa.setCep(gsonAux.getCep());
					pessoa.setLogradouro(gsonAux.getLogradouro());
					pessoa.setComplemento(gsonAux.getComplemento());
					pessoa.setBairro(gsonAux.getBairro());
					pessoa.setLocalidade(gsonAux.getLocalidade());
					pessoa.setUf(gsonAux.getUf());
					pessoa.setUnidade(gsonAux.getUnidade());
					pessoa.setIbge(gsonAux.getUnidade());
					pessoa.setGia(gsonAux.getGia());
										
				
			} catch (Exception ex) {
				ex.printStackTrace();
				mostrarMsg("Erro ao consultar o cep");
			}
			
	
		}//FIM DO METODO PESQUISA CEP
	
		//CHAMANDO O GESTLIST
		//private List<SelectItem> estados;
		public List<SelectItem> getEstados() {
			estados = iDaoPessoa.listaEstados();
			return estados;
		}
		
		
		public void setEstados(List<SelectItem> estados) {
			this.estados = estados;
		}



		//METODO PAARA CARREGAR PELO AJAX
		public void carregaCidades(AjaxBehaviorEvent event) {

			//pegando objeto inteiro
			Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource())
					.getValue();
							
				if(estado != null) {
					pessoa.setEstados(estado);
					
					List<Cidades> cidades = JPAUtil.getEntityManager()
							.createQuery("from Cidades where  estados.id = " 
					 + estado.getId()).getResultList();
					//CARREGANDO O COMBO CIDADES
					List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
					
					//varrendo a lista de cidades
					for (Cidades cidade : cidades) {
						selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
					}
						
					setCidades(selectItemsCidade);
							
				}
						
		}//fim do metodo CARREGAR CIDADES
		
		
		//set/get da list cidades
		public void setCidades(List<SelectItem> cidades) {
			this.cidades = cidades;
		}
		public List<SelectItem> getCidades() {
			return cidades;
		}
		
	
		
		
		//METODO EDITAR PRA EDIÇÃO DE CIDADES E RETORNA DE FORMA CORRETA
		public void editar() {
			if (pessoa.getCidades() != null) {
				Estados estado = pessoa.getCidades().getEstados();
				pessoa.setEstados(estado);

				List<Cidades> cidades = JPAUtil
						.getEntityManager()
						.createQuery(
								"from Cidades where estados.id = " + estado.getId())
						.getResultList();

				List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();

				for (Cidades cidade : cidades) {
					selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				}

				setCidades(selectItemsCidade);

			}
		}//fim metodo editar
		

		//set do arquivofoto
		
		public void setArquivofoto(Part arquivofoto) {
			this.arquivofoto = arquivofoto;
		}
		
		public Part getArquivofoto() {
			return arquivofoto;
		}
		

		/*Metodo que convert inputStream para Array de bytes*/
		private byte[] getByte(InputStream is) throws IOException{
			
			int len;
			int size = 1024;
			byte[] buf = null;
			if(is instanceof ByteArrayInputStream) {
				size = is.available();
				buf = new byte[size];
				len = is.read(buf, 0 , size);
			}else {
				//saida de uma midia em forma de bytes 
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				buf = new byte[size];
				
				//LENDO E ADD EM UMA VARIAVEL DE CONTROLE
				while((len = is.read(buf, 0 , size)) != -1) {
					bos.write(buf,0, len);
				}
				
				buf = bos.toByteArray();
			}
			
			return buf;
			
		}//fim metodo
		
		//METODO PARA DOWLOAD DA IMAGEM
		public void download() throws IOException{
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			
			String fileDownLoadId = params.get("fileDownLoadId");
			
			Pessoa pessoa = daoGeneric.consultar(Pessoa.class, fileDownLoadId);
			
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
			response.addHeader("Content-Disposition", "attachment; filename=download." + pessoa.getExtensao());
			
			response.setContentType("application/octet-stream");//SETANDO ARQUIVO DE MIDIA
			response.setContentLength(pessoa.getFotoIconeBase64Original().length);
					
			response.getOutputStream().write(pessoa.getFotoIconeBase64Original());
								response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();
			
		}
		
		/*valueChangeListener_aos_eventos AO EDITAR CHAMAR O VALOR QUE CONSTA E DEPOIS O NOVO NOME*/
		public void mudancaDeValor(ValueChangeEvent evento) {
			System.out.println("Valor antigo: " + evento.getOldValue());//ANAILSON
			System.out.println("Valor Novo: " + evento.getNewValue()); //ANAILSON JAVA
		}

		public void mudancaDeValorSobrenome(ValueChangeEvent evento) {
			System.out.println("Valor antigo: " + evento.getOldValue());//SOBRENOME
			System.out.println("Valor Novo: " + evento.getNewValue());//NOVO SOBRENOME
		}
		
}
