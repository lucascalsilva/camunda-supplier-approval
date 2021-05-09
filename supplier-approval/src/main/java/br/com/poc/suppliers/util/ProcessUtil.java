package br.com.poc.suppliers.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.variable.Variables.SerializationDataFormats.JSON;

@Component
public class ProcessUtil {

    public ObjectValue listFromCommaSeparatedStr(String commaSeparatedStr){
        return Variables.objectValue(
                Arrays.stream(commaSeparatedStr.split(",")).collect(Collectors.toList()))
                .serializationDataFormat(JSON).create();
    }

    public void cleanFields(DelegateExecution execution, String... fields){
        Arrays.stream(fields).forEach(s -> {
            execution.setVariable(s, "");
        });
    }

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
