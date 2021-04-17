package br.com.poc.suppliers.controllers;

import br.com.poc.suppliers.model.InstanciaProcessoHomologacao;
import br.com.poc.suppliers.model.IndicadoresTarefa;
import br.com.poc.suppliers.model.TimeRecord;
import br.com.poc.suppliers.model.TipoUsuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.averagingLong;
import static java.util.stream.Collectors.groupingBy;

@Controller
@RequestMapping("/homologacao-fornecedores/indicadores")
@RequiredArgsConstructor
@Slf4j
public class RelatorioController {

    private final HistoryService historyService;
    private final IdentityService identityService;

    @GetMapping
    public String getIndicadores(Model model){
        model.addAttribute("instanciasProcessoHomologacao", getInstanciasProcessoHomologacao());
        model.addAttribute("indicadoresTarefa", getIndicadoresTarefa());

        return "/view/relatorio-processo";
    }

    private List<InstanciaProcessoHomologacao> getInstanciasProcessoHomologacao(){
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .completed()
                .processDefinitionKey("HomologacaoFornecedoresProcess").list();

        List<InstanciaProcessoHomologacao> instanciaProcessoHomologacaoList =
                historicProcessInstances.stream().map(historicProcessInstance -> {
                    List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                            .processInstanceId(historicProcessInstance.getId())
                            .list();

                    Optional<HistoricVariableInstance> cnpj = historicVariableInstances.stream().filter(historicVariableInstance ->
                            historicVariableInstance.getName().equals("cnpj")).findFirst();

                    Optional<HistoricVariableInstance> razaoSocial = historicVariableInstances.stream().filter(historicVariableInstance ->
                            historicVariableInstance.getName().equals("razaoSocial")).findFirst();

                    Optional<HistoricVariableInstance> parecer = historicVariableInstances.stream().filter(historicVariableInstance ->
                            historicVariableInstance.getName().equals("parecer")).findFirst();

                    Optional<HistoricVariableInstance> descParecer = historicVariableInstances.stream().filter(historicVariableInstance ->
                            historicVariableInstance.getName().equals("descParecer")).findFirst();

                    Optional<HistoricVariableInstance> motivo = historicVariableInstances.stream().filter(historicVariableInstance ->
                            historicVariableInstance.getName().equals("motivo")).findFirst();

                    InstanciaProcessoHomologacao.InstanciaProcessoHomologacaoBuilder builder = InstanciaProcessoHomologacao.builder()
                            .cnpj(cnpj.get().getValue().toString())
                            .razaoSocial(razaoSocial.get().getValue().toString())
                            .parecer(parecer.get().getValue().toString())
                            .startTime(historicProcessInstance.getStartTime())
                            .endTime(historicProcessInstance.getEndTime())
                            .tempoExecucao(new TimeRecord(historicProcessInstance.getDurationInMillis()));

                    descParecer.ifPresent(historicVariableInstance -> builder.descParecer((String) historicVariableInstance.getValue()));
                    motivo.ifPresent(historicVariableInstance -> builder.motivo((String) historicVariableInstance.getValue()));

                    return builder.build();
                }).collect(Collectors.toList());

        return instanciaProcessoHomologacaoList.stream()
                .sorted(Comparator.comparing(InstanciaProcessoHomologacao::getEndTime))
                .collect(Collectors.toList());
    }

    private List<IndicadoresTarefa> getIndicadoresTarefa(){
        List<HistoricTaskInstance> historicTaskInstances = historyService
                .createHistoricTaskInstanceQuery().taskAssigned().list();

        List<IndicadoresTarefa> indicadoresTarefaList = historicTaskInstances.stream()
                .filter(historicTaskInstance -> historicTaskInstance.getEndTime() != null)
                .collect(groupingBy(HistoricTaskInstance::getAssignee,
                        groupingBy(HistoricTaskInstance::getName,
                                averagingLong(HistoricTaskInstance::getDurationInMillis))))
                .entrySet().stream().map(averageExecutionTime -> {
                    User user = identityService.createUserQuery()
                            .userId(averageExecutionTime.getKey()).singleResult();

                    Boolean fornecedor = identityService.createUserQuery()
                            .userId(user.getId()).memberOfGroup("fornecedores").count() > 0;
                    TipoUsuario tipoUsuario = fornecedor ? TipoUsuario.FORNECEDOR : TipoUsuario.COLABORADOR;


                    return IndicadoresTarefa.builder().nomeUsuario(user.getFirstName() + " " + user.getLastName())
                            .tipoUsuario(tipoUsuario)
                            .tempoMedioTarefas(convertToTimeRecords(averageExecutionTime.getValue())).build();
                }).collect(Collectors.toList());

        return indicadoresTarefaList;
    }

    private Map<String, TimeRecord> convertToTimeRecords(Map<String, Double> durations){
        Map<String, TimeRecord> timeRecordMap = new HashMap<>();
        durations.entrySet().forEach(duration -> {
            TimeRecord timeRecord = new TimeRecord(duration.getValue().longValue());
            timeRecordMap.put(duration.getKey(), timeRecord);
        });

        return timeRecordMap;
    }

}
