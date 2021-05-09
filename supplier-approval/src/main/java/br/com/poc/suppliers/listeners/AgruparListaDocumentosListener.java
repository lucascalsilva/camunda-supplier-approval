package br.com.poc.suppliers.listeners;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;

@Component("agruparListaDocumentos")
public class AgruparListaDocumentosListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        /*List<String> documentos = delegateExecution.getVariables().entrySet().stream()
                .filter(variable -> variable.getKey().contains("DOCUMENTO"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        delegateExecution.setVariable("documentos", Variables.objectValue(documentos)
                .serializationDataFormat(JSON).create());*/
    }
}
