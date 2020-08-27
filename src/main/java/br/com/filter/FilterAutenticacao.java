package br.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

/*
 * Autor : Anailson
 * Classe Filter - responsavel por todas as conexões/relações nas paginas
 */
@WebFilter(urlPatterns = "/*")//BUSCANDO TODAS AS PAGINAS
public class FilterAutenticacao implements Filter{

	//levanta toda conexao
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAUtil.getEntityManager();
		
	}
	//TRABALHA TODA AUTENTICACAO
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)	throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;//PEGA OS DADOS DO REQUEST
		HttpSession session = req.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado"); //CARREGANDO O ATRIBUTO USUARIO LOGADO
		
		String url = req.getServletPath();//DESCOBRINDO QUAL A URL É ACESSADA

		if (!url.equalsIgnoreCase("index.jsf")&& usuarioLogado == null ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			return;
		} else {

			/*Executa as ações do request e do response*/
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
	
		
	}

}
