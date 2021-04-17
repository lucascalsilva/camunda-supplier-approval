package br.com.poc.suppliers.delegates;

import br.com.poc.suppliers.model.Email;
import br.com.poc.suppliers.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("notificarFornecedor")
@RequiredArgsConstructor
public class NotificarFornecedor implements JavaDelegate {

    private final EmailService emailService;

    @Value("email.from")
    private String emailFrom;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String razaoSocial = (String) delegateExecution.getVariable("razaoSocial");
        String enderecoEmail = (String) delegateExecution.getVariable("email");
        String parecer = (String) delegateExecution.getVariable("parecer");

        if(enderecoEmail != null && !enderecoEmail.isEmpty()) {
            Email email = Email.builder().from(emailFrom).to(enderecoEmail)
                    .htmlTemplate(new Email.HtmlTemplate("/email/notificacao-fornecedor",
                            delegateExecution.getVariables()))
                    .subject("Process de homologação do fornecedor " + razaoSocial + " " + parecer)
                    .build();

            emailService.sendHTMLMessage(email);
        }
    }
}
