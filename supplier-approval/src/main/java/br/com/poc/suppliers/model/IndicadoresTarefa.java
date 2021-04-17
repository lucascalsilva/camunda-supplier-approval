package br.com.poc.suppliers.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class IndicadoresTarefa {

    private String nomeUsuario;
    private TipoUsuario tipoUsuario;
    private Map<String, TimeRecord> tempoMedioTarefas;
}
