package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.jpautil.JPAUtil;

@FacesConverter(forClass = Estados.class, value="cidadeConverter")
public class CidadeConverter implements Converter, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override/* Retorna obejto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, 
			String codigoCidade) {
		
		//CONSULTANDO NO BANCO
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Cidades cidade = (Cidades)entityManager
				.find(Cidades.class, Long.parseLong(codigoCidade));

		return cidade;
		
	
	}

	@Override/* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component,
			Object cidade) {

		if (cidade == null){
			return null;
		}
		
		if (cidade instanceof Cidades){
			return ((Cidades) cidade).getId().toString();
		}else {
			return cidade.toString();
		}


	}//FIM METODO

}
