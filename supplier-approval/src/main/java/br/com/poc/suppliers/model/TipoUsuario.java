package br.com.poc.suppliers.model;

import lombok.Getter;

@Getter
public enum TipoUsuario {

    FORNECEDOR("Fornecedor"), COLABORADOR("Colaborador");

    TipoUsuario(String descricao){
        this.descricao = descricao;
    }

    String descricao;
}
