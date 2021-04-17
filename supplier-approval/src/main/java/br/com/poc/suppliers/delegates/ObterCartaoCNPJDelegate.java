package br.com.poc.suppliers.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("obterCartaoCNPJ")
public class ObterCartaoCNPJDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Boolean gerarErro = (Boolean) delegateExecution.getVariable("gerarErro");
        if(gerarErro != null && gerarErro){
            throw new RuntimeException("Erro identificado");
        }
    }
}
