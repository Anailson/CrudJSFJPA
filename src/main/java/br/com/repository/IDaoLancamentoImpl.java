package br.com.repository;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

public class IDaoLancamentoImpl implements IDaoLancamento {

	@Override
	public List<Lancamento> consultar(Long codUser) {
				List<Lancamento> lista = null;
		
		//PEGANDO O JPA UTIL
		EntityManager entiManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entiManager.getTransaction();
		transaction.begin();
		
		//CONSULTANDO NO BANCO DE DADOS OS LANCAMENTOS CONFORME O USUARIO QUE ESTA LOGADO
		lista = entiManager.createQuery(" from Lancamento where usuario.id = " + codUser).getResultList();
		
		
		transaction.commit();
		entiManager.close();
		
		
		return lista;
	}

	
	

}
