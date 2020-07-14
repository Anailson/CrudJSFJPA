package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {

	// SALVAR
	public void salvar(E entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(entidade);

		entityTransaction.commit();
		entityManager.close();

	}

	// SALVAR OU ATUALIZA E RETORNO O OBJETO
	public E merge(E entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		E retorno = entityManager.merge(entidade);

		entityTransaction.commit();
		entityManager.close();

		return retorno;

	}
	
	// DELETE  - dando o erro  java.lang.IllegalArgumentException: Removing a detached instance br.com.entidades.Pessoa#7 - Pois não esta espeficando
	/**public void delete(E entidade) {

			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

		    entityManager.remove(entidade);

			entityTransaction.commit();
			entityManager.close();

			

		}*/
		
		//DELETE POR ID. Pois  erro java.lang.IllegalArgumentException: Removing a detached instance br.com.entidades.Pessoa#7
		public void deletePorId(E entidade) {

			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			/* descobrir a primay key para excluir */
			Object id = JPAUtil.getPrimaryKey(entidade);
			entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
					.executeUpdate();
			
			entityTransaction.commit();
			entityManager.close();
		

		}
		
		//METODO PARA LISTAR O DADOS PELO PRIMEFACES - LISTAS
		
		public List<E> getListEntity(Class<E> entidade) {
			
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			
			//LISTA QUE REPRESENTA OS DADOS CADASTRADOS
			//FROM NA TABELA PESSOA - A CONSULTA ESTA DE FORMA GENERICA SERVA PRA QQL CLASSE - 
			List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		

			entityTransaction.commit();
			entityManager.close();
			
			return retorno;
			
		}


}
