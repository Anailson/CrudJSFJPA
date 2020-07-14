package br.com.jpautil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


//CLASSE RESPONSAVEL PELA PERSISTENCIA EX : SALVAR, EXCLUIR ETC
public class JPAUtil {
	
	private static EntityManagerFactory factory = null;
	
	
	static {  //EXECUTADA APENAS UMA VEZ
		
		if(factory == null) {
			factory= Persistence.createEntityManagerFactory("meuprojetojsf");
		}

	}
	
	public static  EntityManager getEntityManager () {
		return factory.createEntityManager();
	}
	
	
	/*Metodo para identificar por primary Key*/
	public static Object getPrimaryKey(Object entity) {
		
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
