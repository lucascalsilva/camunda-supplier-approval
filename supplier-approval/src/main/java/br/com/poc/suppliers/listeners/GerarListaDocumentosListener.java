package br.com.poc.suppliers.listeners;

import br.com.poc.suppliers.model.Documento;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;

@Component("gerarListaDocumentos")
public class GerarListaDocumentosListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String listaDocumentosStr = (String) delegateExecution.getVariable("listaDocumentos");

        List<Documento> listaDocumentos = Arrays.stream(listaDocumentosStr.split(","))
                .map(s -> Documento.builder().tipo(s).build()).collect(Collectors.toList());

        delegateExecution.setVariable("listaDocumentos", Variables.objectValue(listaDocumentos)
                .serializationDataFormat(JSON).create());
    }
}
