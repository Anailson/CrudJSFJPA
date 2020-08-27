package br.com.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.entidades.Pessoa;

public interface IDaoPessoa {
	
	
     //consultando o usuario
	 Pessoa consultarUsuario(String login, String senha);
	
	 //PRA APLICAR NA LISTA DE ESTADOS
	 List<SelectItem> listaEstados();
	
}