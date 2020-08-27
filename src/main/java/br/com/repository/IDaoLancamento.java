package br.com.repository;

import java.util.List;

import br.com.entidades.Lancamento;



public interface IDaoLancamento {

	//RETORNA LISTA DE LISTANDO E RECEBENDO O USUARIO QUE ESTA LOGADO O COD DE QUEM ESTE LOGADO
	List<Lancamento> consultar(Long codUser);

}
