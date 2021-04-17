package br.com.poc.suppliers.delegates;

import br.com.poc.suppliers.model.Email;
import br.com.poc.suppliers.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Component("enviarLinkAcessoFornecedor")
@RequiredArgsConstructor
public class EnviarLinkAcessoFornecedorDelegate implements JavaDelegate {

    private final EmailService emailService;
    private final IdentityService identityService;

    @Value("email.from")
    private String emailFrom;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String idHomologacao = delegateExecution.getProcessBusinessKey();
        String razaoSocial = (String) delegateExecution.getVariable("razaoSocial");
        String initiator = (String) delegateExecution.getVariable("initiator");
        User user = identityService.createUserQuery().userId(initiator).singleResult();

        Map<String, Object> variables = delegateExecution.getVariables();

        variables.put("idHomologacao", idHomologacao);
        variables.put("solicitante", user.getFirstName() + " " + user.getLastName());

        Email email = Email.builder().from(emailFrom).to(user.getEmail())
                .htmlTemplate(new Email.HtmlTemplate("/email/solicitacao-documentos",
                        variables))
                .subject("Novo processo de homologação do fornecedor: " + idHomologacao)
                .build();

        emailService.sendHTMLMessage(email);
    }
}
