package br.com.poc.suppliers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Arquivo {

    private String nomeArquivo;
    private byte[] conteudo;
    private String mimeType;
}
