package br.com.poc.suppliers.services;

import br.com.poc.suppliers.model.Email;

import javax.mail.MessagingException;

public interface EmailService {

    void sendHTMLMessage(Email email) throws MessagingException;
}
