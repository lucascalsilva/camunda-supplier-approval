package br.com.poc.suppliers.model;

import lombok.Builder;
import lombok.Data;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class InstanciaProcessoHomologacao {

    private String cnpj;
    private String razaoSocial;
    private String parecer;
    private String descParecer;
    private String motivo;
    private TimeRecord tempoExecucao;
    private Date startTime;
    private Date endTime;
}
