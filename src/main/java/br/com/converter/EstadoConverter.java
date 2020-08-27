package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.jpautil.JPAUtil;

@FacesConverter(forClass = Estados.class, value="estadoConverter")//value referencia no JSF
public class EstadoConverter implements Converter, Serializable{
	
	private static final long serialVersionUID = 1L;
	@Override/* Retorna obejto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, 
			String codigoEstado) {
	
		//CONSULTANDO NO BANCO
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		Estados estados = (Estados) entityManager.find(Estados.class,
				Long.parseLong(codigoEstado));

		return estados;
		
	}

	@Override/* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component,
			Object estado) {
			//caso o objeto seja null
		if (estado == null){
			return null;
		}
		
		if (estado instanceof Estados){
			return ((Estados) estado).getId().toString();

		}else {
			return estado.toString();
		}

		
	} //FIM METODO
	

}
